package com.naughtyspirit.desktop.sc2bog.model.db.mapper;

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
import com.naughtyspirit.desktop.sc2bog.model.db.entity.Race;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.Upgrade;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-02-2012
 */
public class UpgradeMapper extends BaseMapper<Upgrade> {

  private static final int FIND_BY_RACE = 1;

  private RaceMapper raceMapper;

  @Inject
  public void setRaceMapper(RaceMapper raceMapper) {
    this.raceMapper = raceMapper;
  }

  public List<Upgrade> findByRace(Race race) {
    return selectAll("SELECT * FROM upgrade WHERE race_id = " + race.id, FIND_BY_RACE, Lists.<Upgrade>newArrayList());
  }

  public List<Upgrade> findByRaceName(String name) {
    Race race = raceMapper.findByName(name);
    return findByRace(race);
  }


  @Override
  protected void onEachResult(ResultSet resultSet, List<Upgrade> resultList, int requestMethod) throws SQLException {
    switch (requestMethod) {
      case FIND_BY_RACE:
        resultList.add(Upgrade.of(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("minerals"), resultSet.getInt("gas"), resultSet.getInt("build_time")));
        break;
    }
  }

  @Override
  protected void onResult(ResultSet resultSet, Upgrade entityInstance, int requestMethod) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
