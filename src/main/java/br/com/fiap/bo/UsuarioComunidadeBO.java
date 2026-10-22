package br.com.fiap.bo;

import br.com.fiap.dao.UsuarioComunidadeDAO;
import br.com.fiap.dao.ComunidadeDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.UsuarioComunidadeTO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class UsuarioComunidadeBO {
    private UsuarioComunidadeDAO usuarioComunidadeDAO = new UsuarioComunidadeDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ComunidadeDAO comunidadeDAO = new ComunidadeDAO();
    private static final ZoneId ZONA_NEGOCIO = ZoneId.of("America/Sao_Paulo");

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
        if (usuarioComunidade == null || usuarioComunidade.getIdUsuario() == null
                || usuarioComunidade.getIdComunidade() == null) {
            return null;
        }
        if (usuarioDAO.buscarPorId(usuarioComunidade.getIdUsuario()) == null
                || comunidadeDAO.buscarPorId(usuarioComunidade.getIdComunidade()) == null
                || usuarioComunidadeDAO.buscarPorIds(
                        usuarioComunidade.getIdUsuario(), usuarioComunidade.getIdComunidade()) != null) {
            return null;
        }

        usuarioComunidade.setDataIngresso(LocalDate.now(ZONA_NEGOCIO));
        return usuarioComunidadeDAO.inserir(usuarioComunidade);
    }

    public UsuarioComunidadeTO atualizar(UsuarioComunidadeTO usuarioComunidade) {
        return usuarioComunidadeDAO.atualizar(usuarioComunidade);
    }

    public boolean excluir(Long idUsuario, Long idComunidade) {
        return usuarioComunidadeDAO.excluir(idUsuario, idComunidade);
    }

}
