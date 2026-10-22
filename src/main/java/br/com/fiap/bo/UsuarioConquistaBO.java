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

}
