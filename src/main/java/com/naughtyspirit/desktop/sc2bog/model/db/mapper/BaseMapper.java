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

import com.naughtyspirit.desktop.sc2bog.model.db.entity.BaseEntity;
import com.naughtyspirit.desktop.sc2bog.model.db.mapper.exception.MappingException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 22-02-2012
 */
public abstract class BaseMapper<Entity_Type extends BaseEntity> {

  private Connection connection;

  @Inject
  public void setConnection(Connection connection) {
    this.connection = connection;
  }

  protected List<Entity_Type> selectAll(String sql, int requestMethod, List<Entity_Type> resultList) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);
      while (resultSet.next()) {
        onEachResult(resultSet, resultList, requestMethod);
      }
    } catch (SQLException e) {
      throw new MappingException("selection query execution failed", e);
    }
    return resultList;
  }

  protected Entity_Type selectOne(String sql, int requestMethod, Entity_Type entityInstance) {
    try {
      Statement statement = connection.createStatement();
      System.out.println(sql);
      ResultSet resultSet = statement.executeQuery(sql);
      resultSet.next();
      onResult(resultSet, entityInstance, requestMethod);
      if (resultSet.next()) {
        throw new MappingException("multiple entities returned from selecting one");
      }
    } catch (SQLException e) {
      throw new MappingException("selection query execution failed", e);
    }
    return entityInstance;
  }

  protected abstract void onEachResult(ResultSet resultSet, List<Entity_Type> resultList, int requestMethod) throws SQLException;

  protected abstract void onResult(ResultSet resultSet, Entity_Type entityInstance, int requestMethod) throws SQLException;
}
