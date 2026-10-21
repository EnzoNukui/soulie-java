package br.com.fiap.resource;

import br.com.fiap.bo.ComunidadeBO;
import br.com.fiap.to.ComunidadeTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/comunidades")
public class ComunidadeResource {
    private ComunidadeBO comunidadeBO = new ComunidadeBO();

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        List<ComunidadeTO> resultado = comunidadeBO.listarTodas();
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma comunidade encontrada.");
        }
    }

    @GetMapping("/{idComunidade}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idComunidade) {
        ComunidadeTO resultado = comunidadeBO.buscarPorId(idComunidade);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidade não encontrada.");
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody @Valid ComunidadeTO comunidade) {
        try {
            ComunidadeTO resultado = comunidadeBO.inserir(comunidade);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar a comunidade.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir o registro.");
        }
    }

    @PutMapping("/{idComunidade}")
    public ResponseEntity<?> atualizar(@PathVariable Long idComunidade, @RequestBody @Valid ComunidadeTO comunidade) {
        try {
            comunidade.setIdComunidade(idComunidade);
            ComunidadeTO resultado = comunidadeBO.atualizar(comunidade);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidade não encontrada para atualização.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o registro.");
        }
    }

    @DeleteMapping("/{idComunidade}")
    public ResponseEntity<String> excluir(@PathVariable Long idComunidade) {
        if (comunidadeBO.excluir(idComunidade)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Comunidade excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidade não encontrada.");
        }
    }
}
