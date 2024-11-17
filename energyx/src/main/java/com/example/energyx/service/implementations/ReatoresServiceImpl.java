package com.example.energyx.service.implementations;

import com.example.energyx.dto.ReatoresDTO;
import com.example.energyx.entity.Reatores;
import com.example.energyx.entity.TipoReator;
import com.example.energyx.entity.LocalizacaoReator;
import com.example.energyx.repository.ReatoresRepository;
import com.example.energyx.repository.TipoReatorRepository;
import com.example.energyx.repository.LocalizacaoReatorRepository;
import com.example.energyx.service.interfaces.ReatoresService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReatoresServiceImpl implements ReatoresService {

    @Autowired
    private ReatoresRepository reatoresRepository;

    @Autowired
    private TipoReatorRepository tipoReatorRepository;

    @Autowired
    private LocalizacaoReatorRepository localizacaoReatorRepository;

    @Override
    public ReatoresDTO criarReator(ReatoresDTO reatoresDTO) {
        TipoReator tipoReator = tipoReatorRepository.findById(reatoresDTO.getTipoReatorId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de Reator não encontrado com ID: " + reatoresDTO.getTipoReatorId()));

        LocalizacaoReator localizacaoReator = localizacaoReatorRepository.findById(reatoresDTO.getLocalizacaoReatorId())
                .orElseThrow(() -> new EntityNotFoundException("Localização do Reator não encontrada com ID: " + reatoresDTO.getLocalizacaoReatorId()));

        Reatores reatores = mapearDTOParaEntidade(reatoresDTO, tipoReator, localizacaoReator);
        reatores = reatoresRepository.save(reatores);
        return mapearEntidadeParaDTO(reatores);
    }

    @Override
    public ReatoresDTO obterReatorPorId(Long id) {
        Reatores reatores = reatoresRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reator não encontrado com ID: " + id));
        return mapearEntidadeParaDTO(reatores);
    }

    @Override
    public List<ReatoresDTO> listarReatores(ReatoresDTO reatoresDTO) {
        List<Reatores> reatoresList = reatoresRepository.findAll();
        return reatoresList.stream()
                .map(this::mapearEntidadeParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReatoresDTO atualizarReator(Long id, ReatoresDTO reatoresDTO) {
        Reatores reatores = reatoresRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reator não encontrado com ID: " + id));

        TipoReator tipoReator = tipoReatorRepository.findById(reatoresDTO.getTipoReatorId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de Reator não encontrado com ID: " + reatoresDTO.getTipoReatorId()));

        LocalizacaoReator localizacaoReator = localizacaoReatorRepository.findById(reatoresDTO.getLocalizacaoReatorId())
                .orElseThrow(() -> new EntityNotFoundException("Localização do Reator não encontrada com ID: " + reatoresDTO.getLocalizacaoReatorId()));

        reatores = mapearDTOParaEntidade(reatoresDTO, tipoReator, localizacaoReator);
        reatores = reatoresRepository.save(reatores);
        return mapearEntidadeParaDTO(reatores);
    }

    @Override
    public boolean excluirReator(Long id) {
        reatoresRepository.deleteById(id);
        return true;
    }

    private ReatoresDTO mapearEntidadeParaDTO(Reatores reatores) {
        return new ReatoresDTO(
                reatores.getReatorId(),
                reatores.getNomeReator(),
                reatores.getPressaoMax(),
                reatores.getTemperaturaMax(),
                reatores.getRadiacaoMax(),
                reatores.getTipoReator().getTipoReatorId(),
                reatores.getLocalizacaoReator().getLocReatorId()
        );
    }

    private Reatores mapearDTOParaEntidade(ReatoresDTO reatoresDTO, TipoReator tipoReator, LocalizacaoReator localizacaoReator) {
        Reatores reatores = new Reatores();
        reatores.setNomeReator(reatoresDTO.getNomeReator());
        reatores.setPressaoMax(reatoresDTO.getPressaoMax());
        reatores.setTemperaturaMax(reatoresDTO.getTemperaturaMax());
        reatores.setRadiacaoMax(reatoresDTO.getRadiacaoMax());
        reatores.setTipoReator(tipoReator);
        reatores.setLocalizacaoReator(localizacaoReator);
        return reatores;
    }
}
