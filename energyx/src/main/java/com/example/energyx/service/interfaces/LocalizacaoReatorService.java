package com.example.energyx.service.interfaces;

import com.example.energyx.dto.LocalizacaoReatorDTO;
import java.util.List;

public interface LocalizacaoReatorService {

    LocalizacaoReatorDTO criarLocalizacaoReator(LocalizacaoReatorDTO localizacaoReatorDTO);
    LocalizacaoReatorDTO obterLocalizacaoReatorPorId(Long id);
    List<LocalizacaoReatorDTO> listarLocalizacoesReator(LocalizacaoReatorDTO localizacaoReatorDTO);
    LocalizacaoReatorDTO atualizarLocalizacaoReator(Long id, LocalizacaoReatorDTO localizacaoReatorDTO);
    boolean excluirLocalizacaoReator(Long id);

}
