package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.*;
import java.util.Date;

public class EventRepositoryImpl extends BasicRepositoryImpl implements EventRepository {
    @Override
    public void save(Event event) {
        long id = save(
                "INSERT INTO `Event` (`userId`, `type`, `creationTime`) " +
                        "VALUES (?, ?, NOW())",
                event.getUserId(),
                String.valueOf(event.getType())
        );

        event.setId(id);
        event.setCreationTime(findCreationTimeById(id));
    }

    private Date findCreationTimeById(long id) {
        return findOne("SELECT creationTime FROM Event WHERE id=?",
                resultSet -> resultSet.getTimestamp(1),
                id
        );
    }
}
