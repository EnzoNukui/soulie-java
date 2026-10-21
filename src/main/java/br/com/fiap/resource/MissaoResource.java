package br.com.fiap.resource;

import br.com.fiap.bo.MissaoBO;
import br.com.fiap.to.MissaoTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissaoResource {
    private MissaoBO missaoBO = new MissaoBO();

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        List<MissaoTO> resultado = missaoBO.listarTodas();
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma missão encontrada.");
        }
    }

    @GetMapping("/{idMissao}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idMissao) {
        MissaoTO resultado = missaoBO.buscarPorId(idMissao);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Missão não encontrada.");
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody @Valid MissaoTO missao) {
        try {
            MissaoTO resultado = missaoBO.inserir(missao);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar a missão.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir o registro.");
        }
    }

    @PutMapping("/{idMissao}")
    public ResponseEntity<?> atualizar(@PathVariable Long idMissao, @RequestBody @Valid MissaoTO missao) {
        try {
            missao.setIdMissao(idMissao);
            MissaoTO resultado = missaoBO.atualizar(missao);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Missão não encontrada para atualização.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o registro.");
        }
    }

    @DeleteMapping("/{idMissao}")
    public ResponseEntity<String> excluir(@PathVariable Long idMissao) {
        if (missaoBO.excluir(idMissao)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Missão excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Missão não encontrada.");
        }
    }
}
