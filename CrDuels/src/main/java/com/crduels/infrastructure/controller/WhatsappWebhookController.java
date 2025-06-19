package com.crduels.infrastructure.controller;

import com.crduels.application.dto.RegistroWhatsAppDto;
import com.crduels.application.service.UsuarioService;
import com.crduels.application.service.WhatsappService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Webhook público para recibir mensajes de WhatsApp.
 */
@RestController
@RequestMapping("/api/webhook")
public class WhatsappWebhookController {

    private final UsuarioService usuarioService;
    private final WhatsappService whatsappService;

    public WhatsappWebhookController(UsuarioService usuarioService, WhatsappService whatsappService) {
        this.usuarioService = usuarioService;
        this.whatsappService = whatsappService;
    }

    @PostMapping("/whatsapp")
    public ResponseEntity<Void> recibirMensaje(@RequestBody JsonNode body) {
        JsonNode entry = body.path("entry").get(0);
        JsonNode change = entry.path("changes").get(0);
        JsonNode value = change.path("value");
        JsonNode contact = value.path("contacts").get(0);
        String waId = contact.path("wa_id").asText();
        String nombre = contact.path("profile").path("name").asText();
        JsonNode message = value.path("messages").get(0);
        String text = message.path("text").path("body").asText();

        RegistroWhatsAppDto dto = new RegistroWhatsAppDto(nombre, text);
        var usuario = usuarioService.registrarDesdeWhatsapp(waId, dto);

        String saludo = "¡Hola " + usuario.getNombre() + "! Para comenzar, necesitas hacer un depósito.";
        whatsappService.enviarMensajeTexto(waId, saludo);
        return ResponseEntity.ok().build();
    }
}
