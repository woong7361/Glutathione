package com.example.productservice.Entity;

import com.example.productservice.dto.file.FileSaveResultDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BannerImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerImageId;

    private String physicalName;
    private String originalName;
    private String path;

    public void update(FileSaveResultDto fileSaveResultDto) {
        this.physicalName = fileSaveResultDto.getPhysicalName();
        this.path = fileSaveResultDto.getPath();
    }
}
