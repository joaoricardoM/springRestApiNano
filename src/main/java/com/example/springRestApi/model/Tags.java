package com.example.springRestApi.model;

import lombok.*;

import javax.persistence.*;
/**
 * Classe de entidade que representa as tags de um arquivo.
 */
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Tags {
    /**
     * O ID das tags. Este é o identificador único gerado automaticamente pelo JPA.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * O ID da NFe associado às tags.
     */
    @Column(unique = true)
    private String NFeId;
    /**
     * A data de emissão associada às tags.
     */
    @Column
    private String dhEmi;
    /**
     * O número da nota fiscal associado às tags.
     */
    @Column
    private String nNF;
    /**
     * O código do estado associado às tags.
     */
    @Column
    private String cUF;
    /**
     * O CNPJ do emissor associado às tags.
     */
    @Column
    private String emitCNPJ;
    /**
     * O nome fantasia do emissor associado às tags.
     */
    @Column
    private String xFant;
    /**
     * O CNPJ do destinatário associado às tags.
     */
    @Column
    private String destCNPJ;
    /**
     * O nome do destinatário associado às tags.
     */
    @Column
    private String xNome;
    /**
     * O valor total dos tributos associado às tags.
     */
    @Column
    private String vTotTrib;
    /**
     * O valor da nota fiscal associado às tags.
     */
    @Column
    private String vNF;
}


