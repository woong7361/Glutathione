package com.example.orderservice.repository;


import com.example.orderservice.dto.file.FileSaveResultDto;

import java.io.File;

/**
 * 물리적 파일 저장소
 */
public interface FileStorage {
    /**
     * 물리적 파일 저장
     * @param bytes 파일 byte[]
     * @param originalFileName 기존 파일 이름
     * @return 저장된 경로, 저장된 이름
     */
    FileSaveResultDto save(byte[] bytes, String originalFileName);

    /**
     * 물리적 파일 가져오기
     * @param path 저장 경로(이름 포함)
     * @return 파일 객체
     */
    File getFile(String path);
}
