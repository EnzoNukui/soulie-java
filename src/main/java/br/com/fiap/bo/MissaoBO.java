package br.com.fiap.bo;

import br.com.fiap.dao.MissaoDAO;
import br.com.fiap.to.MissaoTO;
import java.util.ArrayList;

public class MissaoBO {
    private MissaoDAO missaoDAO = new MissaoDAO();

    public ArrayList<MissaoTO> listarTodas() {
        return missaoDAO.listarTodas();
    }

    public MissaoTO buscarPorId(Long idMissao) {
        return missaoDAO.buscarPorId(idMissao);
    }

    public MissaoTO inserir(MissaoTO missao) {
        return missaoDAO.inserir(missao);
    }

    public MissaoTO atualizar(MissaoTO missao) {
        return missaoDAO.atualizar(missao);
    }

    public boolean excluir(Long idMissao) {
        return missaoDAO.excluir(idMissao);
    }

}
