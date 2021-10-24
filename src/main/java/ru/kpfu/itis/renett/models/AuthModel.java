package ru.kpfu.itis.renett.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class AuthModel {
    private Integer id;
    private String login;
    private UUID uuid;
}
