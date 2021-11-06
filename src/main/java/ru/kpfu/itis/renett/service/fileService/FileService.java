package ru.kpfu.itis.renett.service.fileService;

import java.io.InputStream;

public interface FileService {
    String saveFile(String imageFileName, InputStream inputStream);
}
