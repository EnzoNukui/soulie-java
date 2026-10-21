package br.com.fiap.dao;

import br.com.fiap.to.AtividadeTO;

import java.sql.*;
import java.util.ArrayList;

public class AtividadeDAO {

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

    public AtividadeTO inserir(AtividadeTO atividade) {
        String sql = "insert into ATIVIDADE (ID_USUARIO, ID_MISSAO, DT_REGISTRO, DS_OBSERVACAO) values (?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, atividade.getIdUsuario());
            ps.setLong(2, atividade.getIdMissao());
            ps.setDate(3, Date.valueOf(atividade.getDataRegistro()));
            ps.setString(4, atividade.getObservacao());
            if (ps.executeUpdate() > 0) {
                return atividade;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public AtividadeTO atualizar(AtividadeTO atividade) {
        String sql = "update ATIVIDADE set ID_USUARIO = ?, ID_MISSAO = ?, DT_REGISTRO = ?, DS_OBSERVACAO = ? where ID_REGISTRO = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, atividade.getIdUsuario());
            ps.setLong(2, atividade.getIdMissao());
            ps.setDate(3, Date.valueOf(atividade.getDataRegistro()));
            ps.setString(4, atividade.getObservacao());
            ps.setLong(5, atividade.getIdRegistro());
            if (ps.executeUpdate() > 0) {
                return atividade;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean excluir(Long idRegistro) {
        String sql = "delete from ATIVIDADE where ID_REGISTRO = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idRegistro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }
}
