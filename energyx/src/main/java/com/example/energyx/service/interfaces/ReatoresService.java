package com.example.energyx.service.interfaces;

import com.example.energyx.dto.ReatoresDTO;
import java.util.List;

public interface ReatoresService {

    ReatoresDTO criarReator(ReatoresDTO reatoresDTO);
    ReatoresDTO obterReatorPorId(Long id);
    List<ReatoresDTO> listarReatores(ReatoresDTO reatoresDTO);
    ReatoresDTO atualizarReator(Long id, ReatoresDTO reatoresDTO);
    boolean excluirReator(Long id);

}
