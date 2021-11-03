package ru.kpfu.itis.renett.models;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthModel {
    private String login;
    private UUID uuid;
    private Date createdAt;
}
