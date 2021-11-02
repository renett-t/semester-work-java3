package ru.kpfu.itis.renett.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileServiceImpl implements FileService {
    private String storageUrl;

    public FileServiceImpl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    @Override
    public void upload(String filename, InputStream fileInputStream) {
        try {
            Files.copy(fileInputStream, Paths.get(storageUrl + filename));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
