package com.example.energyx.service.interfaces;

import com.example.energyx.dto.NiveisOperacionaisDTO;
import java.util.List;

public interface NiveisOperacionaisService {

    NiveisOperacionaisDTO criarNiveisOperacionais(NiveisOperacionaisDTO niveisOperacionaisDTO);
    NiveisOperacionaisDTO obterNiveisOperacionaisPorId(Long id);
    List<NiveisOperacionaisDTO> listarNiveisOperacionais(NiveisOperacionaisDTO niveisOperacionaisDTO);
    NiveisOperacionaisDTO atualizarNiveisOperacionais(Long id, NiveisOperacionaisDTO niveisOperacionaisDTO);
    boolean excluirNiveisOperacionais(Long id);

}
