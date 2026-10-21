package br.com.fiap.resource;

import br.com.fiap.bo.StreakBO;
import br.com.fiap.to.StreakTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/streaks")
public class StreakResource {
    private StreakBO streakBO = new StreakBO();

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<StreakTO> resultado = streakBO.listarTodos();
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum streak encontrado.");
        }
    }

    @GetMapping("/{idStreak}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long idStreak) {
        StreakTO resultado = streakBO.buscarPorId(idStreak);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Streak não encontrado.");
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Long idUsuario) {
        StreakTO resultado = streakBO.buscarPorUsuario(idUsuario);
        if (resultado != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Streak não encontrado.");
        }
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody @Valid StreakTO streak) {
        try {
            StreakTO resultado = streakBO.inserir(streak);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar o streak.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir o registro.");
        }
    }

    @PutMapping("/{idStreak}")
    public ResponseEntity<?> atualizar(@PathVariable Long idStreak, @RequestBody @Valid StreakTO streak) {
        try {
            streak.setIdStreak(idStreak);
            StreakTO resultado = streakBO.atualizar(streak);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Streak não encontrado para atualização.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o registro.");
        }
    }

    @DeleteMapping("/{idStreak}")
    public ResponseEntity<String> excluir(@PathVariable Long idStreak) {
        if (streakBO.excluir(idStreak)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Streak excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Streak não encontrado.");
        }
    }
}
