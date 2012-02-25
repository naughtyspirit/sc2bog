package com.naughtyspirit.desktop.sc2bog.ui.table;

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

import com.naughtyspirit.desktop.sc2bog.model.GameObject;
import com.naughtyspirit.desktop.sc2bog.ui.table.model.BuildOrderModel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import javax.swing.*;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-02-2012
 */
public class BuildOrderTable extends JXTable {

  private BuildOrderModel buildOrderModel = new BuildOrderModel();

  public BuildOrderTable() {
    setFillsViewportHeight(true);
    setColumnControlVisible(true);

    Highlighter alternateHighlighter = HighlighterFactory.createAlternateStriping();
    addHighlighter(alternateHighlighter);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setModel(buildOrderModel);
  }

  public void addRow(GameObject row) {
    buildOrderModel.addRow(row);
  }
}
