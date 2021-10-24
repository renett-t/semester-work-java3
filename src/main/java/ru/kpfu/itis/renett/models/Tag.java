package ru.kpfu.itis.renett.models;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class Tag {
    private Integer id;
    private String title;
}
