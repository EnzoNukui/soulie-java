package br.com.fiap.bo;

import br.com.fiap.dao.AtividadeDAO;
import br.com.fiap.dao.AvatarDAO;
import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dao.ConquistaDAO;
import br.com.fiap.dao.MissaoDAO;
import br.com.fiap.dao.StreakDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.dao.UsuarioConquistaDAO;
import br.com.fiap.dao.UsuarioMissaoDAO;
import br.com.fiap.to.AtividadeTO;
import br.com.fiap.to.AvatarTO;
import br.com.fiap.to.ConquistaTO;
import br.com.fiap.to.MissaoTO;
import br.com.fiap.to.StreakTO;
import br.com.fiap.to.UsuarioConquistaTO;
import br.com.fiap.to.UsuarioMissaoTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class AtividadeBO {
    private AtividadeDAO atividadeDAO = new AtividadeDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private MissaoDAO missaoDAO = new MissaoDAO();
    private UsuarioMissaoDAO usuarioMissaoDAO = new UsuarioMissaoDAO();
    private ConquistaDAO conquistaDAO = new ConquistaDAO();
    private UsuarioConquistaDAO usuarioConquistaDAO = new UsuarioConquistaDAO();
    private StreakDAO streakDAO = new StreakDAO();
    private AvatarDAO avatarDAO = new AvatarDAO();
    private static final ZoneId ZONA_NEGOCIO = ZoneId.of("America/Sao_Paulo");

    public ArrayList<AtividadeTO> listarTodas() {
        return atividadeDAO.listarTodas();
    }

    public AtividadeTO buscarPorId(Long idRegistro) {
        return atividadeDAO.buscarPorId(idRegistro);
    }

    public ArrayList<AtividadeTO> listarPorUsuario(Long idUsuario) {
        return atividadeDAO.listarPorUsuario(idUsuario);
    }

    public ArrayList<AtividadeTO> listarPorMissao(Long idMissao) {
        return atividadeDAO.listarPorMissao(idMissao);
    }

    public AtividadeTO inserir(AtividadeTO atividade) {
        if (atividade == null || atividade.getIdUsuario() == null || atividade.getIdMissao() == null) {
            return null;
        }

        LocalDate hoje = LocalDate.now(ZONA_NEGOCIO);
        atividade.setDataRegistro(hoje);

        Connection conexao = ConnectionFactory.getNewConnection();
        if (conexao == null) {
            return null;
        }

        try (conexao) {
            conexao.setAutoCommit(false);
            try {
                if (usuarioDAO.buscarPorId(conexao, atividade.getIdUsuario()) == null) {
                    conexao.rollback();
                    return null;
                }

                MissaoTO missao = missaoDAO.buscarPorId(conexao, atividade.getIdMissao());
                UsuarioMissaoTO participacao = usuarioMissaoDAO.buscarPorIds(
                        conexao, atividade.getIdUsuario(), atividade.getIdMissao());
                if (missao == null || participacao == null
                        || !"EM_ANDAMENTO".equalsIgnoreCase(participacao.getStatus())
                        || participacao.getDataInicio().isAfter(hoje)
                        || atividadeDAO.existeNaData(conexao, atividade.getIdUsuario(),
                                atividade.getIdMissao(), hoje)) {
                    conexao.rollback();
                    return null;
                }

                if (atividadeDAO.inserir(conexao, atividade) == null) {
                    conexao.rollback();
                    return null;
                }

                StreakTO streak = streakDAO.buscarPorUsuario(conexao, atividade.getIdUsuario());
                if (streak == null) {
                    conexao.rollback();
                    return null;
                }
                atualizarStreak(streak, hoje);
                if (!streakDAO.atualizar(conexao, streak)) {
                    conexao.rollback();
                    return null;
                }
                if (streak.getDiasConsecutivos() >= 3
                        && !desbloquearConquista(conexao, atividade.getIdUsuario(), ConquistaBO.CONSISTENCIA, hoje)) {
                    conexao.rollback();
                    return null;
                }
                if (streak.getDiasConsecutivos() >= 7
                        && !desbloquearConquista(
                                conexao, atividade.getIdUsuario(), ConquistaBO.SEMANA_SUSTENTAVEL, hoje)) {
                    conexao.rollback();
                    return null;
                }

                int diasValidos = atividadeDAO.contarDiasDistintos(conexao, atividade.getIdUsuario(),
                        atividade.getIdMissao(), participacao.getDataInicio(), hoje);
                if (diasValidos < 0) {
                    conexao.rollback();
                    return null;
                }
                boolean concluiuMissao = diasValidos >= missao.getMetaDias();
                if (concluiuMissao) {
                    participacao.setStatus("CONCLUIDA");
                    participacao.setDataConclusao(hoje);
                    if (!usuarioMissaoDAO.atualizar(conexao, participacao)) {
                        conexao.rollback();
                        return null;
                    }
                    if (!desbloquearConquista(
                            conexao, atividade.getIdUsuario(), ConquistaBO.PRIMEIROS_PASSOS, hoje)) {
                        conexao.rollback();
                        return null;
                    }

                    if (missao.getIdUsuarioCriador() == null) {
                        if (missao.getPontosRecompensa() == null || missao.getPontosRecompensa() < 0) {
                            conexao.rollback();
                            return null;
                        }
                        if (!usuarioDAO.adicionarPontos(conexao, atividade.getIdUsuario(),
                                missao.getPontosRecompensa())) {
                            conexao.rollback();
                            return null;
                        }
                        AvatarTO avatar = avatarDAO.buscarPorUsuario(conexao, atividade.getIdUsuario());
                        if (avatar == null) {
                            conexao.rollback();
                            return null;
                        }
                        int experiencia = avatar.getExperiencia() + missao.getPontosRecompensa();
                        avatar.setExperiencia(experiencia);
                        avatar.setNivelEvolucao(calcularNivel(experiencia));
                        if (!avatarDAO.atualizar(conexao, avatar)) {
                            conexao.rollback();
                            return null;
                        }
                        if (avatar.getNivelEvolucao() >= 3
                                && !desbloquearConquista(
                                        conexao, atividade.getIdUsuario(), ConquistaBO.SOULIE_EVOLUIDA, hoje)) {
                            conexao.rollback();
                            return null;
                        }

                        int missoesOficiais = usuarioMissaoDAO.contarMissoesOficiaisConcluidas(
                                conexao, atividade.getIdUsuario());
                        if (missoesOficiais < 0) {
                            conexao.rollback();
                            return null;
                        }
                        if (missoesOficiais >= 5 && !desbloquearConquista(conexao,
                                atividade.getIdUsuario(), ConquistaBO.COMPROMISSO_VERDE, hoje)) {
                            conexao.rollback();
                            return null;
                        }
                    }
                }

                conexao.commit();
                return atividade;
            } catch (SQLException | RuntimeException e) {
                try {
                    conexao.rollback();
                } catch (SQLException erroRollback) {
                    e.addSuppressed(erroRollback);
                }
                System.out.println("Erro ao registrar atividade e atualizar jornada: " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na transação da atividade: " + e.getMessage());
            return null;
        }
    }

    void atualizarStreak(StreakTO streak, LocalDate hoje) {
        LocalDate ultimaAtividade = streak.getDataUltimaAtividade();
        int dias = streak.getDiasConsecutivos() == null ? 0 : streak.getDiasConsecutivos();
        if (dias == 0 || ultimaAtividade.isBefore(hoje.minusDays(1))) {
            streak.setDiasConsecutivos(1);
        } else if (ultimaAtividade.isEqual(hoje.minusDays(1))) {
            streak.setDiasConsecutivos(dias + 1);
        } else if (ultimaAtividade.isEqual(hoje)) {
            streak.setDiasConsecutivos(Math.max(dias, 1));
        } else {
            streak.setDiasConsecutivos(1);
        }
        streak.setDataUltimaAtividade(hoje);
    }

    int calcularNivel(int experiencia) {
        if (experiencia >= 700) return 5;
        if (experiencia >= 450) return 4;
        if (experiencia >= 250) return 3;
        if (experiencia >= 100) return 2;
        return 1;
    }

    private boolean desbloquearConquista(Connection conexao, Long idUsuario, String nome, LocalDate hoje) {
        ConquistaTO conquista = conquistaDAO.buscarPorNome(conexao, nome);
        if (conquista == null) {
            System.out.println("Conquista não cadastrada no catálogo: " + nome);
            return false;
        }
        if (usuarioConquistaDAO.buscarPorIds(conexao, idUsuario, conquista.getIdConquista()) != null) {
            return true;
        }

        UsuarioConquistaTO usuarioConquista = new UsuarioConquistaTO(
                idUsuario, conquista.getIdConquista(), hoje);
        return usuarioConquistaDAO.inserir(conexao, usuarioConquista) != null;
    }

    public AtividadeTO atualizar(AtividadeTO atividade) {
        if (atividade == null || atividade.getIdRegistro() == null) {
            return null;
        }

        Connection conexao = ConnectionFactory.getNewConnection();
        if (conexao == null) {
            return null;
        }

        try (conexao) {
            conexao.setAutoCommit(false);
            try {
                AtividadeTO atividadeAtual = atividadeDAO.buscarPorId(conexao, atividade.getIdRegistro());
                if (atividadeAtual == null
                        || usuarioDAO.buscarPorId(conexao, atividadeAtual.getIdUsuario()) == null) {
                    conexao.rollback();
                    return null;
                }
                UsuarioMissaoTO participacao = usuarioMissaoDAO.buscarPorIds(
                        conexao, atividadeAtual.getIdUsuario(), atividadeAtual.getIdMissao());
                if (participacao == null || !"EM_ANDAMENTO".equalsIgnoreCase(participacao.getStatus())) {
                    conexao.rollback();
                    return null;
                }

                atividade.setIdUsuario(atividadeAtual.getIdUsuario());
                atividade.setIdMissao(atividadeAtual.getIdMissao());
                atividade.setDataRegistro(atividadeAtual.getDataRegistro());
                if (!atividadeDAO.atualizar(conexao, atividade)) {
                    conexao.rollback();
                    return null;
                }
                conexao.commit();
                return atividade;
            } catch (SQLException | RuntimeException e) {
                try {
                    conexao.rollback();
                } catch (SQLException erroRollback) {
                    e.addSuppressed(erroRollback);
                }
                System.out.println("Erro ao editar atividade: " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na transação de edição da atividade: " + e.getMessage());
            return null;
        }
    }

    public boolean excluir(Long idRegistro) {
        if (idRegistro == null) {
            return false;
        }

        Connection conexao = ConnectionFactory.getNewConnection();
        if (conexao == null) {
            return false;
        }

        try (conexao) {
            conexao.setAutoCommit(false);
            try {
                AtividadeTO atividade = atividadeDAO.buscarPorId(conexao, idRegistro);
                if (atividade == null
                        || usuarioDAO.buscarPorId(conexao, atividade.getIdUsuario()) == null) {
                    conexao.rollback();
                    return false;
                }

                UsuarioMissaoTO participacao = usuarioMissaoDAO.buscarPorIds(
                        conexao, atividade.getIdUsuario(), atividade.getIdMissao());
                if (participacao == null || !"EM_ANDAMENTO".equalsIgnoreCase(participacao.getStatus())) {
                    conexao.rollback();
                    return false;
                }

                StreakTO streak = streakDAO.buscarPorUsuario(conexao, atividade.getIdUsuario());
                if (streak == null || !atividadeDAO.excluir(conexao, idRegistro)) {
                    conexao.rollback();
                    return false;
                }

                ArrayList<LocalDate> datas = atividadeDAO.listarDatasPorUsuario(conexao,
                        atividade.getIdUsuario());
                if (datas == null) {
                    conexao.rollback();
                    return false;
                }
                recalcularStreak(streak, datas, LocalDate.now(ZONA_NEGOCIO));
                if (!streakDAO.atualizar(conexao, streak)) {
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
                System.out.println("Erro ao excluir atividade e recalcular streak: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro na transação de exclusão da atividade: " + e.getMessage());
            return false;
        }
    }

    private void recalcularStreak(StreakTO streak, ArrayList<LocalDate> datas, LocalDate hoje) {
        LocalDate ultimaData = null;
        for (LocalDate data : datas) {
            if (!data.isAfter(hoje)) {
                ultimaData = data;
            }
        }

        if (ultimaData == null || ultimaData.isBefore(hoje.minusDays(1))) {
            streak.setDiasConsecutivos(0);
            streak.setDataUltimaAtividade(ultimaData == null ? hoje : ultimaData);
            return;
        }

        int dias = 0;
        LocalDate dataEsperada = ultimaData;
        for (int i = datas.size() - 1; i >= 0; i--) {
            LocalDate data = datas.get(i);
            if (data.isAfter(hoje)) {
                continue;
            }
            if (!data.equals(dataEsperada)) {
                break;
            }
            dias++;
            dataEsperada = dataEsperada.minusDays(1);
        }

        streak.setDiasConsecutivos(dias);
        streak.setDataUltimaAtividade(ultimaData);
    }

}
