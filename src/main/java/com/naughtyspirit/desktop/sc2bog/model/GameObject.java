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

  private final String name;
  private final Type type;
  private final int minerals;
  private final int gas;
  private final int supply;
  private final int quantity;
  private final int time;

  public enum Type {
    UNIT, BUILDING, UPGRADE, ABILITY
  }

  public GameObject(String name, Type type, int minerals, int gas, int supply, int quantity, int time) {

    this.name = name;
    this.type = type;
    this.minerals = minerals;
    this.gas = gas;
    this.supply = supply;
    this.quantity = quantity;
    this.time = time;
  }

  public String getName() {
    return name;
  }

  public Type getType() {
    return type;
  }

  public int getMinerals() {
    return minerals;
  }

  public int getGas() {
    return gas;
  }

  public int getSupply() {
    return supply;
  }

  public int getQuantity() {
    return quantity;
  }

  public int getTime() {
    return time;
  }
}
