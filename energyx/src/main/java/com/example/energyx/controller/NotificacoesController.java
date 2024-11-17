package com.example.energyx.controller;

import com.example.energyx.dto.NotificacoesDTO;
import com.example.energyx.service.interfaces.NotificacoesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacoesController {

    @Autowired
    private NotificacoesService notificacoesService;

    // Endpoint para criar uma nova notificação
    @PostMapping
    public ResponseEntity<NotificacoesDTO> criarNotificacao(@Valid @RequestBody NotificacoesDTO notificacoesDTO) {
        NotificacoesDTO novaNotificacao = notificacoesService.criarNotificacao(notificacoesDTO);
        adicionarLinks(novaNotificacao);
        return new ResponseEntity<>(novaNotificacao, HttpStatus.CREATED);
    }

    // Endpoint para obter uma notificação por ID
    @GetMapping("/{id}")
    public ResponseEntity<NotificacoesDTO> obterNotificacaoPorId(@PathVariable Long id) {
        NotificacoesDTO notificacao = notificacoesService.obterNotificacaoPorId(id);
        if (notificacao == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(notificacao);
        return ResponseEntity.ok(notificacao);
    }

    // Endpoint para listar todas as notificações
    @GetMapping
    public ResponseEntity<List<NotificacoesDTO>> listarNotificacoes() {
        List<NotificacoesDTO> notificacoes = notificacoesService.listarNotificacoes(null);
        List<NotificacoesDTO> notificacoesComLinks = notificacoes.stream()
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificacoesComLinks);
    }

    // Endpoint para atualizar uma notificação existente
    @PutMapping("/{id}")
    public ResponseEntity<NotificacoesDTO> atualizarNotificacao(@PathVariable Long id, @Valid @RequestBody NotificacoesDTO notificacoesDTO) {
        NotificacoesDTO notificacaoAtualizada = notificacoesService.atualizarNotificacao(id, notificacoesDTO);
        if (notificacaoAtualizada == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(notificacaoAtualizada);
        return ResponseEntity.ok(notificacaoAtualizada);
    }

    // Endpoint para excluir uma notificação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirNotificacao(@PathVariable Long id) {
        boolean isDeleted = notificacoesService.excluirNotificacao(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(NotificacoesDTO notificacoesDTO) {
        notificacoesDTO.add(linkTo(methodOn(NotificacoesController.class).obterNotificacaoPorId(notificacoesDTO.getNotificacaoId())).withSelfRel());
        notificacoesDTO.add(linkTo(methodOn(NotificacoesController.class).listarNotificacoes()).withRel("notificacoes"));
        notificacoesDTO.add(linkTo(methodOn(NotificacoesController.class).atualizarNotificacao(notificacoesDTO.getNotificacaoId(), notificacoesDTO)).withRel("atualizar"));
        notificacoesDTO.add(linkTo(methodOn(NotificacoesController.class).excluirNotificacao(notificacoesDTO.getNotificacaoId())).withRel("excluir"));
    }
}
