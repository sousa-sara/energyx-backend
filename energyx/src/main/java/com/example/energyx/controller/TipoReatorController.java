package com.example.energyx.controller;

import com.example.energyx.dto.TipoReatorDTO;
import com.example.energyx.service.interfaces.TipoReatorService;
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
@RequestMapping("/api/tipo-reatores")
public class TipoReatorController {

    @Autowired
    private TipoReatorService tipoReatorService;

    // Endpoint para criar um novo tipo de reator
    @PostMapping
    public ResponseEntity<TipoReatorDTO> criarTipoReator(@Valid @RequestBody TipoReatorDTO tipoReatorDTO) {
        TipoReatorDTO createdTipoReator = tipoReatorService.criarTipoReator(tipoReatorDTO);
        adicionarLinks(createdTipoReator);
        return new ResponseEntity<>(createdTipoReator, HttpStatus.CREATED);
    }

    // Endpoint para obter um tipo de reator por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoReatorDTO> obterTipoReatorPorId(@PathVariable Long id) {
        TipoReatorDTO tipoReatorDTO = tipoReatorService.obterTipoReatorPorId(id);
        if (tipoReatorDTO == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(tipoReatorDTO);
        return ResponseEntity.ok(tipoReatorDTO);
    }

    // Endpoint para listar todos os tipos de reatores
    @GetMapping
    public ResponseEntity<List<TipoReatorDTO>> listarTipoReator() {
        List<TipoReatorDTO> tipoReatorList = tipoReatorService.listarTipoReator(null);
        List<TipoReatorDTO> tipoReatorComLinks = tipoReatorList.stream()
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tipoReatorComLinks);
    }

    // Endpoint para atualizar um tipo de reator
    @PutMapping("/{id}")
    public ResponseEntity<TipoReatorDTO> atualizarTipoReator(@PathVariable Long id, @Valid @RequestBody TipoReatorDTO tipoReatorDTO) {
        TipoReatorDTO updatedTipoReator = tipoReatorService.atualizarTipoReator(id, tipoReatorDTO);
        if (updatedTipoReator == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(updatedTipoReator);
        return ResponseEntity.ok(updatedTipoReator);
    }

    // Endpoint para excluir um tipo de reator
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTipoReator(@PathVariable Long id) {
        boolean isDeleted = tipoReatorService.excluirTipoReator(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(TipoReatorDTO tipoReatorDTO) {
        tipoReatorDTO.add(linkTo(methodOn(TipoReatorController.class).obterTipoReatorPorId(tipoReatorDTO.getTipoReatorId())).withSelfRel());
        tipoReatorDTO.add(linkTo(methodOn(TipoReatorController.class).listarTipoReator()).withRel("tipo-reatores"));
        tipoReatorDTO.add(linkTo(methodOn(TipoReatorController.class).atualizarTipoReator(tipoReatorDTO.getTipoReatorId(), tipoReatorDTO)).withRel("atualizar"));
        tipoReatorDTO.add(linkTo(methodOn(TipoReatorController.class).excluirTipoReator(tipoReatorDTO.getTipoReatorId())).withRel("excluir"));
    }
}
