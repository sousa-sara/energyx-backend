package com.example.energyx.controller;

import com.example.energyx.dto.OperadoresDTO;
import com.example.energyx.service.interfaces.OperadoresService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/operadores")
public class OperadoresController {

    @Autowired
    private OperadoresService operadoresService;

    // Endpoint para cadastrar um novo operador
    @PostMapping("/cadastro")
    public ResponseEntity<OperadoresDTO> cadastrarOperador(@Valid @RequestBody OperadoresDTO operadoresDTO) {
        OperadoresDTO operadorCadastrado = operadoresService.cadastrarOperador(operadoresDTO);
        adicionarLinks(operadorCadastrado);
        return new ResponseEntity<>(operadorCadastrado, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os operadores com paginação
    @GetMapping("/listar-paginado")
    public ResponseEntity<Page<OperadoresDTO>> listarOperadores(
            @RequestParam(defaultValue = "0") int pagina,   // Número da página (inicia em 0)
            @RequestParam(defaultValue = "10") int tamanho) { // Tamanho da página (default 10)

        try {
            // Chama o serviço para obter a lista de operadores paginados
            Page<OperadoresDTO> operadoresPage = operadoresService.listarOperadoresPaginado(pagina, tamanho);

            // Adiciona links na resposta, se necessário
            operadoresPage.forEach(this::adicionarLinks);

            // Retorna a resposta paginada
            return ResponseEntity.ok(operadoresPage);
        } catch (Exception e) {
            // Loga o erro para monitoramento
            e.printStackTrace();

            // Em caso de erro, retorna uma página vazia ou uma resposta adequada
            // O ideal é retornar uma página vazia, já que o retorno esperado é Page<OperadoresDTO>
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Page.empty());
        }
    }

    // Endpoint para obter um operador por ID
    @GetMapping("/{id}")
    public ResponseEntity<OperadoresDTO> obterOperadorPorId(@PathVariable Long id) {
        OperadoresDTO operadorDTO = operadoresService.obterOperadorPorId(id);
        adicionarLinks(operadorDTO);
        return ResponseEntity.ok(operadorDTO);
    }

    // Endpoint para atualizar um operador
    @PutMapping("/{id}")
    public ResponseEntity<OperadoresDTO> atualizarOperador(@PathVariable Long id, @Valid @RequestBody OperadoresDTO operadoresDTO) {
        OperadoresDTO operadorAtualizado = operadoresService.atualizarOperador(id, operadoresDTO);
        adicionarLinks(operadorAtualizado);
        return ResponseEntity.ok(operadorAtualizado);
    }

    // Endpoint para excluir um operador
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirOperador(@PathVariable Long id) {
        boolean excluido = operadoresService.excluirOperador(id);
        if (excluido) {
            return ResponseEntity.ok("Operador excluído com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Operador não encontrado");
    }

    // Método para adicionar links HATEOAS ao DTO
    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(OperadoresDTO operadoresDTO) {
        operadoresDTO.add(linkTo(methodOn(OperadoresController.class).obterOperadorPorId(operadoresDTO.getOperadorId())).withSelfRel());
        operadoresDTO.add(linkTo(methodOn(OperadoresController.class)
                .listarOperadores(0, 10)) // Define os parâmetros da página para o link
                .withRel("operadores"));
    }
}
