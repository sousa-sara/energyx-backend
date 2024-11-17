package com.example.energyx.controller;

import com.example.energyx.dto.NiveisOperacionaisDTO;
import com.example.energyx.service.interfaces.NiveisOperacionaisService;
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
@RequestMapping("/api/niveis-operacionais")
public class NiveisOperacionaisController {

    @Autowired
    private NiveisOperacionaisService niveisOperacionaisService;

    // Endpoint para criar novos níveis operacionais
    @PostMapping
    public ResponseEntity<NiveisOperacionaisDTO> criarNiveisOperacionais(@Valid @RequestBody NiveisOperacionaisDTO niveisOperacionaisDTO) {
        NiveisOperacionaisDTO createdNiveisOperacionais = niveisOperacionaisService.criarNiveisOperacionais(niveisOperacionaisDTO);
        adicionarLinks(createdNiveisOperacionais);
        return new ResponseEntity<>(createdNiveisOperacionais, HttpStatus.CREATED);
    }

    // Endpoint para obter níveis operacionais por ID
    @GetMapping("/{id}")
    public ResponseEntity<NiveisOperacionaisDTO> obterNiveisOperacionaisPorId(@PathVariable Long id) {
        NiveisOperacionaisDTO niveisOperacionaisDTO = niveisOperacionaisService.obterNiveisOperacionaisPorId(id);
        if (niveisOperacionaisDTO == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(niveisOperacionaisDTO);
        return ResponseEntity.ok(niveisOperacionaisDTO);
    }

    // Endpoint para listar todos os níveis operacionais
    @GetMapping
    public ResponseEntity<List<NiveisOperacionaisDTO>> listarNiveisOperacionais() {
        List<NiveisOperacionaisDTO> niveisOperacionaisDTOList = niveisOperacionaisService.listarNiveisOperacionais(null);
        List<NiveisOperacionaisDTO> niveisDTOComLinks = niveisOperacionaisDTOList.stream()
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(niveisDTOComLinks);
    }

    // Endpoint para atualizar níveis operacionais
    @PutMapping("/{id}")
    public ResponseEntity<NiveisOperacionaisDTO> atualizarNiveisOperacionais(@PathVariable Long id, @Valid @RequestBody NiveisOperacionaisDTO niveisOperacionaisDTO) {
        NiveisOperacionaisDTO updatedNiveisOperacionais = niveisOperacionaisService.atualizarNiveisOperacionais(id, niveisOperacionaisDTO);
        if (updatedNiveisOperacionais == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(updatedNiveisOperacionais);
        return ResponseEntity.ok(updatedNiveisOperacionais);
    }

    // Endpoint para excluir níveis operacionais
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirNiveisOperacionais(@PathVariable Long id) {
        boolean isDeleted = niveisOperacionaisService.excluirNiveisOperacionais(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(NiveisOperacionaisDTO niveisOperacionaisDTO) {
        niveisOperacionaisDTO.add(linkTo(methodOn(NiveisOperacionaisController.class).obterNiveisOperacionaisPorId(niveisOperacionaisDTO.getNivelOperacionalId())).withSelfRel());
        niveisOperacionaisDTO.add(linkTo(methodOn(NiveisOperacionaisController.class).listarNiveisOperacionais()).withRel("niveis-operacionais"));
        niveisOperacionaisDTO.add(linkTo(methodOn(NiveisOperacionaisController.class).atualizarNiveisOperacionais(niveisOperacionaisDTO.getNivelOperacionalId(), niveisOperacionaisDTO)).withRel("atualizar"));
        niveisOperacionaisDTO.add(linkTo(methodOn(NiveisOperacionaisController.class).excluirNiveisOperacionais(niveisOperacionaisDTO.getNivelOperacionalId())).withRel("excluir"));
    }
}
