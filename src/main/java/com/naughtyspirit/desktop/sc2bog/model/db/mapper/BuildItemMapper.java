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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 26-02-2012
 */
public class BuildItemMapper extends BaseMapper<BuildItem> {

  @Override
  protected void onEachResult(ResultSet resultSet, List<BuildItem> resultList) throws SQLException {
    BuildItem buildItem = BuildItem.of(resultSet.getInt("id"), resultSet.getInt("build_order_id"),
            resultSet.getInt("game_object_id"), resultSet.getString("game_object_type"), resultSet.getInt("quantity"), resultSet.getInt("time"));
    resultList.add(buildItem);
  }

  @Override
  protected void onResult(ResultSet resultSet, BuildItem entityInstance) throws SQLException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public void save(BuildItem buildItem) {
    try {
      PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO build_item VALUES (default, ?, ?, ?, ?, ?)");
      preparedStatement.setInt(1, buildItem.buildOrderId);
      preparedStatement.setInt(2, buildItem.gameObjectId);
      preparedStatement.setString(3, buildItem.gameObjectType);
      preparedStatement.setInt(4, buildItem.quantity);
      preparedStatement.setInt(5, buildItem.time);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
  }

  public void delete(BuildItem buildItem) {
    try {
      PreparedStatement preparedStatement = connection
              .prepareStatement("delete from build_item where id= ?");
      preparedStatement.setInt(1, buildItem.id);
      preparedStatement.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<BuildItem> findByBuildOrderId(int id) {
    return selectAll("SELECT * FROM build_item WHERE build_order_id = " + id + " ORDER BY id", Lists.<BuildItem>newArrayList());
  }
}
