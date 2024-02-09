package com.example.springRestApi.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * Classe de entidade que representa um arquivo.
 */
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Builder
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * O nome do arquivo.
     */
    @Column
    private String nome;
    /**
     * A data de upload do arquivo.
     */
    @Column
    @CreatedDate
    private LocalDateTime uploadDate;
    /**
     * A URL do arquivo.
     */
    @Column
    private String url;
    /**
     * O conteúdo do arquivo. Este é armazenado como um array de bytes.
     */
    @Column
    @Lob
    private byte[] file;
    /**
     * As tags associadas ao arquivo.
     */
    @OneToOne(orphanRemoval = true)
    private Tags tags;
}
