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

import com.google.common.base.Optional;
import com.naughtyspirit.desktop.sc2bog.model.GameObject;
import com.naughtyspirit.desktop.sc2bog.ui.listener.OnDoneListener;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 22-02-2012
 */
public class CustomizeObjectDialog extends JDialog {

  private Optional<OnDoneListener> doneListener;
  private JButton doneButton;

  public CustomizeObjectDialog(Frame owner, String selectedObject) {
    super(owner, "Customize object", true);

    setSize(owner.getWidth() / 2, owner.getHeight() / 2);
    setLocation(owner.getX() + owner.getWidth() / 4, owner.getY() + owner.getHeight() / 4);

    JPanel mainPanel = new JPanel(new MigLayout("center", "", "[100]"));
    setContentPane(mainPanel);
    mainPanel.add(new JLabel(selectedObject));
    JSpinner quantitySpinner = new JSpinner();
    quantitySpinner.setMinimumSize(new Dimension(40, 5));
    quantitySpinner.setValue(1);
    quantitySpinner.setEditor(new JSpinner.NumberEditor(quantitySpinner, "#"));
    mainPanel.add(quantitySpinner);
    doneButton = new JButton();
    mainPanel.add(doneButton);
  }


  @Override
  public void setVisible(boolean b) {
    if (b == true && doneListener.isPresent()) {
      doneButton.setAction(new AbstractAction("Done") {
        @Override
        public void actionPerformed(ActionEvent e) {
          GameObject gameObject = new GameObject("SCV", GameObject.Type.UNIT, 50, 0, 0, 1, 50);
          doneListener.get().onDone(gameObject);
          setVisible(false);
        }
      });
    }
    super.setVisible(b);
  }

  public void setOnDoneListener(OnDoneListener onDoneListener) {
    this.doneListener = Optional.of(onDoneListener);
  }
}
