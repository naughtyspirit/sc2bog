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

import com.naughtyspirit.desktop.sc2bog.injection.annotation.MainPanel;
import com.naughtyspirit.desktop.sc2bog.model.GameObject;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.Unit;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.UnitMapper;
import com.naughtyspirit.desktop.sc2bog.ui.CustomizeObjectDialog;
import com.naughtyspirit.desktop.sc2bog.ui.listener.OnDoneListener;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 22-02-2012
 */
public class MainFrame extends JFrame {

  private final JPanel mainPanel;

  private String buildRace;
  private OnDoneListener onDoneListener = new OnDoneListener() {
    @Override
    public void onDone(GameObject gameObject) {
      System.out.println(gameObject.getName());
    }
  };


  private UnitMapper unitMapper;

  @Inject
  public void setUnitMapper(UnitMapper unitMapper) {
    this.unitMapper = unitMapper;
  }

  @Inject
  public MainFrame(@MainPanel JPanel mainPanel) {
    super("SC2 Build Order Generator");
    this.mainPanel = mainPanel;
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension screenSize = tk.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;
    setSize(screenWidth / 2, screenHeight / 2);
    setLocation(screenWidth / 4, screenHeight / 4);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    setContentPane(mainPanel);
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("File");
    JMenuItem newItem = new JMenuItem();
    newItem.setAction(new NewBuildAction("New"));
    menu.add(newItem);
    JMenuItem exitItem = new JMenuItem("Exit");
    menu.add(exitItem);
    menuBar.add(menu);
    setJMenuBar(menuBar);
  }

  class NewBuildAction extends AbstractAction {

    public NewBuildAction(String name) {
      super(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      String[] possibleValues = {"Terran", "Protoss", "Zerg"};
      int optionSelected = JOptionPane.showOptionDialog(MainFrame.this,
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
    List<Unit> units = unitMapper.findByRaceName(buildRace);
    JLabel searchLabel = new JLabel("Search");
    final JComboBox<Unit> searchTextField = new JComboBox<Unit>(units.toArray(new Unit[units.size()]));
    AutoCompleteDecorator.decorate(searchTextField);

    searchTextField.getEditor().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        Unit selectedObject = (Unit) searchTextField.getSelectedItem();
        CustomizeObjectDialog customizeObjectDialog = new CustomizeObjectDialog(MainFrame.this, selectedObject.name);
        customizeObjectDialog.setOnDoneListener(onDoneListener);
        customizeObjectDialog.setVisible(true);
      }
    });

    mainPanel.add(searchLabel);
    mainPanel.add(searchTextField, "wrap");

    String[] columnNames = {"Name", "Type",
            "Quantity",
            "Time"};

    Object[][] data = {
            {"SCV", "Unit", 1, 3},
            {"SCV", "Unit", 1, 12},
            {"Supply depot", "Building", 1, 22}
    };

    JXTable table = new JXTable(data, columnNames);
    table.setFillsViewportHeight(true);
    table.setColumnControlVisible(true);

    Highlighter alternateHighlighter = HighlighterFactory.createAlternateStriping();
    table.addHighlighter(alternateHighlighter);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setMinimumSize(new Dimension(600, 300));
    mainPanel.add(scrollPane, "span");
    validate();
  }
}
