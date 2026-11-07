package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BasicRepositoryImpl {
    protected final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @SuppressWarnings("SqlSourceToSinkFlow")
    protected <T> T findOne(String sql, RowMapper<T> mapper, Object... args) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setArgs(statement, args);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next() ? mapper.map(resultSet) : null;
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't execute query: sql=[" + sql + "].", e);
        }
    }

    @SuppressWarnings("SqlSourceToSinkFlow")
    protected <T> List<T> findAll(String sql, RowMapper<T> mapper, Object... args) {
        List<T> result = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setArgs(statement, args);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(mapper.map(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't execute query: sql=[" + sql + "].", e);
        }

        return result;
    }

    @SuppressWarnings("SqlSourceToSinkFlow")
    protected long save(String sql, Object... args) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS)) {
                setArgs(statement, args);

                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't execute query: sql=[" + sql + "].");
                }

                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getLong(1);
                    }
                    throw new RepositoryException("No generated key found: sql=[" + sql + "].");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't execute query: sql=[" + sql + "].", e);
        }
    }

    protected int count(String sql, Object... args) {
        Integer result = findOne(sql, resultSet -> resultSet.getInt(1), args);
        return result != null ? result : 0;
    }

    private void setArgs(PreparedStatement statement, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            statement.setObject(i + 1, args[i]);
        }
    }

    @FunctionalInterface
    protected interface  RowMapper<T> {
        T map(ResultSet resultSet) throws SQLException;
    }
}
