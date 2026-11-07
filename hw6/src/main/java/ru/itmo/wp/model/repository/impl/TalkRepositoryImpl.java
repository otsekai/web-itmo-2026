package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.repository.TalkRepository;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class TalkRepositoryImpl extends BasicRepositoryImpl implements TalkRepository {
    @Override
    public void save(Talk talk) {
        long id = save(
                "INSERT INTO `Talk` (`sourceUserId`, `targetUserId`, `text`, `creationTime`) " +
                        "VALUES (?, ?, ?, NOW())",
                talk.getSourceUserId(),
                talk.getTargetUserId(),
                talk.getText()
        );

        talk.setId(id);
        talk.setCreationTime(findCreationTimeById(id));
    }

    @Override
    public List<Talk> findAllById(long userId) {
        return findAll("SELECT * FROM Talk " +
                "WHERE sourceUserId=? OR targetUserId=? ORDER BY creationTime",
                this::toTalk,
                userId,
                userId
        );
    }

    private Talk toTalk(ResultSet resultSet) throws SQLException {
        Talk talk = new Talk();

        talk.setId(resultSet.getLong("id"));
        talk.setSourceUserId(resultSet.getLong("sourceUserId"));
        talk.setTargetUserId(resultSet.getLong("targetUserId"));
        talk.setText(resultSet.getString("text"));
        talk.setCreationTime(resultSet.getTimestamp("creationTime"));

        return talk;
    }

    private Date findCreationTimeById(long id) {
        return findOne("SELECT creationTime FROM Talk WHERE id=?",
                resultSet -> resultSet.getTimestamp(1),
                id
        );
    }
}
