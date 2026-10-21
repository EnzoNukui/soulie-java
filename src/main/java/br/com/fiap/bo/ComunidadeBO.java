package br.com.fiap.bo;

import br.com.fiap.dao.ComunidadeDAO;
import br.com.fiap.to.ComunidadeTO;
import java.util.ArrayList;

public class ComunidadeBO {
    private ComunidadeDAO comunidadeDAO = new ComunidadeDAO();

    public ArrayList<ComunidadeTO> listarTodas() {
        return comunidadeDAO.listarTodas();
    }

    public ComunidadeTO buscarPorId(Long idComunidade) {
        return comunidadeDAO.buscarPorId(idComunidade);
    }

    public ComunidadeTO inserir(ComunidadeTO comunidade) {
        return comunidadeDAO.inserir(comunidade);
    }

    public ComunidadeTO atualizar(ComunidadeTO comunidade) {
        return comunidadeDAO.atualizar(comunidade);
    }

    public boolean excluir(Long idComunidade) {
        return comunidadeDAO.excluir(idComunidade);
    }

}
