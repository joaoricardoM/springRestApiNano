package com.example.springRestApi.services;

import com.example.springRestApi.model.File;
import com.example.springRestApi.model.Tags;
import com.example.springRestApi.repository.FileRepository;
import com.example.springRestApi.repository.TagsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.List;

/**
 * Classe de serviço para manipulação de operações de arquivo.
 */
@Service
@AllArgsConstructor
@Slf4j
public class FileService {

    private final FileRepository repository;
    private final TagsRepository tagsRepository;

    /**
     * Salva um arquivo no repositório.
     *
     * @param file O arquivo a ser salvo.
     * @param tags As tags associadas ao arquivo.
     * @return O arquivo salvo.
     * @throws ResponseStatusException Se ocorrer um erro durante a gravação do arquivo.
     */
    public File save(MultipartFile file, Tags tags) {
        try {
            File fileToSave = File.builder()
                    .nome(file.getOriginalFilename())
                    .file(file.getBytes())
                    .tags(tags)
                    .build();
            return repository.save(fileToSave);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Recupera um arquivo pelo seu ID.
     *
     * @param id O ID do arquivo a ser recuperado.
     * @return O arquivo recuperado.
     * @throws ResponseStatusException Se o arquivo não for encontrado.
     */
    public File getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Arquivo não encontrado"));
    }

    /**
     * Extrai tags de um arquivo.
     *
     * @param file O arquivo do qual extrair tags.
     * @return As tags extraídas.
     * @throws ResponseStatusException Se o arquivo não for do tipo XML ou se um arquivo com o mesmo ID já existir.
     */
    public Tags extractTags(MultipartFile file) {
        if (!file.getContentType().contains("xml")) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "O arquivo deve ser " +
                    "do tipo XML");
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file.getInputStream());

            doc.getDocumentElement().normalize();

            String infNFeId = getAttributeValue(doc, "infNFe", "Id");
            log.info("id: {}", infNFeId);
            if (tagsRepository.existsByNFeId(infNFeId)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um arquivo com o id " + infNFeId);
            };
            String dhEmi = getTagValue(doc, "dhEmi");
            log.info("dhEmi: {}", dhEmi);
            String nNF = getTagValue(doc, "nNF");
            log.info("nNF: {}", nNF);
            String cUF = getTagValue(doc, "cUF");
            log.info("cUF: {}", cUF);
            String cnpjEmitente = getTagValue(doc, "CNPJ");
            log.info("cnpjEmitente: {}", cnpjEmitente);
            String xFantEmitente = getTagValue(doc, "xFant");
            log.info("xFantEmitente: {}", xFantEmitente);
            String cnpjDestinatario = getTagValue(doc, "CNPJ");
            log.info("cnpjDestinatario: {}", cnpjDestinatario);
            String xNomeDestinatario = getTagValue(doc, "xNome");
            log.info("xNomeDestinatario: {}", xNomeDestinatario);
            String vTotTrib = getTagValue(doc, "vTotTrib");
            log.info("vTotTrib: {}", vTotTrib);
            String vNF = getTagValue(doc, "vNF");
            log.info("vNF: {}", vNF);

            return tagsRepository.save(Tags.builder()
                    .NFeId(infNFeId)
                    .dhEmi(dhEmi)
                    .nNF(nNF)
                    .cUF(cUF)
                    .emitCNPJ(cnpjEmitente)
                    .xFant(xFantEmitente)
                    .destCNPJ(cnpjDestinatario)
                    .xNome(xNomeDestinatario)
                    .vTotTrib(vTotTrib)
                    .vNF(vNF)
                    .build());
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), e.getReason());
        } catch (Exception e) {
            log.error("An error occurred while extracting the tags", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Exclui um arquivo pelo seu ID.
     *
     * @param id O ID do arquivo a ser excluído.
     * @throws ResponseStatusException Se o arquivo não for encontrado.
     */
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Arquivo não encontrado");
        }
        repository.deleteById(id);
    }

    private static String getAttributeValue(Document doc, String tagName, String attrName) {
        NodeList nodeList = doc.getElementsByTagName(tagName);

        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null) {
                Node attrNode = node.getAttributes().getNamedItem(attrName);
                if (attrNode != null) {
                    return attrNode.getNodeValue();
                }
            }
        }
        return null;
    }

    private static String getTagValue(Document doc, String tagName) {
        NodeList nodeList = doc.getElementsByTagName(tagName);

        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null) {
                return node.getTextContent();
            }
        }
        return null;
    }

    public List<File> getAllFiles() {
        return repository.findAll();
    }
}

