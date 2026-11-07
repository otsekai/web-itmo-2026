package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class BasePage {
    private static final String USER_KEY = "user";
    private static final String MESSAGE_KEY = "message";

    protected final UserService userService = new UserService();
    protected final EventService eventService = new EventService();
    private HttpServletRequest request;

    public void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;

        User user = getUser();
        if (user != null) {
            view.put(USER_KEY, user);
        }

        view.put("userCount", userService.findCount());

        String message = getMessage();
        if (!Strings.isNullOrEmpty(message)) {
            view.put(MESSAGE_KEY, message);
            removeMessage();
        }
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        // nothing now
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        // nothing now
    }

    protected User getUser() {
        return (User) request.getSession().getAttribute(USER_KEY);
    }

    protected String getMessage() {
        return (String) request.getSession().getAttribute(MESSAGE_KEY);
    }

    protected void setUser(User user) {
        request.getSession().setAttribute(USER_KEY, user);
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute(MESSAGE_KEY, message);
    }

    protected void removeUser() {
        request.getSession().removeAttribute(USER_KEY);
    }

    protected void removeMessage() {
        request.getSession().removeAttribute(MESSAGE_KEY);
    }
}
