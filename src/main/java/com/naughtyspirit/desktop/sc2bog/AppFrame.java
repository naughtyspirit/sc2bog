package com.naughtyspirit.desktop.sc2bog;

/**
 * Copyright (c) 2012 Naughty Spirit
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import com.google.common.collect.Lists;
import com.google.inject.Injector;
import com.naughtyspirit.desktop.sc2bog.injection.annotation.MainPanel;
import com.naughtyspirit.desktop.sc2bog.model.GameObject;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.*;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.BuildOrderMapper;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.BuildingMapper;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.UnitMapper;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.UpgradeMapper;
import com.naughtyspirit.desktop.sc2bog.ui.AppMenu;
import com.naughtyspirit.desktop.sc2bog.ui.CustomizeObjectDialog;
import com.naughtyspirit.desktop.sc2bog.ui.NewBuildDialog;
import com.naughtyspirit.desktop.sc2bog.ui.table.BuildOrderTable;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 22-02-2012
 */
@Singleton
public class AppFrame extends JFrame implements NewBuildDialog.OnDoneListener, AppMenu.OnNewListener, AppMenu.OnExitListener {

  private CustomizeObjectDialog.OnDoneListener onDoneListener = new CustomizeObjectDialog.OnDoneListener() {
    @Override
    public void onDone(GameObject gameObject) {
      buildOrderTable.addRow(gameObject);
      startTime = gameObject.getTime() + 1;
    }
  };

  @Inject
  Injector injector;

  private UnitMapper unitMapper;
  private int startTime = 1;
  private BuildOrder buildOrder;

  @Inject
  public void setUnitMapper(UnitMapper unitMapper) {
    this.unitMapper = unitMapper;
  }

  private UpgradeMapper upgradeMapper;

  @Inject
  public void setUpgradeMapper(UpgradeMapper upgradeMapper) {
    this.upgradeMapper = upgradeMapper;
  }

  private BuildingMapper buildingMapper;

  @Inject
  public void setBuildingMapper(BuildingMapper buildingMapper) {
    this.buildingMapper = buildingMapper;
  }

  private BuildOrderTable buildOrderTable;

  @Inject
  public void setBuildOrderMapper(BuildOrderMapper buildOrderMapper) {
    this.buildOrderMapper = buildOrderMapper;
  }

  private BuildOrderMapper buildOrderMapper;

  @Inject
  public AppFrame(@MainPanel JPanel mainPanel, AppMenu menuBar) {
    super("SC2 Build Order Generator");
    centerOnScreen();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setContentPane(mainPanel);
    setJMenuBar(menuBar);
    menuBar.setOnNewListener(this);
    menuBar.setOnExitListener(this);
  }

  public void display() {
    displayBuildOrderList();
    setVisible(true);
  }

  private void displayBuildOrderList() {
    getContentPane().removeAll();
    repaint();
    JLabel buildOrderListLabel = new JLabel("Saved Build Orders");
    add(buildOrderListLabel, "wrap");
    final DefaultListModel<BuildOrder> buildOrderListModel = new DefaultListModel<BuildOrder>();
    List<BuildOrder> buildOrders = buildOrderMapper.findAll();
    for (BuildOrder buildOrder : buildOrders) {
      buildOrderListModel.addElement(buildOrder);
    }
    final JList<BuildOrder> list = new JList<BuildOrder>(buildOrderListModel);
    list.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
          BuildOrder buildOrder = list.getSelectedValue();
          buildOrderMapper.delete(buildOrder);
          buildOrderListModel.remove(list.getSelectedIndex());
          list.revalidate();
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
      }
    });
    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    list.setLayoutOrientation(JList.VERTICAL);
    list.setVisibleRowCount(-1);
    JScrollPane listScroll = new JScrollPane(list);
    listScroll.setMinimumSize(new Dimension(200, 250));
    add(listScroll);
    revalidate();
  }

  private void centerOnScreen() {
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension screenSize = tk.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;
    setSize(screenWidth / 2, screenHeight / 2);
    setLocation(screenWidth / 4, screenHeight / 4);
  }

  @Override
  public void onDone(String name, Race race) {
    buildOrder = new BuildOrder();
    buildOrder.name = name;
    buildOrder.raceId = race.id;
    displayBuildOrderView(race);
  }

  @Override
  public void onNew() {
    NewBuildDialog newBuildDialog = injector.getInstance(NewBuildDialog.class);
    newBuildDialog.setOnDoneListener(this);
    newBuildDialog.display();
  }

  @Override
  public void onExit() {
    System.exit(0);
  }

  public void displayBuildOrderView(Race race) {
    getContentPane().removeAll();
    repaint();

    buildOrderTable = new BuildOrderTable();
    buildOrderTable.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
          buildOrderTable.removeSelectedRow();
        }
      }
    });
    List<BaseEntity> entities = entitiesForAutocompletion(race);
    JLabel searchLabel = new JLabel("Search");
    final JComboBox<BaseEntity> searchTextField = new JComboBox<BaseEntity>(entities.toArray(new BaseEntity[entities.size()]));
    AutoCompleteDecorator.decorate(searchTextField);

    searchTextField.getEditor().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        BaseEntity selectedObject = searchTextField.getItemAt(searchTextField.getSelectedIndex());
        CustomizeObjectDialog customizeObjectDialog = injector.getInstance(CustomizeObjectDialog.class);
        customizeObjectDialog.setStartTime(startTime);
        customizeObjectDialog.setSelectedEntity(selectedObject);
        customizeObjectDialog.setOnDoneListener(onDoneListener);
        customizeObjectDialog.display();
      }
    });

    add(searchLabel);
    add(searchTextField, "wrap");

    JScrollPane scrollPane = new JScrollPane(buildOrderTable);
    scrollPane.setMinimumSize(new Dimension(600, 300));
    add(scrollPane, "span, wrap");
    JButton saveButton = new JButton();
    saveButton.setAction(new AbstractAction("Save") {
      @Override
      public void actionPerformed(ActionEvent e) {
        buildOrder.setBuildItems(buildOrderTable.getBuildItems());
        buildOrderMapper.save(buildOrder);
        displayBuildOrderList();
      }
    });
    add(saveButton);
    revalidate();
  }

  private List<BaseEntity> entitiesForAutocompletion(Race race) {
    List<Unit> units = unitMapper.findByRaceName(race.name);
    List<Upgrade> upgrades = upgradeMapper.findByRaceName(race.name);
    List<Building> buildings = buildingMapper.findByRaceName(race.name);
    List<BaseEntity> entities = Lists.newArrayList();
    entities.addAll(units);
    entities.addAll(upgrades);
    entities.addAll(buildings);
    return entities;
  }
}
