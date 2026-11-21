package ru.itmo.wp.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.repository.NoticeRepository;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<Notice> findAll() {
        return noticeRepository.findAllByOrderByIdDesc();
    }

    public boolean checkCurses(String content) {
        return content.contains("curse");
    }

    public void save(@Valid NoticeCredentials noticeForm) {
        Notice notice = new Notice();
        notice.setContent(noticeForm.getContent());
        noticeRepository.save(notice);
    }
}
