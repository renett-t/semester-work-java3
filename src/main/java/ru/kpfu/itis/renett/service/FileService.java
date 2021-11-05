package ru.kpfu.itis.renett.service;

import java.io.InputStream;

public interface FileService {
    String saveFile(String imageFileName, InputStream inputStream);
}
