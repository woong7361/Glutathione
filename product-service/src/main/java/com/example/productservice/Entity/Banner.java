package com.example.productservice.Entity;

import com.example.productservice.dto.file.FileSaveResultDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerId;

    private String url;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "banner_image_id", nullable = false)
    private BannerImage bannerImage;

    public void update(FileSaveResultDto fileSaveResultDto, String url) {
        this.url = url;
        bannerImage.update(fileSaveResultDto);
    }
}
