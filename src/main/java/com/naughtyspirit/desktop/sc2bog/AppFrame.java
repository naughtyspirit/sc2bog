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

import com.google.inject.Injector;
import com.naughtyspirit.desktop.sc2bog.injection.annotation.MainPanel;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.BuildItem;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.BuildOrder;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.Race;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.BuildOrderMapper;
import com.naughtyspirit.desktop.sc2bog.ui.AppMenu;
import com.naughtyspirit.desktop.sc2bog.ui.NewBuildDialog;
import com.naughtyspirit.desktop.sc2bog.ui.view.AppView;
import com.naughtyspirit.desktop.sc2bog.ui.view.BuildOrderListView;
import com.naughtyspirit.desktop.sc2bog.ui.view.BuildOrderView;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 22-02-2012
 */
@Singleton
public class AppFrame extends JFrame implements NewBuildDialog.OnOkListener, AppMenu.OnNewListener, AppMenu.OnExitListener, BuildOrderView.OnSaveListener {

  @Inject
  Injector injector;

  private int startTime = 1;

  private BuildOrder buildOrder;

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

  @Override
  public void onNew() {
    NewBuildDialog newBuildDialog = injector.getInstance(NewBuildDialog.class);
    newBuildDialog.setOnOkListener(this);
    newBuildDialog.display();
  }

  @Override
  public void onExit() {
    System.exit(0);
  }

  public void display() {
    displayBuildOrderList();
    setVisible(true);
  }

  private void displayBuildOrderList() {
    BuildOrderListView view = injector.getInstance(BuildOrderListView.class);
    displayView(view);
  }

  private void displayView(AppView view) {
    getContentPane().removeAll();
    repaint();
    view.display();
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

  public void displayBuildOrderView(Race race) {
    BuildOrderView buildOrderView = injector.getInstance(BuildOrderView.class);
    buildOrderView.setRace(race);
    buildOrderView.setOnSaveListener(this);
    displayView(buildOrderView);
  }

  @Override
  public void onSave(List<BuildItem> buildItems) {
    buildOrder.setBuildItems(buildItems);
    buildOrderMapper.save(buildOrder);
    displayBuildOrderList();
  }
}
