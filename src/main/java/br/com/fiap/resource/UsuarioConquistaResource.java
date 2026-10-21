package br.com.fiap.resource;

import br.com.fiap.bo.UsuarioConquistaBO;
import br.com.fiap.to.UsuarioConquistaTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario-conquistas")
public class UsuarioConquistaResource {
    private UsuarioConquistaBO usuarioConquistaBO = new UsuarioConquistaBO();

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable Long idUsuario) {
        List<UsuarioConquistaTO> resultado = usuarioConquistaBO.listarPorUsuario(idUsuario);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma conquista de usuário encontrada.");
        }
    }

    @GetMapping("/conquista/{idConquista}")
    public ResponseEntity<?> listarPorConquista(@PathVariable Long idConquista) {
        List<UsuarioConquistaTO> resultado = usuarioConquistaBO.listarPorConquista(idConquista);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma conquista de usuário encontrada.");
        }
    }

    @GetMapping("/{idUsuario}/{idConquista}")
    public ResponseEntity<?> buscarPorIds(@PathVariable Long idUsuario, @PathVariable Long idConquista) {
        UsuarioConquistaTO resultado = usuarioConquistaBO.buscarPorIds(idUsuario, idConquista);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conquista do usuário não encontrada.");
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody @Valid UsuarioConquistaTO usuarioConquista) {
        try {
            UsuarioConquistaTO resultado = usuarioConquistaBO.inserir(usuarioConquista);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar a conquista do usuário.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir o registro.");
        }
    }

    @PutMapping("/{idUsuario}/{idConquista}")
    public ResponseEntity<?> atualizar(@PathVariable Long idUsuario, @PathVariable Long idConquista,
                                       @RequestBody @Valid UsuarioConquistaTO usuarioConquista) {
        try {
            usuarioConquista.setIdUsuario(idUsuario);
            usuarioConquista.setIdConquista(idConquista);
            UsuarioConquistaTO resultado = usuarioConquistaBO.atualizar(usuarioConquista);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conquista do usuário não encontrada para atualização.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o registro.");
        }
    }

    @DeleteMapping("/{idUsuario}/{idConquista}")
    public ResponseEntity<String> excluir(@PathVariable Long idUsuario, @PathVariable Long idConquista) {
        if (usuarioConquistaBO.excluir(idUsuario, idConquista)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Conquista do usuário excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conquista do usuário não encontrada.");
        }
    }
}
