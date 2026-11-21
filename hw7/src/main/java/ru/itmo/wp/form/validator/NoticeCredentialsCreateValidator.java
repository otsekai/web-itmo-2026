package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.service.NoticeService;

@Component
public class NoticeCredentialsCreateValidator implements Validator {
    private final NoticeService noticeService;

    public NoticeCredentialsCreateValidator(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    public boolean supports(Class<?> clazz) {
        return NoticeCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            NoticeCredentials noticeForm = (NoticeCredentials) target;
            if (noticeService.checkCurses(noticeForm.getContent())) {
                errors.rejectValue(
                        "content", "content.curses", "The content cannot contain the word 'curse'");
            }
        }
    }
}