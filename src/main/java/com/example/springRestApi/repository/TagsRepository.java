package com.example.springRestApi.repository;

import com.example.springRestApi.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags, String> {
    boolean existsByNFeId(String infNFeId);
}

