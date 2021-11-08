package ru.kpfu.itis.renett.service.fileService;

import ru.kpfu.itis.renett.exceptions.FileUploadException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileManagerImpl implements FileManager {
    private String storageUrl;

    public FileManagerImpl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    @Override
    public String saveFile(String fileName, InputStream fileInputStream) {
        String randomizedFileName = getNameForFile(fileName);
        upload(randomizedFileName, fileInputStream);

        return randomizedFileName;
    }

    private String getNameForFile(String fileName) {
        return (int) (Math.random() * (10000 - 100) + 100) + fileName;
    }

    private void upload(String filename, InputStream fileInputStream) {
        try {
            String uploadPath = "/home/renett/apache-tomcat-9.0.54/webapps/pima" + File.separator + storageUrl;
            File fileToSave = new File(uploadPath + File.separator + filename);
            if (!fileToSave.getParentFile().exists()) {
                fileToSave.getParentFile().mkdirs();
            }
            Files.copy(fileInputStream, fileToSave.toPath());
        } catch (IOException e) {
            throw new FileUploadException("Проблема при отправке изображения, попробуйте ещё разочек", e);
        }
    }
}
