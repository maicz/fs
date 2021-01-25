package com.mz.fs.controllers;

import com.mz.fs.dto.DeleteFileResponse;
import com.mz.fs.dto.FileUploadResponse;
import com.mz.fs.services.FileUploadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("files")
@AllArgsConstructor
public class FileController {

    private final FileUploadService fis;

    @PostMapping
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(fis.uploadFile(file));
    }

    @GetMapping("/{fileId}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        Resource file = fis.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<DeleteFileResponse> deleteFile(@PathVariable String fileId) {
        return ResponseEntity.ok(fis.deleteFile(fileId));
    }
}
