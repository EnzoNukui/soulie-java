package br.com.fiap.dao;

import br.com.fiap.to.UsuarioComunidadeTO;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioComunidadeDAO {

    public ArrayList<UsuarioComunidadeTO> listarPorUsuario(Long idUsuario) {
        ArrayList<UsuarioComunidadeTO> comunidades = new ArrayList<>();
        String sql = "select * from USUARIO_COMUNIDADE where ID_USUARIO = ? order by ID_COMUNIDADE";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioComunidadeTO usuarioComunidade = new UsuarioComunidadeTO();
                usuarioComunidade.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuarioComunidade.setIdComunidade(rs.getLong("ID_COMUNIDADE"));
                usuarioComunidade.setDataIngresso(rs.getDate("DT_INGRESSO_GRUPO").toLocalDate());
                comunidades.add(usuarioComunidade);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return comunidades;
    }

    public ArrayList<UsuarioComunidadeTO> listarPorComunidade(Long idComunidade) {
        ArrayList<UsuarioComunidadeTO> usuarios = new ArrayList<>();
        String sql = "select * from USUARIO_COMUNIDADE where ID_COMUNIDADE = ? order by ID_USUARIO";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idComunidade);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioComunidadeTO usuarioComunidade = new UsuarioComunidadeTO();
                usuarioComunidade.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuarioComunidade.setIdComunidade(rs.getLong("ID_COMUNIDADE"));
                usuarioComunidade.setDataIngresso(rs.getDate("DT_INGRESSO_GRUPO").toLocalDate());
                usuarios.add(usuarioComunidade);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return usuarios;
    }

    public UsuarioComunidadeTO buscarPorIds(Long idUsuario, Long idComunidade) {
        String sql = "select * from USUARIO_COMUNIDADE where ID_USUARIO = ? and ID_COMUNIDADE = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idComunidade);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UsuarioComunidadeTO usuarioComunidade = new UsuarioComunidadeTO();
                usuarioComunidade.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuarioComunidade.setIdComunidade(rs.getLong("ID_COMUNIDADE"));
                usuarioComunidade.setDataIngresso(rs.getDate("DT_INGRESSO_GRUPO").toLocalDate());
                return usuarioComunidade;
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

    public UsuarioComunidadeTO inserir(UsuarioComunidadeTO usuarioComunidade) {
        String sql = "insert into USUARIO_COMUNIDADE (ID_USUARIO, ID_COMUNIDADE, DT_INGRESSO_GRUPO) values (?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, usuarioComunidade.getIdUsuario());
            ps.setLong(2, usuarioComunidade.getIdComunidade());
            ps.setDate(3, Date.valueOf(usuarioComunidade.getDataIngresso()));
            if (ps.executeUpdate() > 0) {
                return usuarioComunidade;
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

    public UsuarioComunidadeTO atualizar(UsuarioComunidadeTO usuarioComunidade) {
        String sql = "update USUARIO_COMUNIDADE set DT_INGRESSO_GRUPO = ? where ID_USUARIO = ? and ID_COMUNIDADE = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(usuarioComunidade.getDataIngresso()));
            ps.setLong(2, usuarioComunidade.getIdUsuario());
            ps.setLong(3, usuarioComunidade.getIdComunidade());
            if (ps.executeUpdate() > 0) {
                return usuarioComunidade;
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

    public boolean excluir(Long idUsuario, Long idComunidade) {
        String sql = "delete from USUARIO_COMUNIDADE where ID_USUARIO = ? and ID_COMUNIDADE = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idComunidade);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }
}
