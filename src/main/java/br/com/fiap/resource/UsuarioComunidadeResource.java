package br.com.fiap.resource;

import br.com.fiap.bo.UsuarioComunidadeBO;
import br.com.fiap.to.UsuarioComunidadeTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario-comunidades")
public class UsuarioComunidadeResource {
    private UsuarioComunidadeBO usuarioComunidadeBO = new UsuarioComunidadeBO();

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable Long idUsuario) {
        List<UsuarioComunidadeTO> resultado = usuarioComunidadeBO.listarPorUsuario(idUsuario);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma participação em comunidade encontrada.");
        }
    }

    @GetMapping("/comunidade/{idComunidade}")
    public ResponseEntity<?> listarPorComunidade(@PathVariable Long idComunidade) {
        List<UsuarioComunidadeTO> resultado = usuarioComunidadeBO.listarPorComunidade(idComunidade);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma participação em comunidade encontrada.");
        }
    }

    @GetMapping("/{idUsuario}/{idComunidade}")
    public ResponseEntity<?> buscarPorIds(@PathVariable Long idUsuario, @PathVariable Long idComunidade) {
        UsuarioComunidadeTO resultado = usuarioComunidadeBO.buscarPorIds(idUsuario, idComunidade);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participação na comunidade não encontrada.");
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody @Valid UsuarioComunidadeTO usuarioComunidade) {
        try {
            UsuarioComunidadeTO resultado = usuarioComunidadeBO.inserir(usuarioComunidade);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar a participação na comunidade.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir o registro.");
        }
    }

    @PutMapping("/{idUsuario}/{idComunidade}")
    public ResponseEntity<?> atualizar(@PathVariable Long idUsuario, @PathVariable Long idComunidade,
                                       @RequestBody @Valid UsuarioComunidadeTO usuarioComunidade) {
        try {
            usuarioComunidade.setIdUsuario(idUsuario);
            usuarioComunidade.setIdComunidade(idComunidade);
            UsuarioComunidadeTO resultado = usuarioComunidadeBO.atualizar(usuarioComunidade);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participação na comunidade não encontrada para atualização.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o registro.");
        }
    }

    @DeleteMapping("/{idUsuario}/{idComunidade}")
    public ResponseEntity<String> excluir(@PathVariable Long idUsuario, @PathVariable Long idComunidade) {
        if (usuarioComunidadeBO.excluir(idUsuario, idComunidade)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Participação na comunidade excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participação na comunidade não encontrada.");
        }
    }
}
