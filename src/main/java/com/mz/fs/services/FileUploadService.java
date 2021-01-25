package com.mz.fs.services;

import com.mz.fs.dto.DeleteFileResponse;
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
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadService {

    private final Path fileStorageLocation = Paths.get("D:\\Files\\").toAbsolutePath().normalize();

    public FileUploadResponse uploadFile(MultipartFile file) {
        String fileId = UUID.randomUUID().toString();
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        try {
            Files.createDirectories(Paths.get(fileStorageLocation + File.separator + fileId));
            Files.copy(
                    file.getInputStream(),
                    Paths.get(fileStorageLocation + File.separator + fileId + File.separator + file.getOriginalFilename()),
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
            UrlResource urlResource = new UrlResource(Paths.get(fileStorageLocation + File.separator + fileId).toUri());
            if (urlResource.exists()) {
                File fileLocation = new File(fileStorageLocation + File.separator + fileId);
                File[] fileList = fileLocation.listFiles();
                if (null != fileList) {
                    File file = Arrays.stream(fileList).findFirst().orElseThrow(() -> new RuntimeException("File not found"));
                    return new UrlResource(Paths.get(fileStorageLocation + File.separator + fileId + File.separator + file.getName()).toUri());
                } else {
                    throw new RuntimeException("File not found.");
                }
            } else {
                throw new RuntimeException("File not found.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found.");
        }

    }

    public DeleteFileResponse deleteFile(String fileId) {
        try {
            Files.walk(Paths.get(fileStorageLocation + File.separator + fileId))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            return new DeleteFileResponse("File deleted successfully", new Date().toString());
        } catch (IOException e) {
            throw new RuntimeException("File not found.");
        }


    }
}
