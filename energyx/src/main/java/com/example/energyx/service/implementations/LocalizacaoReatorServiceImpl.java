package com.example.energyx.service.implementations;

import com.example.energyx.dto.LocalizacaoReatorDTO;
import com.example.energyx.entity.LocalizacaoReator;
import com.example.energyx.repository.LocalizacaoReatorRepository;
import com.example.energyx.service.interfaces.LocalizacaoReatorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalizacaoReatorServiceImpl implements LocalizacaoReatorService {

    @Autowired
    private LocalizacaoReatorRepository localizacaoReatorRepository;

    @Override
    public LocalizacaoReatorDTO criarLocalizacaoReator(LocalizacaoReatorDTO localizacaoReatorDTO) {
        LocalizacaoReator localizacaoReator = mapearDTOParaEntidade(localizacaoReatorDTO);
        localizacaoReator = localizacaoReatorRepository.save(localizacaoReator);
        return mapearEntidadeParaDTO(localizacaoReator);
    }

    @Override
    public LocalizacaoReatorDTO obterLocalizacaoReatorPorId(Long id) {
        LocalizacaoReator localizacaoReator = localizacaoReatorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Localização do Reator não encontrada com ID: " + id));
        return mapearEntidadeParaDTO(localizacaoReator);
    }

    @Override
    public List<LocalizacaoReatorDTO> listarLocalizacoesReator(LocalizacaoReatorDTO localizacaoReatorDTO) {
        List<LocalizacaoReator> localizacoesReator = localizacaoReatorRepository.findAll();
        return localizacoesReator.stream()
                .map(this::mapearEntidadeParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LocalizacaoReatorDTO atualizarLocalizacaoReator(Long id, LocalizacaoReatorDTO localizacaoReatorDTO) {
        LocalizacaoReator localizacaoReator = localizacaoReatorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Localização do Reator não encontrada com ID: " + id));
        localizacaoReator = mapearDTOParaEntidade(localizacaoReatorDTO, localizacaoReator);
        localizacaoReator = localizacaoReatorRepository.save(localizacaoReator);
        return mapearEntidadeParaDTO(localizacaoReator);
    }

    @Override
    public boolean excluirLocalizacaoReator(Long id) {
        localizacaoReatorRepository.deleteById(id);
        return true;
    }

    // Método privado para mapear entidade para DTO
    private LocalizacaoReatorDTO mapearEntidadeParaDTO(LocalizacaoReator localizacaoReator) {
        return new LocalizacaoReatorDTO(
                localizacaoReator.getLocReatorId(),
                localizacaoReator.getSetor(),
                localizacaoReator.getUnidade(),
                localizacaoReator.getDescricao()
        );
    }

    // Método privado para mapear DTO para entidade
    private LocalizacaoReator mapearDTOParaEntidade(LocalizacaoReatorDTO localizacaoReatorDTO) {
        LocalizacaoReator localizacaoReator = new LocalizacaoReator();
        localizacaoReator.setSetor(localizacaoReatorDTO.getSetor());
        localizacaoReator.setUnidade(localizacaoReatorDTO.getUnidade());
        localizacaoReator.setDescricao(localizacaoReatorDTO.getDescricao());
        return localizacaoReator;
    }

    // Método privado para mapear DTO para entidade (atualizando um reator existente)
    private LocalizacaoReator mapearDTOParaEntidade(LocalizacaoReatorDTO localizacaoReatorDTO, LocalizacaoReator localizacaoReator) {
        localizacaoReator.setSetor(localizacaoReatorDTO.getSetor());
        localizacaoReator.setUnidade(localizacaoReatorDTO.getUnidade());
        localizacaoReator.setDescricao(localizacaoReatorDTO.getDescricao());
        return localizacaoReator;
    }
}
