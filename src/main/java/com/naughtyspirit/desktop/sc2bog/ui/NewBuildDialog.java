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
import com.naughtyspirit.desktop.sc2bog.model.db.entity.Race;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.RaceMapper;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 26-02-2012
 */
public class NewBuildDialog extends JDialog {

  private final JButton okButton;
  private JComboBox<Race> raceList;
  private RaceMapper raceMapper;
  private JTextField name;

  @Inject
  public NewBuildDialog(@MainFrame JFrame owner, @MainPanel JPanel mainPanel) {
    super(owner, true);
    centerAndResize();
    setContentPane(mainPanel);
    okButton = new JButton();
  }

  @Inject
  public void setRaceMapper(RaceMapper raceMapper) {
    this.raceMapper = raceMapper;
  }

  private void centerAndResize() {
    setSize(getOwner().getWidth() / 2, getOwner().getHeight() / 2);
    setLocation(getOwner().getX() + getOwner().getWidth() / 4, getOwner().getY() + getOwner().getHeight() / 4);
  }


  public interface OnOkListener {

    void onDone(String name, Race race);
  }

  public void display() {

    JLabel createBuildLabel = new JLabel("Create new Build Order");
    add(createBuildLabel, "wrap");
    JLabel nameLabel = new JLabel("Name");
    add(nameLabel);
    name = new JTextField(8);
    add(name, "wrap");

    JLabel raceLabel = new JLabel("Race");
    add(raceLabel);

    List<Race> races = raceMapper.findAll();
    raceList = new JComboBox<Race>(races.toArray(new Race[races.size()]));
    raceList.setSelectedIndex(0);
    add(raceList, "wrap");
    add(okButton);
    setVisible(true);
  }

  public void setOnOkListener(final OnOkListener onOkListener) {
    okButton.setAction(new AbstractAction("Ok") {
      @Override
      public void actionPerformed(ActionEvent e) {
        onOkListener.onDone(name.getText(), raceList.getItemAt(raceList.getSelectedIndex()));
        setVisible(false);
      }
    });
  }
}
