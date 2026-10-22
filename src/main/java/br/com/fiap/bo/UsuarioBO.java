package br.com.fiap.bo;

import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.dao.AvatarDAO;
import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dao.StreakDAO;
import br.com.fiap.to.UsuarioTO;
import br.com.fiap.to.AvatarTO;
import br.com.fiap.to.StreakTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.UUID;

public class UsuarioBO {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private AvatarDAO avatarDAO = new AvatarDAO();
    private StreakDAO streakDAO = new StreakDAO();
    private static final ZoneId ZONA_NEGOCIO = ZoneId.of("America/Sao_Paulo");

    public ArrayList<UsuarioTO> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public UsuarioTO buscarPorId(Long idUsuario) {
        return usuarioDAO.buscarPorId(idUsuario);
    }

    public UsuarioTO buscarPorEmail(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }

    public UsuarioTO buscarPorIdentificadorJornada(String identificadorJornada) {
        if (identificadorJornada == null || identificadorJornada.isBlank()) {
            return null;
        }
        return usuarioDAO.buscarPorIdentificadorJornada(identificadorJornada);
    }

    public UsuarioTO inserir(UsuarioTO usuario) {
        if (usuario == null || usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            return null;
        }

        LocalDate hoje = LocalDate.now(ZONA_NEGOCIO);
        usuario.setPontosAcumulados(0);
        usuario.setStatus("A");
        usuario.setIdentificadorJornada(UUID.randomUUID().toString());

        Connection conexao = ConnectionFactory.getNewConnection();
        if (conexao == null) {
            return null;
        }

        try (conexao) {
            conexao.setAutoCommit(false);
            try {
                if (usuarioDAO.buscarPorEmail(conexao, usuario.getEmail()) != null) {
                    conexao.rollback();
                    return null;
                }

                UsuarioTO usuarioCriado = usuarioDAO.inserir(conexao, usuario);
                if (usuarioCriado == null || usuarioCriado.getIdUsuario() == null) {
                    conexao.rollback();
                    return null;
                }

                AvatarTO avatar = new AvatarTO();
                avatar.setIdUsuario(usuarioCriado.getIdUsuario());
                avatar.setNivelEvolucao(1);
                avatar.setExperiencia(0);
                avatar.setTemperamento("curioso");
                if (avatarDAO.inserir(conexao, avatar) == null) {
                    conexao.rollback();
                    return null;
                }

                StreakTO streak = new StreakTO();
                streak.setIdUsuario(usuarioCriado.getIdUsuario());
                streak.setDiasConsecutivos(0);
                // A coluna DT_ULTIMO_ACESSO é NOT NULL no script atual; a data só é usada
                // como valor inicial até o usuário registrar sua primeira atividade.
                streak.setDataUltimaAtividade(hoje);
                if (streakDAO.inserir(conexao, streak) == null) {
                    conexao.rollback();
                    return null;
                }

                conexao.commit();
                return usuarioCriado;
            } catch (SQLException | RuntimeException e) {
                try {
                    conexao.rollback();
                } catch (SQLException erroRollback) {
                    e.addSuppressed(erroRollback);
                }
                System.out.println("Erro ao cadastrar jornada: " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na transação da jornada: " + e.getMessage());
            return null;
        }
    }

    public UsuarioTO atualizar(UsuarioTO usuario) {
        if (usuario == null || usuario.getIdUsuario() == null) {
            return null;
        }

        UsuarioTO usuarioAtual = usuarioDAO.buscarPorId(usuario.getIdUsuario());
        if (usuarioAtual == null) {
            return null;
        }

        // Pontos, status e identificador são controlados pelas regras da jornada.
        usuario.setPontosAcumulados(usuarioAtual.getPontosAcumulados());
        usuario.setStatus(usuarioAtual.getStatus());
        usuario.setIdentificadorJornada(usuarioAtual.getIdentificadorJornada());
        return usuarioDAO.atualizar(usuario);
    }

    public boolean excluir(Long idUsuario) {
        if (idUsuario == null) {
            return false;
        }

        Connection conexao = ConnectionFactory.getNewConnection();
        if (conexao == null) {
            return false;
        }

        try (conexao) {
            conexao.setAutoCommit(false);
            try {
                if (usuarioDAO.buscarPorId(conexao, idUsuario) == null
                        || !avatarDAO.excluirPorUsuario(conexao, idUsuario)
                        || !streakDAO.excluirPorUsuario(conexao, idUsuario)
                        || !usuarioDAO.excluir(conexao, idUsuario)) {
                    conexao.rollback();
                    return false;
                }

                conexao.commit();
                return true;
            } catch (SQLException | RuntimeException e) {
                try {
                    conexao.rollback();
                } catch (SQLException erroRollback) {
                    e.addSuppressed(erroRollback);
                }
                System.out.println("Erro ao excluir jornada do usuário: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro na transação de exclusão do usuário: " + e.getMessage());
            return false;
        }
    }

}
