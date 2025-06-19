package com.crduels.infrastructure.controller;

import com.crduels.application.dto.RegistroWhatsAppDto;
import com.crduels.application.dto.WhatsappMessagesValueDto;
import com.crduels.application.dto.WhatsappWebhookDto;
import com.crduels.application.service.UsuarioService;
import com.crduels.application.service.WhatsappService;
import com.crduels.infrastructure.repository.PartidaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Webhook público para recibir mensajes de WhatsApp.
 */
@RestController
@RequestMapping("/api/webhook")
public class WhatsappWebhookController {

    private final UsuarioService usuarioService;
    private final WhatsappService whatsappService;
    private final PartidaRepository partidaRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WhatsappWebhookController(UsuarioService usuarioService,
                                     WhatsappService whatsappService,
                                     PartidaRepository partidaRepository) {
        this.usuarioService = usuarioService;
        this.whatsappService = whatsappService;
        this.partidaRepository = partidaRepository;
    }

    @GetMapping("/whatsapp")
    public ResponseEntity<String> verificarWebhook(@RequestParam("hub.mode") String mode,
                                                   @RequestParam("hub.verify_token") String token,
                                                   @RequestParam("hub.challenge") String challenge) {
        if ("Dj191919CrDuels".equals(token)) {
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token inválido");
    }

    @PostMapping("/whatsapp")
    public ResponseEntity<Void> recibirMensaje(@RequestBody WhatsappWebhookDto body) {
        if (body.getEntry() == null || body.getEntry().isEmpty()) {
            return ResponseEntity.ok().build();
        }

        WhatsappWebhookDto.EntryDto entry = body.getEntry().get(0);
        if (entry.getChanges() == null || entry.getChanges().isEmpty()) {
            return ResponseEntity.ok().build();
        }

        WhatsappWebhookDto.ChangeDto change = entry.getChanges().get(0);
        if ("messages".equals(change.getField())) {
            WhatsappMessagesValueDto value = objectMapper.convertValue(change.getValue(), WhatsappMessagesValueDto.class);

            WhatsappMessagesValueDto.Contact contact = value.getContacts().get(0);
            String waId = contact.getWaId();
            String nombre = contact.getProfile().getName();
            WhatsappMessagesValueDto.Message message = value.getMessages().get(0);
            String text = message.getText() != null ? message.getText().getBody() : "";

            RegistroWhatsAppDto dto = new RegistroWhatsAppDto(nombre, text);
            var usuario = usuarioService.registrarDesdeWhatsapp(waId, dto);

            partidaRepository.findActivaPorTelefono(waId).ifPresentOrElse(partida -> {
                String destino;
                if (waId.equals(partida.getApuesta().getJugador1().getTelefono())) {
                    destino = partida.getApuesta().getJugador2().getTelefono();
                } else {
                    destino = partida.getApuesta().getJugador1().getTelefono();
                }
                whatsappService.enviarMensajeTexto(destino, text);
            }, () -> {
                if (usuario.getTagClash() == null) {
                    whatsappService.enviarMenuRegistro(waId);
                } else if (usuario.getSaldo().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                    whatsappService.enviarMenuRecarga(waId);
                } else {
                    whatsappService.enviarMenuPrincipal(waId);
                }
            });
        }

        return ResponseEntity.ok().build();
    }
}
