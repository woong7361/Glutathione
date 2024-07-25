package com.example.orderservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CouponImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponImageId;

    private String physicalName;
    private String originalName;
    private String path;
}
