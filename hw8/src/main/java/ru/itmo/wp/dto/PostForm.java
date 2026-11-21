package ru.itmo.wp.dto;

import javax.validation.constraints.*;


public class PostForm {
    @NotEmpty
    @Size(min = 1, max = 60)
    private String title;

    @NotEmpty
    @Size(min = 1, max = 65000)
    private String text;

    private String tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
