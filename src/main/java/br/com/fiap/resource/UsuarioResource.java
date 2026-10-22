package br.com.fiap.resource;

import br.com.fiap.bo.UsuarioBO;
import br.com.fiap.to.UsuarioTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
    private UsuarioBO usuarioBO = new UsuarioBO();

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<UsuarioTO> resultado = usuarioBO.listarTodos();
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum usuário encontrado.");
        }
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idUsuario) {
        UsuarioTO usuario = usuarioBO.buscarPorId(idUsuario);
        if (usuario != null) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    @GetMapping("/email")
    public ResponseEntity<?> buscarPorEmail(@RequestParam String email) {
        UsuarioTO usuario = usuarioBO.buscarPorEmail(email);
        if (usuario != null) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    @GetMapping("/jornada/{identificadorJornada}")
    public ResponseEntity<?> buscarPorIdentificadorJornada(@PathVariable String identificadorJornada) {
        UsuarioTO usuario = usuarioBO.buscarPorIdentificadorJornada(identificadorJornada);
        if (usuario != null) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jornada não encontrada.");
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody @Valid UsuarioTO usuario) {
        try {
            UsuarioTO resultado = usuarioBO.inserir(usuario);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar o usuário.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir o registro.");
        }
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<?> atualizar(@PathVariable Long idUsuario, @RequestBody @Valid UsuarioTO usuario) {
        try {
            usuario.setIdUsuario(idUsuario);
            UsuarioTO resultado = usuarioBO.atualizar(usuario);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado para atualização.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o registro.");
        }
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<String> excluir(@PathVariable Long idUsuario) {
        if (usuarioBO.excluir(idUsuario)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário excluído com sucesso.");
        } else if (usuarioBO.buscarPorId(idUsuario) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Usuário possui dados vinculados e não pode ser excluído.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }
}
