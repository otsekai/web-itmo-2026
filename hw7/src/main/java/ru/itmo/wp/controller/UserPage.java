package ru.itmo.wp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.service.UserService;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{stringId}")
    public String getUserProfile(Model model, @PathVariable String stringId, HttpSession httpSession) {
        long id;
        try {
            id = Long.parseLong(stringId);
        } catch (final NumberFormatException e) {
            setMessage(httpSession, "Wrong argument for get user: [" + e + "]");
            return "UserPage";
        }
        model.addAttribute("profileUser", userService.findById(id));
        return "UserPage";
    }
}
