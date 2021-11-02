package ru.kpfu.itis.renett.service;

import java.io.InputStream;

public interface FileService {
    void upload(String filename, InputStream fileInputStream);
}
