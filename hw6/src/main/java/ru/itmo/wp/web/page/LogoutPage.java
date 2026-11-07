package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.Type;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class LogoutPage extends BasePage {
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        User user = super.getUser();

        if (user != null) {
            Event event = new Event();
            event.setUserId(user.getId());
            event.setType(Type.LOGOUT);
            super.eventService.registerEvent(event);
        }

        super.removeUser();
        super.setMessage("Good bye. Hope to see you soon!");

        throw new RedirectException("/index");
    }
}
