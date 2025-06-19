package com.crduels.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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

    /**
     * Envía un menú de registro con un solo botón para iniciar el proceso.
     */
    public void enviarMenuRegistro(String waId) {
        if (apiToken.isBlank() || phoneNumberId.isBlank()) {
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = Map.of(
                "messaging_product", "whatsapp",
                "to", waId,
                "type", "interactive",
                "interactive", Map.of(
                        "type", "button",
                        "body", Map.of("text", "¡Hola! Para comenzar a usar el sistema, por favor regístrate."),
                        "action", Map.of("buttons", List.of(
                                Map.of(
                                        "type", "reply",
                                        "reply", Map.of(
                                                "id", "REGISTRARSE",
                                                "title", "📝 Registrarse"
                                        )
                                )
                        ))
                )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
        String url = "https://graph.facebook.com/v19.0/" + phoneNumberId + "/messages";
        restTemplate.postForEntity(url, entity, String.class);
    }

    /**
     * Envía opciones de recarga de saldo en un menú interactivo.
     */
    public void enviarMenuRecarga(String waId) {
        if (apiToken.isBlank() || phoneNumberId.isBlank()) {
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = Map.of(
                "messaging_product", "whatsapp",
                "to", waId,
                "type", "interactive",
                "interactive", Map.of(
                        "type", "button",
                        "body", Map.of("text", "Necesitas recargar saldo para poder jugar. Elige un monto:"),
                        "action", Map.of("buttons", List.of(
                                Map.of(
                                        "type", "reply",
                                        "reply", Map.of("id", "RECARGA_6000", "title", "💰 Recargar 6000")
                                ),
                                Map.of(
                                        "type", "reply",
                                        "reply", Map.of("id", "RECARGA_12000", "title", "💰 Recargar 12000")
                                ),
                                Map.of(
                                        "type", "reply",
                                        "reply", Map.of("id", "RECARGA_18000", "title", "💰 Recargar 18000")
                                )
                        ))
                )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
        String url = "https://graph.facebook.com/v19.0/" + phoneNumberId + "/messages";
        restTemplate.postForEntity(url, entity, String.class);
    }

    /**
     * Envía el menú principal con las acciones disponibles para el usuario.
     */
    public void enviarMenuPrincipal(String waId) {
        if (apiToken.isBlank() || phoneNumberId.isBlank()) {
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = Map.of(
                "messaging_product", "whatsapp",
                "to", waId,
                "type", "interactive",
                "interactive", Map.of(
                        "type", "button",
                        "body", Map.of("text", "¿Qué deseas hacer?"),
                        "action", Map.of("buttons", List.of(
                                Map.of(
                                        "type", "reply",
                                        "reply", Map.of("id", "BUSCAR_PARTIDA", "title", "⚔️ Buscar partida")
                                ),
                                Map.of(
                                        "type", "reply",
                                        "reply", Map.of("id", "RETIRAR_SALDO", "title", "Retirar saldo")
                                ),
                                Map.of(
                                        "type", "reply",
                                        "reply", Map.of("id", "MI_SALDO", "title", "Mi saldo")
                                ),
                                Map.of(
                                        "type", "reply",
                                        "reply", Map.of("id", "HISTORIAL", "title", "Historial de partidas")
                                )
                        ))
                )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
        String url = "https://graph.facebook.com/v19.0/" + phoneNumberId + "/messages";
        restTemplate.postForEntity(url, entity, String.class);
    }
}
