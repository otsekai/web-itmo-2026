package ru.itmo.wp.controller;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.dto.PostForm;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;

    public WritePostPage(UserService userService) {
        this.userService = userService;
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "WritePostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("post") PostForm postForm,
                                BindingResult bindingResult,
                                HttpSession httpSession) {
        Set<String> tags = new LinkedHashSet<>();
        String rawTags = postForm.getTags() == null ? "" : postForm.getTags();

        for (String name : rawTags.split("\\s+")) {
            if (name.isEmpty()) {
                continue;
            }
            if (!name.matches("[a-z]{1,255}")) {
                bindingResult.rejectValue("tags", "tags.invalid-format-value",
                        "Tags must be form 1 to 255 letters and of Latin symbols");
                break;
            }
            tags.add(name);
        }

        if (bindingResult.hasErrors()) {
            return "WritePostPage";
        }

        userService.writePost(getUser(httpSession), tags, postForm);
        putMessage(httpSession, "You published new post");

        return "redirect:/myPosts";
    }
}
