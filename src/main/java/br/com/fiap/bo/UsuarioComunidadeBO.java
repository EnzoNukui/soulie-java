package br.com.fiap.bo;

import br.com.fiap.dao.UsuarioComunidadeDAO;
import br.com.fiap.to.UsuarioComunidadeTO;
import java.util.ArrayList;

public class UsuarioComunidadeBO {
    private UsuarioComunidadeDAO usuarioComunidadeDAO = new UsuarioComunidadeDAO();

    public ArrayList<UsuarioComunidadeTO> listarPorUsuario(Long idUsuario) {
        return usuarioComunidadeDAO.listarPorUsuario(idUsuario);
    }

    public ArrayList<UsuarioComunidadeTO> listarPorComunidade(Long idComunidade) {
        return usuarioComunidadeDAO.listarPorComunidade(idComunidade);
    }

    public UsuarioComunidadeTO buscarPorIds(Long idUsuario, Long idComunidade) {
        return usuarioComunidadeDAO.buscarPorIds(idUsuario, idComunidade);
    }

    public UsuarioComunidadeTO inserir(UsuarioComunidadeTO usuarioComunidade) {
        return usuarioComunidadeDAO.inserir(usuarioComunidade);
    }

    public UsuarioComunidadeTO atualizar(UsuarioComunidadeTO usuarioComunidade) {
        return usuarioComunidadeDAO.atualizar(usuarioComunidade);
    }

    public boolean excluir(Long idUsuario, Long idComunidade) {
        return usuarioComunidadeDAO.excluir(idUsuario, idComunidade);
    }

}
