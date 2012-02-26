package com.naughtyspirit.desktop.sc2bog.ui.view;

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

import com.google.inject.Injector;
import com.naughtyspirit.desktop.sc2bog.injection.annotation.MainFrame;
import com.naughtyspirit.desktop.sc2bog.model.GameObject;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.BaseEntity;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.BuildItem;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.Race;
import com.naughtyspirit.desktop.sc2bog.service.BuildOrderService;
import com.naughtyspirit.desktop.sc2bog.ui.CustomizeObjectDialog;
import com.naughtyspirit.desktop.sc2bog.ui.table.BuildOrderTable;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 26-02-2012
 */
public class BuildOrderView implements AppView {

  private BuildOrderService buildOrderService;
  private BuildOrderTable buildOrderTable;

  @Inject
  Injector injector;

  private OnSaveListener onSaveListener;
  private Race race;
  private JFrame owner;

  @Inject
  public void setBuildOrderService(BuildOrderService buildOrderService) {
    this.buildOrderService = buildOrderService;
  }

  private int startTime = 1;

  @Inject
  public BuildOrderView(@MainFrame JFrame owner) {
    this.owner = owner;
  }

  public void setOnSaveListener(OnSaveListener onSaveListener) {
    this.onSaveListener = onSaveListener;
  }

  public void setRace(Race race) {
    this.race = race;
  }

  @Override
  public void display() {
    buildOrderTable = new BuildOrderTable();
    buildOrderTable.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
          buildOrderTable.removeSelectedRow();
        }
      }
    });
    List<BaseEntity> entities = buildOrderService.entitiesForAutocompletion(race);
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

    owner.add(searchLabel);
    owner.add(searchTextField, "wrap");

    JScrollPane scrollPane = new JScrollPane(buildOrderTable);
    scrollPane.setMinimumSize(new Dimension(600, 300));
    owner.add(scrollPane, "span, wrap");
    JButton saveButton = new JButton();
    saveButton.setAction(new AbstractAction("Save") {
      @Override
      public void actionPerformed(ActionEvent e) {
        onSaveListener.onSave(buildOrderTable.getBuildItems());
      }
    });
    owner.add(saveButton);
  }

  public interface OnSaveListener {

    void onSave(List<BuildItem> buildItems);
  }

  private CustomizeObjectDialog.OnDoneListener onDoneListener = new CustomizeObjectDialog.OnDoneListener() {
    @Override
    public void onDone(GameObject gameObject) {
      buildOrderTable.addRow(gameObject);
      startTime = gameObject.getTime() + 1;
    }
  };
}
