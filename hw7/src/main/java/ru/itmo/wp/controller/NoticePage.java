package ru.itmo.wp.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.form.validator.NoticeCredentialsCreateValidator;
import ru.itmo.wp.form.validator.UserCredentialsRegisterValidator;
import ru.itmo.wp.service.NoticeService;
import ru.itmo.wp.service.UserService;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;
    private final NoticeCredentialsCreateValidator noticeCredentialsCreateValidator;

    public NoticePage(
            NoticeService noticeService,
            NoticeCredentialsCreateValidator noticeCredentialsCreateValidator
    ) {
        this.noticeService = noticeService;
        this.noticeCredentialsCreateValidator = noticeCredentialsCreateValidator;
    }

    @InitBinder("noticeForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(noticeCredentialsCreateValidator);
    }

    @GetMapping("/notice")
    public String noticeGet(Model model) {
        model.addAttribute("noticeForm", new NoticeCredentials());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String noticePost(@Valid @ModelAttribute("noticeForm") NoticeCredentials noticeForm,
                             BindingResult bindingResult,
                             HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        noticeService.save(noticeForm);
        setMessage(httpSession, "Congrats, you have made a notice!");

        return "redirect:";
    }

}
