package com.example.energyx.service.implementations;

import com.example.energyx.dto.TipoReatorDTO;
import com.example.energyx.entity.TipoReator;
import com.example.energyx.repository.TipoReatorRepository;
import com.example.energyx.service.interfaces.TipoReatorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoReatorServiceImpl implements TipoReatorService {

    @Autowired
    private TipoReatorRepository tipoReatorRepository;

    @Override
    public TipoReatorDTO criarTipoReator(TipoReatorDTO tipoReatorDTO) {
        TipoReator tipoReator = mapearDTOParaEntidade(tipoReatorDTO);
        tipoReator = tipoReatorRepository.save(tipoReator);
        return mapearEntidadeParaDTO(tipoReator);
    }

    @Override
    public TipoReatorDTO obterTipoReatorPorId(Long id) {
        TipoReator tipoReator = tipoReatorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de Reator não encontrado com ID: " + id));
        return mapearEntidadeParaDTO(tipoReator);
    }

    @Override
    public List<TipoReatorDTO> listarTipoReator(TipoReatorDTO tipoReatorDTO) {
        List<TipoReator> tipoReatores = tipoReatorRepository.findAll();
        return tipoReatores.stream()
                .map(this::mapearEntidadeParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TipoReatorDTO atualizarTipoReator(Long id, TipoReatorDTO tipoReatorDTO) {
        TipoReator tipoReator = tipoReatorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de Reator não encontrado com ID: " + id));
        tipoReator = mapearDTOParaEntidade(tipoReatorDTO, tipoReator);
        tipoReator = tipoReatorRepository.save(tipoReator);
        return mapearEntidadeParaDTO(tipoReator);
    }

    @Override
    public boolean excluirTipoReator(Long id) {
        tipoReatorRepository.deleteById(id);
        return true;
    }

    // Método privado para mapear entidade para DTO
    private TipoReatorDTO mapearEntidadeParaDTO(TipoReator tipoReator) {
        return new TipoReatorDTO(
                tipoReator.getTipoReatorId(),
                tipoReator.getDescricaoReator(),
                tipoReator.getCapacidadeEnergia(),
                tipoReator.getTecnologia(),
                tipoReator.getFabricante()
        );
    }

    // Método privado para mapear DTO para entidade
    private TipoReator mapearDTOParaEntidade(TipoReatorDTO tipoReatorDTO) {
        TipoReator tipoReator = new TipoReator();
        tipoReator.setDescricaoReator(tipoReatorDTO.getDescricaoReator());
        tipoReator.setCapacidadeEnergia(tipoReatorDTO.getCapacidadeEnergia());
        tipoReator.setTecnologia(tipoReatorDTO.getTecnologia());
        tipoReator.setFabricante(tipoReatorDTO.getFabricante());
        return tipoReator;
    }

    // Método privado para mapear DTO para entidade (atualizando um tipo de reator existente)
    private TipoReator mapearDTOParaEntidade(TipoReatorDTO tipoReatorDTO, TipoReator tipoReator) {
        tipoReator.setDescricaoReator(tipoReatorDTO.getDescricaoReator());
        tipoReator.setCapacidadeEnergia(tipoReatorDTO.getCapacidadeEnergia());
        tipoReator.setTecnologia(tipoReatorDTO.getTecnologia());
        tipoReator.setFabricante(tipoReatorDTO.getFabricante());
        return tipoReator;
    }
}
