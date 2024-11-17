package com.example.energyx.controller;

import com.example.energyx.dto.RelatoriosTurnoDTO;
import com.example.energyx.service.interfaces.RelatoriosTurnoService;
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
@RequestMapping("/api/relatorios-turno")
public class RelatoriosTurnoController {

    @Autowired
    private RelatoriosTurnoService relatoriosTurnoService;

    // Endpoint para criar um novo relatório de turno
    @PostMapping
    public ResponseEntity<RelatoriosTurnoDTO> criarRelatorioTurno(@Valid @RequestBody RelatoriosTurnoDTO relatoriosTurnoDTO) {
        RelatoriosTurnoDTO createdRelatorioTurno = relatoriosTurnoService.criarRelatorioTurno(relatoriosTurnoDTO);
        adicionarLinks(createdRelatorioTurno);
        return new ResponseEntity<>(createdRelatorioTurno, HttpStatus.CREATED);
    }

    // Endpoint para obter um relatório de turno por ID
    @GetMapping("/{id}")
    public ResponseEntity<RelatoriosTurnoDTO> obterRelatorioTurnoPorId(@PathVariable Long id) {
        RelatoriosTurnoDTO relatoriosTurnoDTO = relatoriosTurnoService.obterRelatorioTurnoPorId(id);
        if (relatoriosTurnoDTO == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(relatoriosTurnoDTO);
        return ResponseEntity.ok(relatoriosTurnoDTO);
    }

    // Endpoint para listar todos os relatórios de turno
    @GetMapping
    public ResponseEntity<List<RelatoriosTurnoDTO>> listarRelatoriosTurno() {
        List<RelatoriosTurnoDTO> relatoriosTurnoList = relatoriosTurnoService.listarRelatoriosTurno();
        List<RelatoriosTurnoDTO> relatoriosComLinks = relatoriosTurnoList.stream()
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(relatoriosComLinks);
    }

    // Endpoint para atualizar um relatório de turno existente
    @PutMapping("/{id}")
    public ResponseEntity<RelatoriosTurnoDTO> atualizarRelatorioTurno(@PathVariable Long id, @Valid @RequestBody RelatoriosTurnoDTO relatoriosTurnoDTO) {
        RelatoriosTurnoDTO updatedRelatorioTurno = relatoriosTurnoService.atualizarRelatorioTurno(id, relatoriosTurnoDTO);
        if (updatedRelatorioTurno == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(updatedRelatorioTurno);
        return ResponseEntity.ok(updatedRelatorioTurno);
    }

    // Endpoint para excluir um relatório de turno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirRelatorioTurno(@PathVariable Long id) {
        boolean isDeleted = relatoriosTurnoService.excluirRelatorioTurno(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(RelatoriosTurnoDTO relatoriosTurnoDTO) {
        relatoriosTurnoDTO.add(linkTo(methodOn(RelatoriosTurnoController.class).obterRelatorioTurnoPorId(relatoriosTurnoDTO.getRelatorioTurnoId())).withSelfRel());
        relatoriosTurnoDTO.add(linkTo(methodOn(RelatoriosTurnoController.class).listarRelatoriosTurno()).withRel("relatorios-turno"));
        relatoriosTurnoDTO.add(linkTo(methodOn(RelatoriosTurnoController.class).atualizarRelatorioTurno(relatoriosTurnoDTO.getRelatorioTurnoId(), relatoriosTurnoDTO)).withRel("atualizar"));
        relatoriosTurnoDTO.add(linkTo(methodOn(RelatoriosTurnoController.class).excluirRelatorioTurno(relatoriosTurnoDTO.getRelatorioTurnoId())).withRel("excluir"));
    }
}
