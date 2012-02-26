package com.naughtyspirit.desktop.sc2bog.model.db.entity;

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

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-02-2012
 */
public class BuildItem extends BaseEntity {

  public int buildOrderId;
  public int gameObjectId;
  public String gameObjectType;
  public int quantity;
  public int time;

  @Override
  public GameObject.Type getType() {
    return GameObject.Type.BUILD_ITEM;
  }

  public static BuildItem of(int id, int buildOrderId, int gameObjectId, String gameObjectType, int quantity, int time) {
    BuildItem buildItem = new BuildItem();
    buildItem.id = id;
    buildItem.buildOrderId = buildOrderId;
    buildItem.gameObjectId = gameObjectId;
    buildItem.gameObjectType = gameObjectType;
    buildItem.quantity = quantity;
    buildItem.time = time;
    return buildItem;
  }
}