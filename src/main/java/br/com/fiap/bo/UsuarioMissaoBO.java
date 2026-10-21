package br.com.fiap.bo;

import br.com.fiap.dao.UsuarioMissaoDAO;
import br.com.fiap.to.UsuarioMissaoTO;
import java.util.ArrayList;

public class UsuarioMissaoBO {
    private UsuarioMissaoDAO usuarioMissaoDAO = new UsuarioMissaoDAO();

    public ArrayList<UsuarioMissaoTO> listarPorUsuario(Long idUsuario) {
        return usuarioMissaoDAO.listarPorUsuario(idUsuario);
    }

    public ArrayList<UsuarioMissaoTO> listarPorMissao(Long idMissao) {
        return usuarioMissaoDAO.listarPorMissao(idMissao);
    }

    public UsuarioMissaoTO buscarPorIds(Long idUsuario, Long idMissao) {
        return usuarioMissaoDAO.buscarPorIds(idUsuario, idMissao);
    }

    public UsuarioMissaoTO inserir(UsuarioMissaoTO usuarioMissao) {
        return usuarioMissaoDAO.inserir(usuarioMissao);
    }

    public UsuarioMissaoTO atualizar(UsuarioMissaoTO usuarioMissao) {
        return usuarioMissaoDAO.atualizar(usuarioMissao);
    }

    public boolean excluir(Long idUsuario, Long idMissao) {
        return usuarioMissaoDAO.excluir(idUsuario, idMissao);
    }

}
