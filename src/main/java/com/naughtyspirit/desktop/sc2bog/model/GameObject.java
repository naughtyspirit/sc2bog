package com.naughtyspirit.desktop.sc2bog.model;

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

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 22-02-2012
 */
public class GameObject {

  private final int id;
  private final String name;
  private final Type type;
  private final int quantity;
  private final int time;

  public GameObject(int id, String name, Type type, int quantity, int time) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.quantity = quantity;
    this.time = time;
  }

  public int getId() {
    return id;
  }

  public enum Type {
    RACE, UNIT, BUILDING, UPGRADE, ABILITY, BUILD_ORDER, BUILD_ITEM
  }

  public String getName() {
    return name;
  }

  public Type getType() {
    return type;
  }

  public int getQuantity() {
    return quantity;
  }

  public int getTime() {
    return time;
  }
}
