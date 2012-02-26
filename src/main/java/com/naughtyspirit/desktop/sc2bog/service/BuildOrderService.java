package com.naughtyspirit.desktop.sc2bog.service;

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

import com.google.common.collect.Lists;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.*;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.BuildingMapper;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.UnitMapper;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.UpgradeMapper;

import javax.inject.Inject;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 26-02-2012
 */
public class BuildOrderService {

  private UnitMapper unitMapper;

  @Inject
  public void setUnitMapper(UnitMapper unitMapper) {
    this.unitMapper = unitMapper;
  }

  private UpgradeMapper upgradeMapper;

  @Inject
  public void setUpgradeMapper(UpgradeMapper upgradeMapper) {
    this.upgradeMapper = upgradeMapper;
  }

  private BuildingMapper buildingMapper;

  @Inject
  public void setBuildingMapper(BuildingMapper buildingMapper) {
    this.buildingMapper = buildingMapper;
  }

  public List<BaseEntity> entitiesForAutocompletion(Race race) {
    List<Unit> units = unitMapper.findByRaceName(race.name);
    List<Upgrade> upgrades = upgradeMapper.findByRaceName(race.name);
    List<Building> buildings = buildingMapper.findByRaceName(race.name);
    List<BaseEntity> entities = Lists.newArrayList();
    entities.addAll(units);
    entities.addAll(upgrades);
    entities.addAll(buildings);
    return entities;
  }
}
