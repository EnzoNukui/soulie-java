package br.com.fiap.resource;

import br.com.fiap.bo.StreakBO;
import br.com.fiap.to.StreakTO;
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

}
