package com.example.springRestApi.model.dto;

import com.example.springRestApi.model.Tags;
import lombok.Builder;
import lombok.Data;

@Data
public class TagsDto {
    private String NFeId;
    private String dhEmi;
    private String nNF;
    private String cUF;
    private String emitCNPJ;
    private String xFant;
    private String destCNPJ;
    private String xNome;
    private String vTotTrib;
    private String vNF;

    public TagsDto(Tags tags) {
        this.NFeId = tags.getNFeId();
        this.dhEmi = tags.getDhEmi();
        this.nNF = tags.getNNF();
        this.cUF = tags.getCUF();
        this.emitCNPJ = tags.getEmitCNPJ();
        this.xFant = tags.getXFant();
        this.destCNPJ = tags.getDestCNPJ();
        this.xNome = tags.getXNome();
        this.vTotTrib = tags.getVTotTrib();
        this.vNF = tags.getVNF();
    }
}
