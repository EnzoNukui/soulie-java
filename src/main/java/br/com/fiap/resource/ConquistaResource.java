package br.com.fiap.resource;

import br.com.fiap.bo.ConquistaBO;
import br.com.fiap.to.ConquistaTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/conquistas")
public class ConquistaResource {
    private ConquistaBO conquistaBO = new ConquistaBO();

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        List<ConquistaTO> resultado = conquistaBO.listarTodas();
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma conquista encontrada.");
        }
    }

    @GetMapping("/{idConquista}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idConquista) {
        ConquistaTO resultado = conquistaBO.buscarPorId(idConquista);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conquista não encontrada.");
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody @Valid ConquistaTO conquista) {
        try {
            ConquistaTO resultado = conquistaBO.inserir(conquista);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Não foi possível cadastrar a conquista. Nomes usados pelas regras automáticas são reservados.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir o registro.");
        }
    }

    @PutMapping("/{idConquista}")
    public ResponseEntity<?> atualizar(@PathVariable Long idConquista, @RequestBody @Valid ConquistaTO conquista) {
        try {
            conquista.setIdConquista(idConquista);
            ConquistaTO resultado = conquistaBO.atualizar(conquista);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            } else if (conquistaBO.buscarPorId(idConquista) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Conquistas automáticas são gerenciadas pelo sistema e não podem ser alteradas.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conquista não encontrada para atualização.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o registro.");
        }
    }

    @DeleteMapping("/{idConquista}")
    public ResponseEntity<String> excluir(@PathVariable Long idConquista) {
        if (conquistaBO.excluir(idConquista)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Conquista excluída com sucesso.");
        } else if (conquistaBO.buscarPorId(idConquista) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conquistas automáticas ou vinculadas a usuários não podem ser excluídas.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conquista não encontrada.");
        }
    }
}
