package com.example.userservice.repository;

import com.example.userservice.entity.Temp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempRepository extends JpaRepository<Temp, Long> {
}
