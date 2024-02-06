package com.example.springRestApi.controller;

import com.example.springRestApi.model.File;
import com.example.springRestApi.services.FileService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService service;

    /**
     * Endpoint para upload de um único arquivo.
     *
     * @param file O arquivo a ser carregado.
     * @return Uma ResponseEntity contendo a URI do arquivo carregado.
     */

    @PostMapping
    public ResponseEntity upload(@RequestParam(value = "file") MultipartFile file) {
        File fileSaved = service.save(file, service.extractTags(file));
        return ResponseEntity.created(buildFileURL(fileSaved)).build();
    }

    private URI buildFileURL(File file) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + file.getId()).build().toUri();
    }
}
