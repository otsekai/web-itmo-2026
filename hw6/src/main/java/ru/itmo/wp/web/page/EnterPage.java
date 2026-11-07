package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.Type;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class EnterPage extends BasePage {
    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");

        User user = super.userService.validateEnter(loginOrEmail, password);
        super.setUser(user);
        super.setMessage("Hello, " + user.getLogin());

        Event event = new Event();
        event.setUserId(user.getId());
        event.setType(Type.ENTER);
        super.eventService.registerEvent(event);

        throw new RedirectException("/index");
    }
}
