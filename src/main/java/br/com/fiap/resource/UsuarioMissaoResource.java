package br.com.fiap.resource;

import br.com.fiap.bo.UsuarioMissaoBO;
import br.com.fiap.to.UsuarioMissaoTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario-missoes")
public class UsuarioMissaoResource {
    private UsuarioMissaoBO usuarioMissaoBO = new UsuarioMissaoBO();

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable Long idUsuario) {
        List<UsuarioMissaoTO> resultado = usuarioMissaoBO.listarPorUsuario(idUsuario);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma participação em missão encontrada.");
        }
    }

    @GetMapping("/missao/{idMissao}")
    public ResponseEntity<?> listarPorMissao(@PathVariable Long idMissao) {
        List<UsuarioMissaoTO> resultado = usuarioMissaoBO.listarPorMissao(idMissao);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma participação em missão encontrada.");
        }
    }

    @GetMapping("/{idUsuario}/{idMissao}")
    public ResponseEntity<?> buscarPorIds(@PathVariable Long idUsuario, @PathVariable Long idMissao) {
        UsuarioMissaoTO resultado = usuarioMissaoBO.buscarPorIds(idUsuario, idMissao);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participação na missão não encontrada.");
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody @Valid UsuarioMissaoTO usuarioMissao) {
        try {
            UsuarioMissaoTO resultado = usuarioMissaoBO.inserir(usuarioMissao);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar a participação na missão.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir o registro.");
        }
    }

    @PutMapping("/{idUsuario}/{idMissao}")
    public ResponseEntity<?> atualizar(@PathVariable Long idUsuario, @PathVariable Long idMissao,
                                       @RequestBody @Valid UsuarioMissaoTO usuarioMissao) {
        try {
            usuarioMissao.setIdUsuario(idUsuario);
            usuarioMissao.setIdMissao(idMissao);
            UsuarioMissaoTO resultado = usuarioMissaoBO.atualizar(usuarioMissao);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("O status e as datas da participação são controlados pelo registro de atividades.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o registro.");
        }
    }

    @DeleteMapping("/{idUsuario}/{idMissao}")
    public ResponseEntity<String> excluir(@PathVariable Long idUsuario, @PathVariable Long idMissao) {
        if (usuarioMissaoBO.excluir(idUsuario, idMissao)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Participação na missão excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Não é possível remover a participação se ela estiver concluída ou tiver atividades registradas.");
        }
    }
}
