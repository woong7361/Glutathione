package com.example.orderservice.repository;

import com.example.orderservice.config.FileConfigurationService;
import com.example.orderservice.dto.file.FileSaveResultDto;
import com.example.orderservice.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocalFileStorage implements FileStorage{
    private final FileConfigurationService fileConfigurationService;
    @Override
    public FileSaveResultDto save(byte[] bytes, String originalFileName) {
        String newFilename = getNewFilename(originalFileName);

        try (OutputStream outputStream = new FileOutputStream(fileConfigurationService.getPath() + newFilename))
        {
            outputStream.write(bytes);
        } catch (IOException e) {
            log.error("file I/O exception  fileName: {}, message: {}", originalFileName, e.getMessage());
            //TODO custom exception 필요
            throw new IllegalArgumentException("파일 저장 에러", e);
        }

        log.info("file: {} local에 파일저장 완료!", originalFileName);

        return FileSaveResultDto.builder()
                .path(fileConfigurationService.getPath())
                .physicalName(newFilename)
                .build();
    }

    @Override
    public File getFile(String path) {
        File file = new File(path);
        if (isFileNotExist(file)) {
            //TODO custom exception 필요
            throw new NotFoundException("존재하지 않는 파일입니다.", 0L);
        }

        return file;
    }

    private boolean isFileNotExist(File file) {
        return !file.exists();
    }

    private String getNewFilename(String fileName) {
        String uuid = UUID.randomUUID().toString();

        return System.nanoTime() + uuid + "." + getExtension(fileName);
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

}
