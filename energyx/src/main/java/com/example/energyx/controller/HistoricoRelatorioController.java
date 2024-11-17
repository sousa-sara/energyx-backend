package com.example.energyx.controller;

import com.example.energyx.dto.HistoricoRelatorioDTO;
import com.example.energyx.service.interfaces.HistoricoRelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/historico-relatorio")
public class HistoricoRelatorioController {

    @Autowired
    private HistoricoRelatorioService historicoRelatorioService;

    // Criar um novo histórico de relatório
    @PostMapping
    public ResponseEntity<HistoricoRelatorioDTO> criarHistoricoRelatorio(@RequestBody HistoricoRelatorioDTO historicoRelatorioDTO) {
        HistoricoRelatorioDTO criado = historicoRelatorioService.criarHistoricoRelatorio(historicoRelatorioDTO);
        adicionarLinks(criado);
        return new ResponseEntity<>(criado, HttpStatus.CREATED);
    }

    // Obter histórico de relatório por ID
    @GetMapping("/{id}")
    public ResponseEntity<HistoricoRelatorioDTO> obterHistoricoRelatorioPorId(@PathVariable Long id) {
        HistoricoRelatorioDTO historicoRelatorioDTO = historicoRelatorioService.obterHistoricoRelatorioPorId(id);
        if (historicoRelatorioDTO == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(historicoRelatorioDTO);
        return new ResponseEntity<>(historicoRelatorioDTO, HttpStatus.OK);
    }

    // Listar todos os históricos de relatório
    @GetMapping
    public ResponseEntity<List<HistoricoRelatorioDTO>> listarHistoricoRelatorios() {
        List<HistoricoRelatorioDTO> historicos = historicoRelatorioService.listarHistoricoRelatorios(null);
        // Adiciona links HATEOAS a cada DTO retornado
        List<HistoricoRelatorioDTO> historicosComLinks = historicos.stream()
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());
        return new ResponseEntity<>(historicosComLinks, HttpStatus.OK);
    }

    // Atualizar um histórico de relatório existente
    @PutMapping("/{id}")
    public ResponseEntity<HistoricoRelatorioDTO> atualizarHistoricoRelatorio(
            @PathVariable Long id,
            @RequestBody HistoricoRelatorioDTO historicoRelatorioDTO) {
        HistoricoRelatorioDTO atualizado = historicoRelatorioService.atualizarHistoricoRelatorio(id, historicoRelatorioDTO);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(atualizado);
        return new ResponseEntity<>(atualizado, HttpStatus.OK);
    }

    // Excluir um histórico de relatório por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirHistoricoRelatorio(@PathVariable Long id) {
        boolean isDeleted = historicoRelatorioService.excluirHistoricoRelatorio(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(HistoricoRelatorioDTO historicoRelatorioDTO) {
        historicoRelatorioDTO.add(linkTo(methodOn(HistoricoRelatorioController.class).obterHistoricoRelatorioPorId(historicoRelatorioDTO.getHistRelatorioId())).withSelfRel());
        historicoRelatorioDTO.add(linkTo(methodOn(HistoricoRelatorioController.class).listarHistoricoRelatorios()).withRel("historicos-relatorios"));
        historicoRelatorioDTO.add(linkTo(methodOn(HistoricoRelatorioController.class).atualizarHistoricoRelatorio(historicoRelatorioDTO.getHistRelatorioId(), historicoRelatorioDTO)).withRel("atualizar"));
        historicoRelatorioDTO.add(linkTo(methodOn(HistoricoRelatorioController.class).excluirHistoricoRelatorio(historicoRelatorioDTO.getHistRelatorioId())).withRel("excluir"));
    }
}
