package ru.kpfu.itis.renett.models;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class Comment {
    private Integer id;
    private String body;
    private Article article;
    private User author;
    private Date publishedAt;

    private Comment parentComment;
    private List<Comment> nestedComments;
}
