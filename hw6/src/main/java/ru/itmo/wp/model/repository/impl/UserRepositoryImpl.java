package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;
import java.util.List;

public class UserRepositoryImpl extends BasicRepositoryImpl implements UserRepository {
    @Override
    public User find(long id) {
        return findBySmth(new String[]{"id"}, id);
    }

    @Override
    public User findByLogin(String login) {
        return findBySmth(new String[]{"login"}, login);
    }

    @Override
    public User findByEmail(String email) {
        return findBySmth(new String[]{"email"}, email);
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return findBySmth(new String[]{"login", "passwordSha"}, login, passwordSha);
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return findBySmth(new String[]{"email", "passwordSha"}, email, passwordSha);
    }

    @Override
    public List<User> findAll() {
        return findAll("SELECT * FROM User ORDER BY id DESC", this::toUser);
    }

    @Override
    public int findCount() {
        return count("SELECT COUNT(*) FROM User");
    }

    @Override
    public void save(User user, String passwordSha) {
        long id = save(
                "INSERT INTO `User` (`login`, `email`, `passwordSha`, `creationTime`) " +
                        "VALUES (?, ?, ?, NOW())",
                user.getLogin(),
                user.getEmail(),
                passwordSha
        );

        user.setId(id);
        user.setCreationTime(find(id).getCreationTime());
    }

    private User toUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getLong("id"));
        user.setLogin(resultSet.getString("login"));
        user.setEmail(resultSet.getString("email"));
        user.setCreationTime(resultSet.getTimestamp("creationTime"));

        return user;
    }

    private User findBySmth(String[] argsName, Object... args) {
        StringBuilder sql = new StringBuilder("SELECT * FROM User WHERE ");
        for (int i = 0; i < argsName.length; i++) {
            sql.append(argsName[i]).append("=? ");
            if (i + 1 < argsName.length) {
                sql.append("AND ");
            }
        }
        return findOne(sql.toString(), this::toUser, args);
    }
}
