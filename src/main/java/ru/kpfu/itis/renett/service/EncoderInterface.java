package ru.kpfu.itis.renett.service;

public interface EncoderInterface {
    String encodeString(String str);
    boolean validate(String value1, String value2);
}
