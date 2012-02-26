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

import com.naughtyspirit.desktop.sc2bog.injection.annotation.MainFrame;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.BuildOrder;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.BuildOrderMapper;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 26-02-2012
 */
public class BuildOrderListView implements AppView {


  private final JFrame owner;

  private BuildOrderMapper buildOrderMapper;

  @Inject
  public void setBuildOrderMapper(BuildOrderMapper buildOrderMapper) {
    this.buildOrderMapper = buildOrderMapper;
  }

  @Inject
  public BuildOrderListView(@MainFrame JFrame owner) {
    this.owner = owner;
  }

  @Override
  public void display() {
    JLabel buildOrderListLabel = new JLabel("Saved Build Orders");
    owner.add(buildOrderListLabel, "wrap");
    final DefaultListModel<BuildOrder> buildOrderListModel = new DefaultListModel<BuildOrder>();
    List<BuildOrder> buildOrders = buildOrderMapper.findAll();
    for (BuildOrder buildOrder : buildOrders) {
      buildOrderListModel.addElement(buildOrder);
    }
    final JList<BuildOrder> list = new JList<BuildOrder>(buildOrderListModel);
    list.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
          BuildOrder buildOrder = list.getSelectedValue();
          buildOrderMapper.delete(buildOrder);
          buildOrderListModel.remove(list.getSelectedIndex());
          list.revalidate();
        }
      }
    });
    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    list.setLayoutOrientation(JList.VERTICAL);
    list.setVisibleRowCount(-1);
    JScrollPane listScroll = new JScrollPane(list);
    listScroll.setMinimumSize(new Dimension(200, 250));
    owner.add(listScroll);
  }
}
