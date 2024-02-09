package com.example.springRestApi.controller;

import com.example.springRestApi.model.File;
import com.example.springRestApi.model.dto.FileDto;
import com.example.springRestApi.model.dto.TagsDto;
import com.example.springRestApi.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
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

    /**
     * Endpoint para upload de vários arquivos.
     *
     * @param files A lista de arquivos a serem carregados.
     * @return Uma ResponseEntity contendo a URI do primeiro arquivo carregado.
     */
    @PostMapping("/lote")
    public ResponseEntity uploadLote(@RequestParam(value = "files") List<MultipartFile> files) {
        List<Map<String, Object>> fileStatuses = new ArrayList<>();

        for (MultipartFile file : files) {
            Map<String, Object> fileStatus = new HashMap<>();
            fileStatus.put("fileName", file.getOriginalFilename());

            try {
                File fileSaved = service.save(file, service.extractTags(file));
                URI fileUri = buildFileURL(fileSaved);
                fileStatus.put("status", "Salvo com sucesso");
                fileStatus.put("uri", fileUri);
            } catch (ResponseStatusException e) {
                fileStatus.put("status", "Erro ao salvar");
                fileStatus.put("error", e.getReason());
            }

            fileStatuses.add(fileStatus);
        }
        return ResponseEntity.ok(fileStatuses);
    }

    /**
     * Endpoint para recuperar um arquivo pelo seu ID.
     *
     * @param id O ID do arquivo a ser recuperado.
     * @return Uma ResponseEntity contendo os dados do arquivo.
     */
    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        var file = service.getById(id);
        return ResponseEntity.ok().header("Content-Type", "application/xml").body(file.getFile());
    }

    @GetMapping
    public ResponseEntity<List<FileDto>> getAllFiles() {
        return ResponseEntity.ok(service.getAllFiles().stream().map(file -> {
            var url = buildFileURL(file);
            return FileDto.builder()
                    .id(file.getId())
                    .nome(file.getNome())
                    .uploadDate(file.getUploadDate())
                    .url(url.toString())
                    .tags(new TagsDto(file.getTags()))
                    .build();
        }).collect(Collectors.toList()));
    }

    /**
     * Endpoint para excluir um arquivo pelo seu ID.
     *
     * @param id O ID do arquivo a ser excluído.
     * @return Uma ResponseEntity indicando que a operação foi bem-sucedida.
     */
    @DeleteMapping("{id}")
    public ResponseEntity deleteFile(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Método auxiliar para construir uma URL de arquivo.
     *
     * @param file O arquivo para o qual construir a URL.
     * @return A URI construída.
     */
    private URI buildFileURL(File file) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + file.getId()).build().toUri();
    }
}
