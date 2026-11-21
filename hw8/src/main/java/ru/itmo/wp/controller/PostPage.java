package ru.itmo.wp.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.dto.CommentForm;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

@Controller
public class PostPage extends Page {
    private final PostService postService;
    private final CommentService commentService;

    public PostPage(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String getPost(Model model, @PathVariable Long id) {
        model.addAttribute("foundPost", findPostOrThrow(id));
        model.addAttribute("commentForm", new CommentForm());
        return "PostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/post/{id}")
    public String writeCommentPost(
            @PathVariable Long id,
            @Valid @ModelAttribute("commentForm") CommentForm commentForm,
            BindingResult bindingResult,
            Model model,
            HttpSession httpSession
    ) {
        Post post = findPostOrThrow(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("foundPost", post);
            return "PostPage";
        }

        commentService.save(getUser(httpSession), post, commentForm);
        putMessage(httpSession, "You published a new comment");
        return "redirect:/post/" + post.getId();
    }

    private Post findPostOrThrow(Long id) {
        Post post = postService.findById(id);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        return post;
    }
}
