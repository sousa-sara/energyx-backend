package com.example.energyx.controller;

import com.example.energyx.dto.LocalizacaoReatorDTO;
import com.example.energyx.service.interfaces.LocalizacaoReatorService;
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
@RequestMapping("/localizacoes-reator")
public class LocalizacaoReatorController {

    @Autowired
    private LocalizacaoReatorService localizacaoReatorService;

    // Endpoint para criar uma nova localização de reator
    @PostMapping
    public ResponseEntity<LocalizacaoReatorDTO> criarLocalizacaoReator(@Valid @RequestBody LocalizacaoReatorDTO localizacaoReatorDTO) {
        LocalizacaoReatorDTO created = localizacaoReatorService.criarLocalizacaoReator(localizacaoReatorDTO);
        adicionarLinks(created);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Endpoint para obter uma localização de reator por ID
    @GetMapping("/{id}")
    public ResponseEntity<LocalizacaoReatorDTO> obterLocalizacaoReatorPorId(@PathVariable Long id) {
        LocalizacaoReatorDTO localizacaoReatorDTO = localizacaoReatorService.obterLocalizacaoReatorPorId(id);
        if (localizacaoReatorDTO == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(localizacaoReatorDTO);
        return ResponseEntity.ok(localizacaoReatorDTO);
    }

    // Endpoint para listar todas as localizações de reator
    @GetMapping
    public ResponseEntity<List<LocalizacaoReatorDTO>> listarLocalizacoesReator() {
        List<LocalizacaoReatorDTO> localizacoesReator = localizacaoReatorService.listarLocalizacoesReator(null);
        List<LocalizacaoReatorDTO> localizacoesDTO = localizacoesReator.stream()
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(localizacoesDTO);
    }

    // Endpoint para atualizar uma localização de reator
    @PutMapping("/{id}")
    public ResponseEntity<LocalizacaoReatorDTO> atualizarLocalizacaoReator(@PathVariable Long id, @Valid @RequestBody LocalizacaoReatorDTO localizacaoReatorDTO) {
        LocalizacaoReatorDTO updated = localizacaoReatorService.atualizarLocalizacaoReator(id, localizacaoReatorDTO);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(updated);
        return ResponseEntity.ok(updated);
    }

    // Endpoint para excluir uma localização de reator
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirLocalizacaoReator(@PathVariable Long id) {
        boolean isDeleted = localizacaoReatorService.excluirLocalizacaoReator(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(LocalizacaoReatorDTO localizacaoReatorDTO) {
        localizacaoReatorDTO.add(linkTo(methodOn(LocalizacaoReatorController.class).obterLocalizacaoReatorPorId(localizacaoReatorDTO.getLocReatorId())).withSelfRel());
        localizacaoReatorDTO.add(linkTo(methodOn(LocalizacaoReatorController.class).listarLocalizacoesReator()).withRel("localizacoes-reatores"));
        localizacaoReatorDTO.add(linkTo(methodOn(LocalizacaoReatorController.class).atualizarLocalizacaoReator(localizacaoReatorDTO.getLocReatorId(), localizacaoReatorDTO)).withRel("atualizar"));
        localizacaoReatorDTO.add(linkTo(methodOn(LocalizacaoReatorController.class).excluirLocalizacaoReator(localizacaoReatorDTO.getLocReatorId())).withRel("excluir"));
    }
}
