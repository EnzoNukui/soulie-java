package br.com.fiap.bo;

import br.com.fiap.dao.AtividadeDAO;
import br.com.fiap.to.AtividadeTO;
import java.util.ArrayList;

public class AtividadeBO {
    private AtividadeDAO atividadeDAO = new AtividadeDAO();

    public ArrayList<AtividadeTO> listarTodas() {
        return atividadeDAO.listarTodas();
    }

    public AtividadeTO buscarPorId(Long idRegistro) {
        return atividadeDAO.buscarPorId(idRegistro);
    }

    public ArrayList<AtividadeTO> listarPorUsuario(Long idUsuario) {
        return atividadeDAO.listarPorUsuario(idUsuario);
    }

    public ArrayList<AtividadeTO> listarPorMissao(Long idMissao) {
        return atividadeDAO.listarPorMissao(idMissao);
    }

    public AtividadeTO inserir(AtividadeTO atividade) {
        return atividadeDAO.inserir(atividade);
    }

    public AtividadeTO atualizar(AtividadeTO atividade) {
        return atividadeDAO.atualizar(atividade);
    }

    public boolean excluir(Long idRegistro) {
        return atividadeDAO.excluir(idRegistro);
    }

}
