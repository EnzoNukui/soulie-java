package br.com.fiap.resource;

import br.com.fiap.bo.PostagemBO;
import br.com.fiap.to.PostagemTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/postagens")
public class PostagemResource {
    private PostagemBO postagemBO = new PostagemBO();

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        List<PostagemTO> resultado = postagemBO.listarTodas();
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma postagem encontrada.");
        }
    }

    @GetMapping("/{idPostagem}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idPostagem) {
        PostagemTO resultado = postagemBO.buscarPorId(idPostagem);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Postagem não encontrada.");
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable Long idUsuario) {
        List<PostagemTO> resultado = postagemBO.listarPorUsuario(idUsuario);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma postagem encontrada.");
        }
    }

    @GetMapping("/comunidade/{idComunidade}")
    public ResponseEntity<?> listarPorComunidade(@PathVariable Long idComunidade) {
        List<PostagemTO> resultado = postagemBO.listarPorComunidade(idComunidade);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma postagem encontrada.");
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody @Valid PostagemTO postagem) {
        try {
            PostagemTO resultado = postagemBO.inserir(postagem);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar a postagem.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir o registro.");
        }
    }

    @PutMapping("/{idPostagem}")
    public ResponseEntity<?> atualizar(@PathVariable Long idPostagem, @RequestBody @Valid PostagemTO postagem) {
        try {
            postagem.setIdPostagem(idPostagem);
            PostagemTO resultado = postagemBO.atualizar(postagem);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Postagem não encontrada para atualização.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o registro.");
        }
    }

    @DeleteMapping("/{idPostagem}")
    public ResponseEntity<String> excluir(@PathVariable Long idPostagem) {
        if (postagemBO.excluir(idPostagem)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Postagem excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Postagem não encontrada.");
        }
    }
}
