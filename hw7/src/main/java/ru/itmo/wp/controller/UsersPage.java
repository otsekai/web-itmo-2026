package ru.itmo.wp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @Transactional
    @PostMapping("/users/disabled")
    public String changeDisablement(
            HttpSession httpSession,
            @RequestParam Long id,
            @RequestParam boolean disabled
    ) {
        User user = getUser(httpSession);
        if (user == null || !user.isAdmin() || user.isDisabled()) {
            setMessage(httpSession, "You cannot toggle disablement anyone, you have no rights");
            return "redirect:/users/all";
        }

        if (disabled && id.equals(user.getId())) {
            setMessage(httpSession, "You cannot disable yourself");
            return "redirect:/users/all";
        }

        if (!userService.changeDisablement(id, disabled)) {
            setMessage(httpSession, "No user with such id");
        }
        return "redirect:/users/all";
    }
}
