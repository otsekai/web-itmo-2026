package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public void validateTalk(long sourceUserId, String targetId, String text) throws ValidationException {
        if (Strings.isNullOrEmpty(targetId)) {
            throw new ValidationException("Target id is null or empty");
        }

        long targetUserId;
        try {
            targetUserId = Long.parseLong(targetId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Target id is invalid");
        }

        if (userRepository.find(targetUserId) == null) {
            throw new ValidationException("Target user is not found by id");
        }
        if (targetUserId == sourceUserId) {
            throw new ValidationException("You can't be target for sending message");
        }

        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException("Text is null or empty");
        }
        if (text.length() < 3) {
            throw new ValidationException("Text is too short");
        }
        if (text.length() > 300) {
            throw new ValidationException("Text is too long");
        }
    }

    public void saveMessage(Talk talk) {
        talkRepository.save(talk);
    }

    public List<Talk> findAllById(long userId) {
        return talkRepository.findAllById(userId);
    }
}
