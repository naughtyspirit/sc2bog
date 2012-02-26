package com.naughtyspirit.desktop.sc2bog.ui;

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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 26-02-2012
 */
public class AppMenu extends JMenuBar {

  private OnNewListener onNewListener;
  private OnExitListener onExitListener;

  public void setOnExitListener(OnExitListener onExitListener) {
    this.onExitListener = onExitListener;
  }

  public void setOnNewListener(OnNewListener onNewListener) {
    this.onNewListener = onNewListener;
  }

  public interface OnNewListener {

    void onNew();
  }

  public interface OnExitListener {

    void onExit();
  }

  public AppMenu() {
    JMenu menu = new JMenu("File");
    JMenuItem newItem = new JMenuItem("New");
    newItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onNewListener.onNew();
      }
    });
    menu.add(newItem);
    JMenuItem exitItem = new JMenuItem("Exit");
    exitItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onExitListener.onExit();
      }
    });
    menu.add(exitItem);
    add(menu);
  }

}
