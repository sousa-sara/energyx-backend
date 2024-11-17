package com.example.energyx.service.interfaces;

import com.example.energyx.dto.TipoReatorDTO;
import java.util.List;

public interface TipoReatorService {

    TipoReatorDTO criarTipoReator(TipoReatorDTO tipoReatorDTO);
    TipoReatorDTO obterTipoReatorPorId(Long id);
    List<TipoReatorDTO> listarTipoReator(TipoReatorDTO tipoReatorDTO);
    TipoReatorDTO atualizarTipoReator(Long id, TipoReatorDTO tipoReatorDTO);
    boolean excluirTipoReator(Long id);

}
