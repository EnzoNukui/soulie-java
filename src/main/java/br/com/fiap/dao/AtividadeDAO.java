package br.com.fiap.dao;

import br.com.fiap.to.AtividadeTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AtividadeDAO {

    public AtividadeTO buscarPorId(Connection conexao, Long idRegistro) {
        String sql = "select * from ATIVIDADE where ID_REGISTRO = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, idRegistro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AtividadeTO atividade = new AtividadeTO();
                    atividade.setIdRegistro(rs.getLong("ID_REGISTRO"));
                    atividade.setIdUsuario(rs.getLong("ID_USUARIO"));
                    atividade.setIdMissao(rs.getLong("ID_MISSAO"));
                    atividade.setDataRegistro(rs.getDate("DT_REGISTRO").toLocalDate());
                    atividade.setObservacao(rs.getString("DS_OBSERVACAO"));
                    return atividade;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }
        return null;
    }

    public boolean excluir(Connection conexao, Long idRegistro) {
        String sql = "delete from ATIVIDADE where ID_REGISTRO = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, idRegistro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Connection conexao, AtividadeTO atividade) {
        String sql = "update ATIVIDADE set DS_OBSERVACAO = ? where ID_REGISTRO = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, atividade.getObservacao());
            ps.setLong(2, atividade.getIdRegistro());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar atividade: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<LocalDate> listarDatasPorUsuario(Connection conexao, Long idUsuario) {
        ArrayList<LocalDate> datas = new ArrayList<>();
        String sql = "select distinct TRUNC(DT_REGISTRO) as DT_REGISTRO from ATIVIDADE "
                + "where ID_USUARIO = ? order by TRUNC(DT_REGISTRO)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    datas.add(rs.getDate("DT_REGISTRO").toLocalDate());
                }
                return datas;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar datas de atividade: " + e.getMessage());
            return null;
        }
    }

    public boolean existeNaData(Connection conexao, Long idUsuario, Long idMissao, LocalDate data) {
        String sql = "select 1 from ATIVIDADE where ID_USUARIO = ? and ID_MISSAO = ? and TRUNC(DT_REGISTRO) = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idMissao);
            ps.setDate(3, Date.valueOf(data));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar atividade duplicada: " + e.getMessage());
            return true;
        }
    }

    public boolean existePorUsuarioEMissao(Long idUsuario, Long idMissao) {
        String sql = "select 1 from ATIVIDADE where ID_USUARIO = ? and ID_MISSAO = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idMissao);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar atividades da participação: " + e.getMessage());
            // Em caso de falha na consulta, impede a exclusão para não deixar atividades órfãs.
            return true;
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public int contarDiasDistintos(Connection conexao, Long idUsuario, Long idMissao,
                                   LocalDate dataInicio, LocalDate dataFim) {
        String sql = "select count(distinct TRUNC(DT_REGISTRO)) from ATIVIDADE "
                + "where ID_USUARIO = ? and ID_MISSAO = ? and TRUNC(DT_REGISTRO) between ? and ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idMissao);
            ps.setDate(3, Date.valueOf(dataInicio));
            ps.setDate(4, Date.valueOf(dataFim));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao contar dias de atividade: " + e.getMessage());
            return -1;
        }
    }

    public AtividadeTO inserir(Connection conexao, AtividadeTO atividade) {
        String sql = "insert into ATIVIDADE (ID_USUARIO, ID_MISSAO, DT_REGISTRO, DS_OBSERVACAO) values (?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, atividade.getIdUsuario());
            ps.setLong(2, atividade.getIdMissao());
            ps.setDate(3, Date.valueOf(atividade.getDataRegistro()));
            ps.setString(4, atividade.getObservacao());
            if (ps.executeUpdate() == 0) {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
            return null;
        }
        return atividade;
    }

    public ArrayList<AtividadeTO> listarTodas() {
        ArrayList<AtividadeTO> atividades = new ArrayList<>();
        String sql = "select * from ATIVIDADE order by ID_REGISTRO";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AtividadeTO atividade = new AtividadeTO();
                atividade.setIdRegistro(rs.getLong("ID_REGISTRO"));
                atividade.setIdUsuario(rs.getLong("ID_USUARIO"));
                atividade.setIdMissao(rs.getLong("ID_MISSAO"));
                atividade.setDataRegistro(rs.getDate("DT_REGISTRO").toLocalDate());
                atividade.setObservacao(rs.getString("DS_OBSERVACAO"));
                atividades.add(atividade);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return atividades;
    }

    public AtividadeTO buscarPorId(Long idRegistro) {
        String sql = "select * from ATIVIDADE where ID_REGISTRO = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idRegistro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AtividadeTO atividade = new AtividadeTO();
                atividade.setIdRegistro(rs.getLong("ID_REGISTRO"));
                atividade.setIdUsuario(rs.getLong("ID_USUARIO"));
                atividade.setIdMissao(rs.getLong("ID_MISSAO"));
                atividade.setDataRegistro(rs.getDate("DT_REGISTRO").toLocalDate());
                atividade.setObservacao(rs.getString("DS_OBSERVACAO"));
                return atividade;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public ArrayList<AtividadeTO> listarPorUsuario(Long idUsuario) {
        ArrayList<AtividadeTO> atividades = new ArrayList<>();
        String sql = "select * from ATIVIDADE where ID_USUARIO = ? order by DT_REGISTRO desc";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AtividadeTO atividade = new AtividadeTO();
                atividade.setIdRegistro(rs.getLong("ID_REGISTRO"));
                atividade.setIdUsuario(rs.getLong("ID_USUARIO"));
                atividade.setIdMissao(rs.getLong("ID_MISSAO"));
                atividade.setDataRegistro(rs.getDate("DT_REGISTRO").toLocalDate());
                atividade.setObservacao(rs.getString("DS_OBSERVACAO"));
                atividades.add(atividade);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return atividades;
    }

    public ArrayList<AtividadeTO> listarPorMissao(Long idMissao) {
        ArrayList<AtividadeTO> atividades = new ArrayList<>();
        String sql = "select * from ATIVIDADE where ID_MISSAO = ? order by DT_REGISTRO desc";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idMissao);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AtividadeTO atividade = new AtividadeTO();
                atividade.setIdRegistro(rs.getLong("ID_REGISTRO"));
                atividade.setIdUsuario(rs.getLong("ID_USUARIO"));
                atividade.setIdMissao(rs.getLong("ID_MISSAO"));
                atividade.setDataRegistro(rs.getDate("DT_REGISTRO").toLocalDate());
                atividade.setObservacao(rs.getString("DS_OBSERVACAO"));
                atividades.add(atividade);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return atividades;
    }

}
