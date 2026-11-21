package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.dto.CommentForm;
import ru.itmo.wp.repository.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void save(User user, Post post, CommentForm commentForm) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setText(commentForm.getText());
        comment.setPost(post);
        commentRepository.save(comment);
    }
}
