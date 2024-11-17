package com.example.energyx.controller;

import com.example.energyx.dto.TurnosDTO;
import com.example.energyx.service.interfaces.TurnosService;
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
@RequestMapping("/api/turnos")
public class TurnosController {

    @Autowired
    private TurnosService turnosService;

    // Endpoint para criar um novo turno
    @PostMapping
    public ResponseEntity<TurnosDTO> criarTurno(@Valid @RequestBody TurnosDTO turnosDTO) {
        TurnosDTO createdTurno = turnosService.criarTurno(turnosDTO);
        adicionarLinks(createdTurno);
        return new ResponseEntity<>(createdTurno, HttpStatus.CREATED);
    }

    // Endpoint para obter um turno por ID
    @GetMapping("/{id}")
    public ResponseEntity<TurnosDTO> obterTurnoPorId(@PathVariable Long id) {
        TurnosDTO turnosDTO = turnosService.obterTurnoPorId(id);
        if (turnosDTO == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(turnosDTO);
        return ResponseEntity.ok(turnosDTO);
    }

    // Endpoint para listar todos os turnos
    @GetMapping
    public ResponseEntity<List<TurnosDTO>> listarTurnos() {
        List<TurnosDTO> turnosList = turnosService.listarTurnos(null);
        List<TurnosDTO> turnosComLinks = turnosList.stream()
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(turnosComLinks);
    }

    // Endpoint para atualizar um turno
    @PutMapping("/{id}")
    public ResponseEntity<TurnosDTO> atualizarTurno(@PathVariable Long id, @Valid @RequestBody TurnosDTO turnosDTO) {
        TurnosDTO updatedTurno = turnosService.atualizarTurno(id, turnosDTO);
        if (updatedTurno == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(updatedTurno);
        return ResponseEntity.ok(updatedTurno);
    }

    // Endpoint para excluir um turno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTurno(@PathVariable Long id) {
        boolean isDeleted = turnosService.excluirTurno(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(TurnosDTO turnosDTO) {
        turnosDTO.add(linkTo(methodOn(TurnosController.class).obterTurnoPorId(turnosDTO.getTurnoId())).withSelfRel());
        turnosDTO.add(linkTo(methodOn(TurnosController.class).listarTurnos()).withRel("turnos"));
        turnosDTO.add(linkTo(methodOn(TurnosController.class).atualizarTurno(turnosDTO.getTurnoId(), turnosDTO)).withRel("atualizar"));
        turnosDTO.add(linkTo(methodOn(TurnosController.class).excluirTurno(turnosDTO.getTurnoId())).withRel("excluir"));
    }
}
