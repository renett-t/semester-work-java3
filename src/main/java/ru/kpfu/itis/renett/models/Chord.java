package ru.kpfu.itis.renett.models;

import lombok.*;

import javax.json.Json;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class Chord {
    private Integer id;
    private String title;
    private String type;
    private Json position;
    private String audioFilePath;
    private String pictureFilePath;
}
