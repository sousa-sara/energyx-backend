package com.example.energyx.controller;

import com.example.energyx.dto.StatusNotificacaoDTO;
import com.example.energyx.service.interfaces.StatusNotificacaoService;
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
@RequestMapping("/api/status-notificacoes")
public class StatusNotificacaoController {

    @Autowired
    private StatusNotificacaoService statusNotificacaoService;

    // Endpoint para criar um novo status de notificação
    @PostMapping
    public ResponseEntity<StatusNotificacaoDTO> criarStatusNotificacao(@Valid @RequestBody StatusNotificacaoDTO statusNotificacaoDTO) {
        StatusNotificacaoDTO createdStatus = statusNotificacaoService.criarStatusNotificacao(statusNotificacaoDTO);
        adicionarLinks(createdStatus);
        return new ResponseEntity<>(createdStatus, HttpStatus.CREATED);
    }

    // Endpoint para obter um status de notificação por ID
    @GetMapping("/{id}")
    public ResponseEntity<StatusNotificacaoDTO> obterStatusNotificacaoPorId(@PathVariable Long id) {
        StatusNotificacaoDTO statusNotificacaoDTO = statusNotificacaoService.obterStatusNotificacaoPorId(id);
        if (statusNotificacaoDTO == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(statusNotificacaoDTO);
        return ResponseEntity.ok(statusNotificacaoDTO);
    }

    // Endpoint para listar todos os status de notificações
    @GetMapping
    public ResponseEntity<List<StatusNotificacaoDTO>> listarStatusNotificacao() {
        List<StatusNotificacaoDTO> statusList = statusNotificacaoService.listarStatusNotificacao(null);
        List<StatusNotificacaoDTO> statusComLinks = statusList.stream()
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(statusComLinks);
    }

    // Endpoint para atualizar um status de notificação
    @PutMapping("/{id}")
    public ResponseEntity<StatusNotificacaoDTO> atualizarStatusNotificacao(@PathVariable Long id, @Valid @RequestBody StatusNotificacaoDTO statusNotificacaoDTO) {
        StatusNotificacaoDTO updatedStatus = statusNotificacaoService.atualizarStatusNotificacao(id, statusNotificacaoDTO);
        if (updatedStatus == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(updatedStatus);
        return ResponseEntity.ok(updatedStatus);
    }

    // Endpoint para excluir um status de notificação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirStatusNotificacao(@PathVariable Long id) {
        boolean isDeleted = statusNotificacaoService.excluirStatusNotificacao(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(StatusNotificacaoDTO statusNotificacaoDTO) {
        statusNotificacaoDTO.add(linkTo(methodOn(StatusNotificacaoController.class).obterStatusNotificacaoPorId(statusNotificacaoDTO.getStatusNotificacaoId())).withSelfRel());
        statusNotificacaoDTO.add(linkTo(methodOn(StatusNotificacaoController.class).listarStatusNotificacao()).withRel("status-notificacoes"));
        statusNotificacaoDTO.add(linkTo(methodOn(StatusNotificacaoController.class).atualizarStatusNotificacao(statusNotificacaoDTO.getStatusNotificacaoId(), statusNotificacaoDTO)).withRel("atualizar"));
        statusNotificacaoDTO.add(linkTo(methodOn(StatusNotificacaoController.class).excluirStatusNotificacao(statusNotificacaoDTO.getStatusNotificacaoId())).withRel("excluir"));
    }
}
