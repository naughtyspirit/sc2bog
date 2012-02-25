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
import com.naughtyspirit.desktop.sc2bog.model.db.entity.BaseEntity;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.Building;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.Unit;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.Upgrade;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.BuildingMapper;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.UnitMapper;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.UpgradeMapper;
import com.naughtyspirit.desktop.sc2bog.ui.CustomizeObjectDialog;
import com.naughtyspirit.desktop.sc2bog.ui.listener.OnDoneListener;
import com.naughtyspirit.desktop.sc2bog.ui.table.BuildOrderTable;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 22-02-2012
 */
@Singleton
public class AppFrame extends JFrame {

  private final JPanel mainPanel;

  private String buildRace;
  private OnDoneListener onDoneListener = new OnDoneListener() {
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
  public void setBuildOrderTable(BuildOrderTable buildOrderTable) {
    this.buildOrderTable = buildOrderTable;
  }

  @Inject
  public AppFrame(@MainPanel JPanel mainPanel) {
    super("SC2 Build Order Generator");
    this.mainPanel = mainPanel;
    centerOnScreen();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setContentPane(mainPanel);
    setJMenuBar(appMenu());
  }

  private JMenuBar appMenu() {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("File");
    JMenuItem newItem = new JMenuItem();
    newItem.setAction(new NewBuildAction("New"));
    menu.add(newItem);
    JMenuItem exitItem = new JMenuItem("Exit");
    menu.add(exitItem);
    menuBar.add(menu);
    return menuBar;
  }

  private void centerOnScreen() {
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension screenSize = tk.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;
    setSize(screenWidth / 2, screenHeight / 2);
    setLocation(screenWidth / 4, screenHeight / 4);
  }

  class NewBuildAction extends AbstractAction {

    public NewBuildAction(String name) {
      super(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      String[] possibleValues = {"Terran", "Protoss", "Zerg"};
      int optionSelected = JOptionPane.showOptionDialog(AppFrame.this,
              "Choose race", "Race",
              JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE, null,
              possibleValues, possibleValues[0]);
      if (optionSelected != JOptionPane.CLOSED_OPTION) {
        buildRace = possibleValues[optionSelected];
        addBuildOrderView();
      }
    }
  }

  public void addBuildOrderView() {
    List<BaseEntity> entities = entitiesForAutocompletion();
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

    mainPanel.add(searchLabel);
    mainPanel.add(searchTextField, "wrap");

    JScrollPane scrollPane = new JScrollPane(buildOrderTable);
    scrollPane.setMinimumSize(new Dimension(600, 300));
    mainPanel.add(scrollPane, "span");
    validate();
  }

  private List<BaseEntity> entitiesForAutocompletion() {
    List<Unit> units = unitMapper.findByRaceName(buildRace);
    List<Upgrade> upgrades = upgradeMapper.findByRaceName(buildRace);
    List<Building> buildings = buildingMapper.findByRaceName(buildRace);
    List<BaseEntity> entities = Lists.newArrayList();
    entities.addAll(units);
    entities.addAll(upgrades);
    entities.addAll(buildings);
    return entities;
  }
}
