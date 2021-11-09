package ru.kpfu.itis.renett.models;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthModel {
    private String login;
    private UUID uuid;
    private Date createdAt;
}
