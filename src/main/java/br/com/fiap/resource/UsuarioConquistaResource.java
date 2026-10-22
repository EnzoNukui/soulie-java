package br.com.fiap.resource;

import br.com.fiap.bo.UsuarioConquistaBO;
import br.com.fiap.to.UsuarioConquistaTO;
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

}
