package edu.upc.talent.swqa.jdbc;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ConnectionScope {
  private final Connection connection;

  public ConnectionScope(final Connection connection) {
    this.connection = connection;
  }

  public <A> List<A> select(final String sql, final RowReader<A> reader, final Param... params) {
    try (final PreparedStatement stmt = connection.prepareStatement(sql)) {
      for (int i = 0; i < params.length; i++) params[i].set(stmt, i + 1);
      try (final ResultSet rs = stmt.executeQuery()) {
        final List<A> res = new ArrayList<>();
        while (rs.next()) {
          res.add(reader.apply(new ResultSetView(rs)));
        }
        return Collections.unmodifiableList(res);
      }
    } catch (final SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public int update(final String sql, final Param... params) {
    try (final PreparedStatement stmt = connection.prepareStatement(sql)) {
      for (int i = 0; i < params.length; i++) params[i].set(stmt, i + 1);
      return stmt.executeUpdate();
    } catch (final SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public <K> K insertAndGetKey(final String sql, final RowReader<K> keyReader, final Param... params) {
    try (final PreparedStatement stmt = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
      for (int i = 0; i < params.length; i++) params[i].set(stmt, i + 1);
      stmt.executeUpdate();
      try (final ResultSet keys = stmt.getGeneratedKeys()) {
        keys.next();
        return keyReader.apply(new ResultSetView(keys));
      }
    } catch (final SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public <A> A selectOne(final String sql, final RowReader<A> reader, final Param... params) {
    try (final PreparedStatement stmt = connection.prepareStatement(sql)) {
      for (int i = 0; i < params.length; i++) params[i].set(stmt, i + 1);
      try (final ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) throw new SQLException("No rows returned");
        final A result = reader.apply(new ResultSetView(rs));
        if (rs.next()) throw new SQLException("More than one row returned");
        return result;
      }
    } catch (final SQLException ex) {
      throw new RuntimeException(ex);
    }
  }
}
