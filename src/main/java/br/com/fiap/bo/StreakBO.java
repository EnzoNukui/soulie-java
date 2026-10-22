package br.com.fiap.bo;

import br.com.fiap.dao.StreakDAO;
import br.com.fiap.to.StreakTO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class StreakBO {
    private StreakDAO streakDAO = new StreakDAO();

    public ArrayList<StreakTO> listarTodos() {
        ArrayList<StreakTO> streaks = streakDAO.listarTodos();
        if (streaks != null) {
            for (StreakTO streak : streaks) {
                normalizarStreakAtual(streak);
            }
        }
        return streaks;
    }

    public StreakTO buscarPorId(Long idStreak) {
        return normalizarStreakAtual(streakDAO.buscarPorId(idStreak));
    }

    public StreakTO buscarPorUsuario(Long idUsuario) {
        return normalizarStreakAtual(streakDAO.buscarPorUsuario(idUsuario));
    }

    private StreakTO normalizarStreakAtual(StreakTO streak) {
        if (streak != null && streak.getDataUltimaAtividade() != null
                && streak.getDataUltimaAtividade().isBefore(
                        LocalDate.now(ZoneId.of("America/Sao_Paulo")).minusDays(1))) {
            streak.setDiasConsecutivos(0);
        }
        return streak;
    }

}
