package com.example.springRestApi.repository;

import com.example.springRestApi.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {

}
