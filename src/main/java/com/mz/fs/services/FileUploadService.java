package com.mz.fs.services;

import com.mz.fs.dto.FileUploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadService {

    private final Path fileStorageLocationl = Paths.get("D:\\Workspace\\temp\\").toAbsolutePath().normalize();

    public FileUploadResponse uploadFile(MultipartFile file) {
        String fileId = UUID.randomUUID().toString();
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        try {
            Files.createDirectories(Paths.get(fileStorageLocationl + fileId));
            Files.copy(
                    file.getInputStream(),
                    Paths.get(fileStorageLocationl + fileId + "\\" + file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file");
        }
        return FileUploadResponse.builder()
                .fileId(fileId)
                .fileName(file.getOriginalFilename())
                .fileSize(String.valueOf(file.getSize()))
                .uploadDate(new Date().toString())
                .build();
    }

    public Resource getFile(String fileId) {
        try {
            UrlResource urlResource = new UrlResource(Paths.get(fileStorageLocationl + File.separator + fileId).toUri());
            if (urlResource.exists()) {
                String name = Arrays.stream(new File(fileStorageLocationl + File.separator + fileId).listFiles()).findFirst().get().getName();
                return new UrlResource(Paths.get(fileStorageLocationl + File.separator + fileId + File.separator + name).toUri());
            } else {
                throw new RuntimeException("File not found.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found.");
        }

    }
}
