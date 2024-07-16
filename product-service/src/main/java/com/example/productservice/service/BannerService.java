package com.example.productservice.service;

import com.example.productservice.Entity.Banner;
import com.example.productservice.Entity.BannerImage;
import com.example.productservice.dto.banner.BannerResponseDto;
import com.example.productservice.dto.file.FileSaveResultDto;
import com.example.productservice.error.exception.FileException;
import com.example.productservice.repository.BannerRepository;
import com.example.productservice.repository.FileStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BannerService {
    private final FileStorage fileStorage;
    private final BannerRepository bannerRepository;

    /**
     * 배너 생성
     * @param multipartFile 배너 이미지
     * @param url 배너 이동 url
     * @return 생성된 배너 식별자
     */
    public Long createBanner(MultipartFile multipartFile, String url) {
        FileSaveResultDto fileSaveResultDto = fileStorage.save(getBytes(multipartFile), multipartFile.getOriginalFilename());

        BannerImage bannerImage = BannerImage.builder()
                .originalName(multipartFile.getOriginalFilename())
                .physicalName(fileSaveResultDto.getPhysicalName())
                .path(fileSaveResultDto.getPath())
                .build();
        Banner banner = Banner.builder()
                .bannerImage(bannerImage)
                .url(url)
                .build();

        bannerRepository.save(banner);

        return banner.getBannerId();
    }

    /**
     * 배너 전체 조회
     *
     * @return 배너 리스트
     */
    public List<BannerResponseDto> getAllBanners() {
        return bannerRepository.findAll()
                .stream()
                .map(banner -> new BannerResponseDto(banner.getBannerId(), banner.getUrl(), banner.getBannerImage().getBannerImageId()))
                .collect(Collectors.toList());
    }

    /**
     * 배너 삭제
     * @param bannerId 배너 식별자
     */
    public void deleteBanner(Long bannerId) {
        bannerRepository.deleteById(bannerId);
    }

    private static byte[] getBytes(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            log.error("{}", e);
            throw new FileException("파일 I/O - READ 에러");
        }
    }
}
