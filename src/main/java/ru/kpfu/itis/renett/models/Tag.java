package ru.kpfu.itis.renett.models;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Tag {
    private Integer id;
    private String title;

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
