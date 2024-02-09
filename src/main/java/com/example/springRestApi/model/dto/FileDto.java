package com.example.springRestApi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FileDto {
    private Integer id;
    private String url;
    private String nome;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime uploadDate;
    private TagsDto tags;
}

