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
import com.naughtyspirit.desktop.sc2bog.model.db.entity.Ability;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.Race;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-02-2012
 */
public class AbilityMapper extends BaseMapper<Ability> {

  private RaceMapper raceMapper;

  @Inject
  public void setRaceMapper(RaceMapper raceMapper) {
    this.raceMapper = raceMapper;
  }

  public List<Ability> findByRace(Race race) {
    return selectAll("SELECT * FROM ability WHERE race_id = " + race.id, Lists.<Ability>newArrayList());
  }

  public List<Ability> findByRaceName(String name) {
    Race race = raceMapper.findByName(name);
    return findByRace(race);
  }

  @Override
  protected void onEachResult(ResultSet resultSet, List<Ability> resultList) throws SQLException {
    resultList.add(Ability.of(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("build_time")));
  }

  @Override
  protected void onResult(ResultSet resultSet, Ability entityInstance) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
