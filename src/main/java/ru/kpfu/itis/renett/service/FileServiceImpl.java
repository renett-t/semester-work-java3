package ru.kpfu.itis.renett.service;

import ru.kpfu.itis.renett.exceptions.FileUploadException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class FileServiceImpl implements FileService {
    private String storageUrl;

    public FileServiceImpl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    @Override
    public String saveFile(String fileName, InputStream fileInputStream) {
        String randomFileName = getNameForFile(fileName);
        upload(storageUrl + randomFileName, fileInputStream);

        return randomFileName;
    }

    private String getNameForFile(String fileName) {
        return (new Random().nextLong()) + fileName;
    }

// https://www.baeldung.com/upload-file-servlet
    private void upload(String filename, InputStream fileInputStream) {
        try {
            File fileToSave = new File(filename);
            System.out.println("TRYING TO SAVE");
            System.out.println(fileToSave.exists());
            fileToSave.getParentFile().mkdirs();
            System.out.println("HERE: " + fileToSave);

            Files.copy(fileInputStream, fileToSave.toPath());
        } catch (IOException e) {
            throw new FileUploadException("Проблема при отправке изображения, попробуйте ещё разочек", e);
        }
    }
}
