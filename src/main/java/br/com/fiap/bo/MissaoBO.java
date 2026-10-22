package br.com.fiap.bo;

import br.com.fiap.dao.MissaoDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.MissaoTO;
import java.util.ArrayList;

public class MissaoBO {
    private MissaoDAO missaoDAO = new MissaoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public ArrayList<MissaoTO> listarTodas() {
        return missaoDAO.listarTodas();
    }

    public MissaoTO buscarPorId(Long idMissao) {
        return missaoDAO.buscarPorId(idMissao);
    }

    public MissaoTO inserir(MissaoTO missao) {
        if (missao == null || missao.getIdUsuarioCriador() == null) {
            return null;
        }
        if (usuarioDAO.buscarPorId(missao.getIdUsuarioCriador()) == null) {
            return null;
        }
        return missaoDAO.inserir(missao);
    }

    public MissaoTO atualizar(MissaoTO missao) {
        if (missao == null || missao.getIdMissao() == null) {
            return null;
        }

        MissaoTO missaoAtual = missaoDAO.buscarPorId(missao.getIdMissao());
        if (missaoAtual == null || missaoAtual.getIdUsuarioCriador() == null) {
            // Missões oficiais são mantidas pelo sistema e não recebem recompensa via CRUD.
            return null;
        }

        // A classificação da missão é definida na criação e não pode ser alterada pelo cliente.
        missao.setIdUsuarioCriador(missaoAtual.getIdUsuarioCriador());
        return missaoDAO.atualizar(missao);
    }

    public boolean excluir(Long idMissao) {
        MissaoTO missao = missaoDAO.buscarPorId(idMissao);
        if (missao == null || missao.getIdUsuarioCriador() == null) {
            return false;
        }
        return missaoDAO.excluir(idMissao);
    }

}
