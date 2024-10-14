package fr.pilato.elasticsearch.crawler.fs;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class CustomMetadataExtractor {

    public Metadata extractCustomMetadata(InputStream inputStream) throws IOException, TikaException {
        Tika tika = new Tika();
        Metadata metadata = new Metadata();

        // Extract content from the document
        String content = tika.parseToString(inputStream, metadata);

        // Custom logic to extract keywords or classify document types
        List<String> keywords = extractKeywords(content);
        String documentType = classifyDocumentType(content);

        metadata.add("custom:keywords", String.join(",", keywords));
        metadata.add("custom:documentType", documentType);

        return metadata;
    }

    private List<String> extractKeywords(String content) {
        // Logic to extract custom keywords (e.g., NLP or regex)
        return Arrays.asList("Officer/Escrow Officer", "invoice", "payment", "File No", "Seller");
    }

    private String classifyDocumentType(String content) {
        // Logic to classify document types (e.g., Invoice, Contract)
        if (content.contains("ALTA Settlement Statement")) {
            return "ALTA Settlement";
        } else if (content.contains("Contract")) {
            return "Contract";
        }
        return "Unknown";
    }
}
