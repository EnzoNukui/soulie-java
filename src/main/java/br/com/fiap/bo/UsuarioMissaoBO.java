package br.com.fiap.bo;

import br.com.fiap.dao.UsuarioMissaoDAO;
import br.com.fiap.dao.AtividadeDAO;
import br.com.fiap.dao.MissaoDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.UsuarioMissaoTO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class UsuarioMissaoBO {
    private UsuarioMissaoDAO usuarioMissaoDAO = new UsuarioMissaoDAO();
    private AtividadeDAO atividadeDAO = new AtividadeDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private MissaoDAO missaoDAO = new MissaoDAO();
    private static final ZoneId ZONA_NEGOCIO = ZoneId.of("America/Sao_Paulo");

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
        if (usuarioMissao == null || usuarioMissao.getIdUsuario() == null
                || usuarioMissao.getIdMissao() == null) {
            return null;
        }
        if (usuarioDAO.buscarPorId(usuarioMissao.getIdUsuario()) == null
                || missaoDAO.buscarPorId(usuarioMissao.getIdMissao()) == null
                || usuarioMissaoDAO.buscarPorIds(usuarioMissao.getIdUsuario(), usuarioMissao.getIdMissao()) != null) {
            return null;
        }

        usuarioMissao.setDataInicio(LocalDate.now(ZONA_NEGOCIO));
        usuarioMissao.setDataConclusao(null);
        usuarioMissao.setStatus("EM_ANDAMENTO");
        return usuarioMissaoDAO.inserir(usuarioMissao);
    }

    public UsuarioMissaoTO atualizar(UsuarioMissaoTO usuarioMissao) {
        // Status e datas da participação são controlados pelo registro de atividades.
        return null;
    }

    public boolean excluir(Long idUsuario, Long idMissao) {
        if (idUsuario == null || idMissao == null) {
            return false;
        }

        UsuarioMissaoTO participacao = usuarioMissaoDAO.buscarPorIds(idUsuario, idMissao);
        if (participacao == null || !"EM_ANDAMENTO".equalsIgnoreCase(participacao.getStatus())
                || atividadeDAO.existePorUsuarioEMissao(idUsuario, idMissao)) {
            return false;
        }
        return usuarioMissaoDAO.excluir(idUsuario, idMissao);
    }

}
