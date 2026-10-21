package br.com.fiap.resource;

import br.com.fiap.bo.AvatarBO;
import br.com.fiap.to.AvatarTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/avatares")
public class AvatarResource {
    private AvatarBO avatarBO = new AvatarBO();

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<AvatarTO> resultado = avatarBO.listarTodos();
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum avatar encontrado.");
        }
    }

    @GetMapping("/{idAvatar}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idAvatar) {
        AvatarTO resultado = avatarBO.buscarPorId(idAvatar);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avatar não encontrado.");
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Long idUsuario) {
        AvatarTO resultado = avatarBO.buscarPorUsuario(idUsuario);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avatar não encontrado.");
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody @Valid AvatarTO avatar) {
        try {
            AvatarTO resultado = avatarBO.inserir(avatar);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar o avatar.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir o registro.");
        }
    }

    @PutMapping("/{idAvatar}")
    public ResponseEntity<?> atualizar(@PathVariable Long idAvatar, @RequestBody @Valid AvatarTO avatar) {
        try {
            avatar.setIdAvatar(idAvatar);
            AvatarTO resultado = avatarBO.atualizar(avatar);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avatar não encontrado para atualização.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o registro.");
        }
    }

    @DeleteMapping("/{idAvatar}")
    public ResponseEntity<String> excluir(@PathVariable Long idAvatar) {
        if (avatarBO.excluir(idAvatar)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Avatar excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avatar não encontrado.");
        }
    }
}
