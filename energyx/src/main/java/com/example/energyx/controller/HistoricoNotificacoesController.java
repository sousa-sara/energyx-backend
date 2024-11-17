package com.example.energyx.controller;

import com.example.energyx.dto.HistoricoNotificacoesDTO;
import com.example.energyx.service.interfaces.HistoricoNotificacoesService;
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
@RequestMapping("/api/historicos-notificacoes")
public class HistoricoNotificacoesController {

    @Autowired
    private HistoricoNotificacoesService historicoNotificacoesService;

    @PostMapping
    public ResponseEntity<HistoricoNotificacoesDTO> criarHistoricoNotificacao(@Valid @RequestBody HistoricoNotificacoesDTO historicoNotificacoesDTO) {
        HistoricoNotificacoesDTO novoHistorico = historicoNotificacoesService.criarHistoricoNotificacao(historicoNotificacoesDTO);
        adicionarLinks(novoHistorico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoHistorico);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoNotificacoesDTO> obterHistoricoNotificacao(@PathVariable Long id) {
        HistoricoNotificacoesDTO historicoNotificacao = historicoNotificacoesService.obterHistoricoNotificacaoPorId(id);
        if (historicoNotificacao == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(historicoNotificacao);
        return ResponseEntity.ok(historicoNotificacao);
    }

    @GetMapping
    public ResponseEntity<List<HistoricoNotificacoesDTO>> listarHistoricosNotificacoes() {
        // Chama o serviço para listar os históricos de notificações
        List<HistoricoNotificacoesDTO> historicosNotificacoes = historicoNotificacoesService.listarHistoricosNotificacoes();

        // Adiciona links HATEOAS a cada DTO retornado
        List<HistoricoNotificacoesDTO> historicoDTOs = historicosNotificacoes.stream()
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());

        return ResponseEntity.ok(historicoDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoNotificacoesDTO> atualizarHistoricoNotificacao(@PathVariable Long id, @Valid @RequestBody HistoricoNotificacoesDTO historicoNotificacoesDTO) {
        HistoricoNotificacoesDTO historicoAtualizado = historicoNotificacoesService.atualizarHistoricoNotificacao(id, historicoNotificacoesDTO);
        if (historicoAtualizado == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(historicoAtualizado);
        return ResponseEntity.ok(historicoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirHistoricoNotificacao(@PathVariable Long id) {
        if (historicoNotificacoesService.excluirHistoricoNotificacao(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(HistoricoNotificacoesDTO historicoNotificacoesDTO) {
        historicoNotificacoesDTO.add(linkTo(methodOn(HistoricoNotificacoesController.class).obterHistoricoNotificacao(historicoNotificacoesDTO.getHistNotificacaoId())).withSelfRel());
        historicoNotificacoesDTO.add(linkTo(methodOn(HistoricoNotificacoesController.class).listarHistoricosNotificacoes()).withRel("historicos-notificacoes"));
        historicoNotificacoesDTO.add(linkTo(methodOn(HistoricoNotificacoesController.class).atualizarHistoricoNotificacao(historicoNotificacoesDTO.getHistNotificacaoId(), historicoNotificacoesDTO)).withRel("atualizar"));
        historicoNotificacoesDTO.add(linkTo(methodOn(HistoricoNotificacoesController.class).excluirHistoricoNotificacao(historicoNotificacoesDTO.getHistNotificacaoId())).withRel("excluir"));
    }
}
