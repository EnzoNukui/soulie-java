package br.com.fiap.resource;

import br.com.fiap.bo.AtividadeBO;
import br.com.fiap.to.AtividadeTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeResource {
    private AtividadeBO atividadeBO = new AtividadeBO();

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        List<AtividadeTO> resultado = atividadeBO.listarTodas();
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma atividade encontrada.");
        }
    }

    @GetMapping("/{idRegistro}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idRegistro) {
        AtividadeTO resultado = atividadeBO.buscarPorId(idRegistro);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atividade não encontrada.");
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable Long idUsuario) {
        List<AtividadeTO> resultado = atividadeBO.listarPorUsuario(idUsuario);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma atividade encontrada.");
        }
    }

    @GetMapping("/missao/{idMissao}")
    public ResponseEntity<?> listarPorMissao(@PathVariable Long idMissao) {
        List<AtividadeTO> resultado = atividadeBO.listarPorMissao(idMissao);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma atividade encontrada.");
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody @Valid AtividadeTO atividade) {
        try {
            AtividadeTO resultado = atividadeBO.inserir(atividade);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar a atividade.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir o registro.");
        }
    }

    @PutMapping("/{idRegistro}")
    public ResponseEntity<?> atualizar(@PathVariable Long idRegistro, @RequestBody @Valid AtividadeTO atividade) {
        try {
            atividade.setIdRegistro(idRegistro);
            AtividadeTO resultado = atividadeBO.atualizar(atividade);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atividade não encontrada para atualização.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o registro.");
        }
    }

    @DeleteMapping("/{idRegistro}")
    public ResponseEntity<String> excluir(@PathVariable Long idRegistro) {
        if (atividadeBO.excluir(idRegistro)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Atividade excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atividade não encontrada.");
        }
    }
}
