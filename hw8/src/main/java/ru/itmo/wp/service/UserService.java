package ru.itmo.wp.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.dto.PostForm;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.RoleRepository;
import ru.itmo.wp.repository.TagRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TagService tagService;

    /**
     * @noinspection FieldCanBeLocal, unused
     */
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, TagService tagService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.tagService = tagService;

        this.roleRepository = roleRepository;
        for (Role.Name name : Role.Name.values()) {
            if (!roleRepository.existsByName(name)) {
                roleRepository.save(new Role(name));
            }
        }
    }

    public User register(UserCredentials userCredentials) {
        User user = new User();
        user.setLogin(userCredentials.getLogin());
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), userCredentials.getLogin(), userCredentials.getPassword());
        return user;
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public User findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    @Transactional
    public void writePost(User user, Set<String> tagNames, PostForm postForm) {
        Set<Tag> tags = new LinkedHashSet<>();
        for (String name : tagNames) {
            Tag tag = tagService.findByName(name);
            if (tag == null) {
                tag = tagService.save(name);
            }
            if (tag == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something bad happened");
            }
            tags.add(tag);
        }

        Post post = new Post();
        post.setUser(user);
        post.setTitle(postForm.getTitle());
        post.setText(postForm.getText());
        post.setTags(tags);

        user.addPost(post);
        userRepository.save(user);
    }
}
