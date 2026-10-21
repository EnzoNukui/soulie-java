package br.com.fiap.dao;

import br.com.fiap.to.MissaoTO;

import java.sql.*;
import java.util.ArrayList;

public class MissaoDAO {

    public ArrayList<MissaoTO> listarTodas() {
        ArrayList<MissaoTO> missoes = new ArrayList<>();
        String sql = "select * from MISSAO order by ID_MISSAO";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MissaoTO missao = new MissaoTO();
                missao.setIdMissao(rs.getLong("ID_MISSAO"));
                missao.setNome(rs.getString("NM_MISSAO"));
                missao.setDescricao(rs.getString("DS_MISSAO"));
                missao.setPontosRecompensa(rs.getInt("QT_PONTOS_RECOMPENSA"));
                missao.setIdUsuarioCriador(rs.getObject("ID_USUARIO_CRIADOR", Long.class));
                missao.setMetaDias(rs.getInt("QT_META_DIAS"));
                missoes.add(missao);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return missoes;
    }

    public MissaoTO buscarPorId(Long idMissao) {
        String sql = "select * from MISSAO where ID_MISSAO = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idMissao);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                MissaoTO missao = new MissaoTO();
                missao.setIdMissao(rs.getLong("ID_MISSAO"));
                missao.setNome(rs.getString("NM_MISSAO"));
                missao.setDescricao(rs.getString("DS_MISSAO"));
                missao.setPontosRecompensa(rs.getInt("QT_PONTOS_RECOMPENSA"));
                missao.setIdUsuarioCriador(rs.getObject("ID_USUARIO_CRIADOR", Long.class));
                missao.setMetaDias(rs.getInt("QT_META_DIAS"));
                return missao;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public MissaoTO inserir(MissaoTO missao) {
        String sql = "insert into MISSAO (NM_MISSAO, DS_MISSAO, QT_PONTOS_RECOMPENSA, ID_USUARIO_CRIADOR, QT_META_DIAS) values (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, missao.getNome());
            ps.setString(2, missao.getDescricao());
            ps.setInt(3, missao.getPontosRecompensa());
            if (missao.getIdUsuarioCriador() == null) {
                ps.setNull(4, Types.NUMERIC);
            } else {
                ps.setLong(4, missao.getIdUsuarioCriador());
            }
            ps.setInt(5, missao.getMetaDias());
            if (ps.executeUpdate() > 0) {
                return missao;
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

    public MissaoTO atualizar(MissaoTO missao) {
        String sql = "update MISSAO set NM_MISSAO = ?, DS_MISSAO = ?, QT_PONTOS_RECOMPENSA = ?, ID_USUARIO_CRIADOR = ?, QT_META_DIAS = ? where ID_MISSAO = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, missao.getNome());
            ps.setString(2, missao.getDescricao());
            ps.setInt(3, missao.getPontosRecompensa());
            if (missao.getIdUsuarioCriador() == null) {
                ps.setNull(4, Types.NUMERIC);
            } else {
                ps.setLong(4, missao.getIdUsuarioCriador());
            }
            ps.setInt(5, missao.getMetaDias());
            ps.setLong(6, missao.getIdMissao());
            if (ps.executeUpdate() > 0) {
                return missao;
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

    public boolean excluir(Long idMissao) {
        String sql = "delete from MISSAO where ID_MISSAO = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idMissao);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }
}
