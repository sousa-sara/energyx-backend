package com.example.energyx.service.interfaces;

import com.example.energyx.dto.OperadoresDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OperadoresService {

    OperadoresDTO cadastrarOperador(OperadoresDTO operadoresDTO);
    OperadoresDTO obterOperadorPorId(Long id);
    // Modificado para retornar uma Page de OperadoresDTO
    Page<OperadoresDTO> listarOperadoresPaginado(int pagina, int tamanho);
    OperadoresDTO atualizarOperador(Long id, OperadoresDTO operadoresDTO);
    boolean excluirOperador(Long id);

}
