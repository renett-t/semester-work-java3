package ru.kpfu.itis.renett.service.security;

public interface Parser {
    String getTokenFromServerResponse(String tokenidemail);
    String getIdFromServerResponse(String tokenidemail);
    String getEmailFromServerResponse(String tokenidemail);
    String getFirstNameFromResponse(String userData);
    String getSecondNameFromResponse(String userData);
}
