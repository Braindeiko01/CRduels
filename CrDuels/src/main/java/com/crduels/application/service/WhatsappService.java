package com.crduels.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Servicio para enviar mensajes a través de la API de WhatsApp Business.
 */
@Service
public class WhatsappService {

    /** WhatsApp API token. Uses an empty string if not configured to prevent
     *  startup failures. */
    @Value("${whatsapp.api.token:}")
    private String apiToken;

    /** Identifier of the WhatsApp Business phone number. */
    @Value("${whatsapp.phone-number-id:}")

    private String phoneNumberId;

    private final RestTemplate restTemplate = new RestTemplate();

    public void enviarMensajeTexto(String waId, String mensaje) {
        if (apiToken.isBlank() || phoneNumberId.isBlank()) {
            // Avoid failing at runtime if credentials are missing
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = Map.of(
                "messaging_product", "whatsapp",
                "to", waId,
                "type", "text",
                "text", Map.of("body", mensaje)
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
        String url = "https://graph.facebook.com/v19.0/" + phoneNumberId + "/messages";
        restTemplate.postForEntity(url, entity, String.class);
    }

    public String generarLinkClash(String tag) {
        if (tag == null) {
            return "";
        }
        String clean = tag.replace("#", "");
        return "https://link.clashroyale.com/invite/friend?tag=" + clean;
    }
}
