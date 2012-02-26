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

import com.naughtyspirit.desktop.sc2bog.injection.annotation.MainFrame;
import com.naughtyspirit.desktop.sc2bog.injection.annotation.MainPanel;
import com.naughtyspirit.desktop.sc2bog.model.GameObject;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.BaseEntity;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 22-02-2012
 */
public class CustomizeObjectDialog extends JDialog {

  private BaseEntity selectedEntity;
  private int startTime;
  private final JButton doneButton;
  private final JSpinner quantitySpinner;
  private JSpinner timeSpinner;

  @Inject
  public CustomizeObjectDialog(@MainFrame JFrame owner, @MainPanel JPanel mainPanel) {
    super(owner, "Customize object", true);
    centerAndResize();
    setContentPane(mainPanel);
    quantitySpinner = createQuantitySpinner();
    doneButton = new JButton();
  }

  private JSpinner createTimeSpinner() {
    JSpinner spinner = new JSpinner();
    spinner.setMinimumSize(new Dimension(40, 5));
    SpinnerNumberModel model = new SpinnerNumberModel(startTime, startTime, 999, 1);
    spinner.setModel(model);
    spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
    return spinner;
  }

  private void centerAndResize() {
    setSize(getOwner().getWidth() / 2, getOwner().getHeight() / 2);
    setLocation(getOwner().getX() + getOwner().getWidth() / 4, getOwner().getY() + getOwner().getHeight() / 4);
  }

  public void setSelectedEntity(BaseEntity entity) {
    this.selectedEntity = entity;
  }

  public void display() {
    add(new JLabel(selectedEntity.name), "wrap 10");
    add(new JLabel("Quantity"));
    add(quantitySpinner, "wrap 10");
    add(new JLabel("Time"));
    timeSpinner = createTimeSpinner();
    add(timeSpinner, "wrap 10");
    add(doneButton);
    setVisible(true);
  }
  public interface OnDoneListener {
    void onDone(GameObject gameObject);
  }

  private JSpinner createQuantitySpinner() {
    JSpinner spinner = new JSpinner();
    spinner.setMinimumSize(new Dimension(40, 5));
    SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 100, 1);
    spinner.setModel(model);
    spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
    return spinner;
  }

  public void setOnDoneListener(final OnDoneListener onDoneListener) {
    doneButton.setAction(new AbstractAction("Done") {
      @Override
      public void actionPerformed(ActionEvent e) {
        GameObject gameObject = new GameObject(selectedEntity.id, selectedEntity.name, selectedEntity.getType(), (Integer) quantitySpinner.getValue(), (Integer) timeSpinner.getValue());
        onDoneListener.onDone(gameObject);
        setVisible(false);
      }
    });
  }

  public void setStartTime(int startTime) {
    this.startTime = startTime;
  }
}
