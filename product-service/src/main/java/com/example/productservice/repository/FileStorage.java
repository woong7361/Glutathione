package com.example.productservice.repository;

import com.example.productservice.dto.file.FileSaveResultDto;

import java.io.File;

public interface FileStorage {
    FileSaveResultDto save(byte[] bytes, String originalFileName);

    void delete(Long fileId);

    File getFile(String path);
}
