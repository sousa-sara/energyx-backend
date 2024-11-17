package com.example.energyx.service.implementations;

import com.example.energyx.dto.NiveisOperacionaisDTO;
import com.example.energyx.entity.NiveisOperacionais;
import com.example.energyx.entity.Reatores;
import com.example.energyx.repository.NiveisOperacionaisRepository;
import com.example.energyx.repository.ReatoresRepository;
import com.example.energyx.service.interfaces.NiveisOperacionaisService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NiveisOperacionaisServiceImpl implements NiveisOperacionaisService {

    @Autowired
    private NiveisOperacionaisRepository niveisOperacionaisRepository;

    @Autowired
    private ReatoresRepository reatoresRepository;

    @Override
    public NiveisOperacionaisDTO criarNiveisOperacionais(NiveisOperacionaisDTO niveisOperacionaisDTO) {
        NiveisOperacionais niveisOperacionais = mapearDTOParaEntidade(niveisOperacionaisDTO);
        niveisOperacionais = niveisOperacionaisRepository.save(niveisOperacionais);
        return mapearEntidadeParaDTO(niveisOperacionais);
    }

    @Override
    public NiveisOperacionaisDTO obterNiveisOperacionaisPorId(Long id) {
        NiveisOperacionais niveisOperacionais = niveisOperacionaisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Níveis Operacionais não encontrados com ID: " + id));
        return mapearEntidadeParaDTO(niveisOperacionais);
    }

    @Override
    public List<NiveisOperacionaisDTO> listarNiveisOperacionais(NiveisOperacionaisDTO niveisOperacionaisDTO) {
        List<NiveisOperacionais> niveisOperacionais = niveisOperacionaisRepository.findAll();
        return niveisOperacionais.stream()
                .map(this::mapearEntidadeParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NiveisOperacionaisDTO atualizarNiveisOperacionais(Long id, NiveisOperacionaisDTO niveisOperacionaisDTO) {
        // Recupera a entidade existente ou lança exceção caso não exista
        NiveisOperacionais niveisOperacionais = niveisOperacionaisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Níveis Operacionais não encontrados com ID: " + id));

        // Atualiza os dados da entidade com os dados do DTO
        niveisOperacionais = mapearDTOParaEntidade(niveisOperacionaisDTO);

        // Salva a entidade atualizada
        niveisOperacionais = niveisOperacionaisRepository.save(niveisOperacionais);

        // Aqui é que a conversão precisa ser feita corretamente: mapear a entidade para DTO
        return mapearEntidadeParaDTO(niveisOperacionais);
    }

    @Override
    public boolean excluirNiveisOperacionais(Long id) {
        niveisOperacionaisRepository.deleteById(id);
        return true;
    }

    // Método privado para mapear entidade para DTO
    private NiveisOperacionaisDTO mapearEntidadeParaDTO(NiveisOperacionais niveisOperacionais) {
        return new NiveisOperacionaisDTO(
                niveisOperacionais.getNivelOperacionalId(),
                niveisOperacionais.getDataHoraMedicao(),
                niveisOperacionais.getPressao(),
                niveisOperacionais.getTemperatura(),
                niveisOperacionais.getRadiacao(),
                niveisOperacionais.getReatores() != null ? niveisOperacionais.getReatores().getReatorId() : null // Verificação se o reator existe
        );
    }

    // Método para mapear DTO para entidade
    private NiveisOperacionais mapearDTOParaEntidade(NiveisOperacionaisDTO niveisOperacionaisDTO) {
        NiveisOperacionais niveisOperacionais = new NiveisOperacionais();
        niveisOperacionais.setNivelOperacionalId(niveisOperacionaisDTO.getNivelOperacionalId());
        niveisOperacionais.setDataHoraMedicao(niveisOperacionaisDTO.getDataHoraMedicao());
        niveisOperacionais.setPressao(niveisOperacionaisDTO.getPressao());
        niveisOperacionais.setTemperatura(niveisOperacionaisDTO.getTemperatura());
        niveisOperacionais.setRadiacao(niveisOperacionaisDTO.getRadiacao());

        // Assumindo que você tem um método para buscar o reator se necessário
        Reatores reator = reatoresRepository.findById(niveisOperacionaisDTO.getReatorId())
                .orElseThrow(() -> new EntityNotFoundException("Reator não encontrado"));
        niveisOperacionais.setReatores(reator);

        return niveisOperacionais;
    }
}
