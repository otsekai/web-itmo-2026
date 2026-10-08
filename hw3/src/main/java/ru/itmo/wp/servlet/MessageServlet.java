package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MessageServlet extends HttpServlet {

    private class Message {
        private final String user;
        private final String text;
        private final transient long order;

        public Message(String user, String text, long order) {
            this.user = user;
            this.text = text;
            this.order = order;
        }

        public Long getOrder() {
            return order;
        }
    }

    private final List<Message> messages = new CopyOnWriteArrayList<>();
    private final AtomicLong counter = new AtomicLong();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        switch (uri) {
            case "/message/auth":
                handleAuth(request, response);
                break;
            case "/message/findAll":
                handleFindAll(response);
                break;
            case "/message/add":
                handleAdd(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }

        response.getWriter().flush();
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String text = request.getParameter("text");
        HttpSession session = request.getSession();
        String user = session == null ? null : (String) session.getAttribute("user");

        if (user != null && !user.isEmpty() && text != null) {
            messages.add(new Message(user, text, counter.incrementAndGet()));
        }

        response.getWriter().print("{}");
    }

    private void handleFindAll(HttpServletResponse response) throws IOException {
        response.getWriter().print(
                gson.toJson(
                        messages.stream()
                                .sorted(Comparator.comparingLong(Message::getOrder))
                                .collect(Collectors.toList())
                )
        );
    }

    private void handleAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("user");
        HttpSession session = request.getSession();

        if (user != null) {
            session.setAttribute("user", user);
        }

        String currentUser = (String) session.getAttribute("user");
        if (currentUser == null) {
            currentUser = "";
        }

        response.getWriter().print(gson.toJson(currentUser));
    }
}
