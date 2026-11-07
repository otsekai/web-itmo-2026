package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused"})
public class TalksPage extends BasePage {
    private final TalkService talkService = new TalkService();

    public void before(HttpServletRequest request, Map<String, Object> view) throws RedirectException {
        super.before(request, view);

        User user = super.getUser();
        if (user == null) {
            throw new RedirectException("/index");
        }

        List<User> users = super.userService.findAll();
        Map<String, String> idToLogin = new HashMap<>();
        for (User currentUser : users) {
            idToLogin.put(String.valueOf(currentUser.getId()), currentUser.getLogin());
        }

        view.put("users", users);
        view.put("mapper", idToLogin);
        view.put("talks", talkService.findAllById(user.getId()));
    }

    private void sendMessage(HttpServletRequest request, Map<String, Object> map) throws ValidationException {
        long sourceUserId = super.getUser().getId();

        talkService.validateTalk(sourceUserId, request.getParameter("targetId"), request.getParameter("text"));

        long targetId = Long.parseLong(request.getParameter("targetId"));
        Talk talk = new Talk();
        talk.setSourceUserId(sourceUserId);
        talk.setTargetUserId(targetId);
        talk.setText(request.getParameter("text"));

        talkService.saveMessage(talk);
        throw new RedirectException("/talks");
    }
}
