package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.TagRepository;

@Service
public class TagService {
    private final TagRepository tagRepository;


    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag save(String name) {
        if (name == null) {
            return null;
        }
        Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }

    public Tag findByName(String name) {
        return name == null ? null : tagRepository.findByName(name);
    }
}
