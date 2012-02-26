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
import com.naughtyspirit.desktop.sc2bog.model.db.entity.BuildItem;
import com.naughtyspirit.desktop.sc2bog.model.db.entity.BuildOrder;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-02-2012
 */
public class BuildOrderMapper extends BaseMapper<BuildOrder> {


  public List<BuildOrder> findAll() {
    return selectAll("SELECT * FROM build_order", Lists.<BuildOrder>newArrayList());
  }

  private BuildItemMapper buildItemMapper;

  @Inject
  public void setBuildItemMapper(BuildItemMapper buildItemMapper) {
    this.buildItemMapper = buildItemMapper;
  }

  @Override
  protected void onEachResult(ResultSet resultSet, List<BuildOrder> resultList) throws SQLException {
    BuildOrder buildOrder = BuildOrder.of(resultSet.getInt("id"), resultSet.getString("name"));
    buildOrder.buildItems = buildItemMapper.findByBuildOrderId(buildOrder.id);
    resultList.add(buildOrder);
  }

  @Override
  protected void onResult(ResultSet resultSet, BuildOrder entityInstance) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public void save(BuildOrder buildOrder) {
    try {
      PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO build_order VALUES (default, ?, ?)", Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, buildOrder.raceId);
      preparedStatement.setString(2, buildOrder.name);
      preparedStatement.executeUpdate();
      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (!generatedKeys.next()) {
        throw new SQLException("Saving failed");
      }
      int buildOrderId = generatedKeys.getInt(1);
      for (BuildItem buildItem : buildOrder.buildItems) {
        buildItem.buildOrderId = buildOrderId;
        buildItemMapper.save(buildItem);
      }
    } catch (SQLException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
  }

  public void delete(BuildOrder buildOrder) {
    try {
      for (BuildItem buildItem : buildOrder.buildItems) {
        buildItemMapper.delete(buildItem);
      }
      PreparedStatement preparedStatement = connection
              .prepareStatement("delete from build_order where id= ?");
      preparedStatement.setInt(1, buildOrder.id);
      preparedStatement.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
