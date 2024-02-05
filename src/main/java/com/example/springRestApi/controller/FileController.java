package com.example.springRestApi.controller;

import com.example.springRestApi.services.FileService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService service;

    /**
     * Criação dos endpoints
     */
}
