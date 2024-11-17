package com.example.energyx.controller;

import com.example.energyx.dto.ReatoresDTO;
import com.example.energyx.service.interfaces.ReatoresService;
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
@RequestMapping("/api/reatores")
public class ReatoresController {

    @Autowired
    private ReatoresService reatoresService;

    // Endpoint para criar um novo reator
    @PostMapping
    public ResponseEntity<ReatoresDTO> criarReator(@Valid @RequestBody ReatoresDTO reatoresDTO) {
        ReatoresDTO createdReator = reatoresService.criarReator(reatoresDTO);
        adicionarLinks(createdReator);
        return new ResponseEntity<>(createdReator, HttpStatus.CREATED);
    }

    // Endpoint para obter um reator por ID
    @GetMapping("/{id}")
    public ResponseEntity<ReatoresDTO> obterReatorPorId(@PathVariable Long id) {
        ReatoresDTO reatoresDTO = reatoresService.obterReatorPorId(id);
        if (reatoresDTO == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(reatoresDTO);
        return ResponseEntity.ok(reatoresDTO);
    }

    // Endpoint para listar todos os reatores
    @GetMapping
    public ResponseEntity<List<ReatoresDTO>> listarReatores() {
        List<ReatoresDTO> reatoresList = reatoresService.listarReatores(null); // null é passado pois não há filtro
        List<ReatoresDTO> reatoresComLinks = reatoresList.stream()
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reatoresComLinks);
    }

    // Endpoint para atualizar um reator existente
    @PutMapping("/{id}")
    public ResponseEntity<ReatoresDTO> atualizarReator(@PathVariable Long id, @Valid @RequestBody ReatoresDTO reatoresDTO) {
        ReatoresDTO updatedReator = reatoresService.atualizarReator(id, reatoresDTO);
        if (updatedReator == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(updatedReator);
        return ResponseEntity.ok(updatedReator);
    }

    // Endpoint para excluir um reator
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirReator(@PathVariable Long id) {
        boolean isDeleted = reatoresService.excluirReator(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(ReatoresDTO reatoresDTO) {
        reatoresDTO.add(linkTo(methodOn(ReatoresController.class).obterReatorPorId(reatoresDTO.getReatorId())).withSelfRel());
        reatoresDTO.add(linkTo(methodOn(ReatoresController.class).listarReatores()).withRel("reatores"));
        reatoresDTO.add(linkTo(methodOn(ReatoresController.class).atualizarReator(reatoresDTO.getReatorId(), reatoresDTO)).withRel("atualizar"));
        reatoresDTO.add(linkTo(methodOn(ReatoresController.class).excluirReator(reatoresDTO.getReatorId())).withRel("excluir"));
    }
}
