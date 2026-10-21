package br.com.fiap.bo;

import br.com.fiap.dao.ConquistaDAO;
import br.com.fiap.to.ConquistaTO;
import java.util.ArrayList;

public class ConquistaBO {
    private ConquistaDAO conquistaDAO = new ConquistaDAO();

    public ArrayList<ConquistaTO> listarTodas() {
        return conquistaDAO.listarTodas();
    }

    public ConquistaTO buscarPorId(Long idConquista) {
        return conquistaDAO.buscarPorId(idConquista);
    }

    public ConquistaTO inserir(ConquistaTO conquista) {
        return conquistaDAO.inserir(conquista);
    }

    public ConquistaTO atualizar(ConquistaTO conquista) {
        return conquistaDAO.atualizar(conquista);
    }

    public boolean excluir(Long idConquista) {
        return conquistaDAO.excluir(idConquista);
    }

}
