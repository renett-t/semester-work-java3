package ru.kpfu.itis.renett.models;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class Article {
    private Integer id;
    private String title;
    private String body;
    private User author;
    private Date publishedAt;
    private List<Comment> commentList;
    private List<Tag> tagList;
    private int view_count;
    private int commentAmount;
}
