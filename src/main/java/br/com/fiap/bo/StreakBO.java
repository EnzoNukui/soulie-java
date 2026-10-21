package br.com.fiap.bo;

import br.com.fiap.dao.StreakDAO;
import br.com.fiap.to.StreakTO;
import java.util.ArrayList;

public class StreakBO {
    private StreakDAO streakDAO = new StreakDAO();

    public ArrayList<StreakTO> listarTodos() {
        return streakDAO.listarTodos();
    }

    public StreakTO buscarPorId(Long idStreak) {
        return streakDAO.buscarPorId(idStreak);
    }

    public StreakTO buscarPorUsuario(Long idUsuario) {
        return streakDAO.buscarPorUsuario(idUsuario);
    }

    public StreakTO inserir(StreakTO streak) {
        return streakDAO.inserir(streak);
    }

    public StreakTO atualizar(StreakTO streak) {
        return streakDAO.atualizar(streak);
    }

    public boolean excluir(Long idStreak) {
        return streakDAO.excluir(idStreak);
    }

}
