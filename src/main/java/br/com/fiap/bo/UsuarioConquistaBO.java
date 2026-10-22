package br.com.fiap.bo;

import br.com.fiap.dao.UsuarioConquistaDAO;
import br.com.fiap.to.UsuarioConquistaTO;
import java.util.ArrayList;

public class UsuarioConquistaBO {
    private UsuarioConquistaDAO usuarioConquistaDAO = new UsuarioConquistaDAO();

    public ArrayList<UsuarioConquistaTO> listarPorUsuario(Long idUsuario) {
        return usuarioConquistaDAO.listarPorUsuario(idUsuario);
    }

    public ArrayList<UsuarioConquistaTO> listarPorConquista(Long idConquista) {
        return usuarioConquistaDAO.listarPorConquista(idConquista);
    }

    public UsuarioConquistaTO buscarPorIds(Long idUsuario, Long idConquista) {
        return usuarioConquistaDAO.buscarPorIds(idUsuario, idConquista);
    }

    public UsuarioConquistaTO inserir(UsuarioConquistaTO usuarioConquista) {
        // Conquistas são concedidas automaticamente pelas regras de atividade.
        return null;
    }

    public UsuarioConquistaTO atualizar(UsuarioConquistaTO usuarioConquista) {
        // A data de concessão é definida pelo sistema.
        return null;
    }

    public boolean excluir(Long idUsuario, Long idConquista) {
        // A concessão registrada faz parte do histórico da jornada.
        return false;
    }

}
