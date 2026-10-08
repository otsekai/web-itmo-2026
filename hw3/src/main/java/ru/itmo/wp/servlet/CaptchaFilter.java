package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter implements Filter {
    private final Random random = new Random();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        if (Boolean.TRUE.equals(session.getAttribute("captchaEnabled"))) {
            chain.doFilter(request, response);
            return;
        }

        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String answer = request.getParameter("captcha");
        if (answer != null) {
            String expected = (String) session.getAttribute("captcha");
            if (expected != null && expected.equals(answer)) {
                session.setAttribute("captchaEnabled", Boolean.TRUE);
            }

            response.sendRedirect(request.getRequestURI());
            return;
        }

        showCaptcha(session, response);
    }

    private void showCaptcha(HttpSession session, HttpServletResponse response) throws IOException {
        String generated = generateCaptchaInt();

        session.setAttribute("captcha", generated);

        String base64 = Base64.getEncoder().encodeToString(
                ImageUtils.toPng(generated)
        );

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Captcha</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form method=\"GET\" style=\"display: flex; flex-direction: column; gap: 100%\">\n" +
                "    <img src=\"data:image/png;base64," + base64 + "\" alt=\"captcha\"/>\n" +
                "    <input name=\"captcha\"/>\n" +
                "    <input type=\"submit\" value=\"Submit\"/>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>");
        response.getWriter().flush();
    }

    private String generateCaptchaInt() {
        return String.valueOf(100 + random.nextInt(900));
    }

    /*
    Я только потом прочитал что нужно генерировать числа, поэтому у меня тут ещё и генератор случайных строк
     */
    private String generateCaptchaString() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
