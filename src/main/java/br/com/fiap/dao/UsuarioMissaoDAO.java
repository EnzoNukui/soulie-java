package br.com.fiap.dao;

import br.com.fiap.to.UsuarioMissaoTO;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioMissaoDAO {

    public int contarMissoesOficiaisConcluidas(Connection conexao, Long idUsuario) {
        String sql = "select count(*) from USUARIO_MISSAO um join MISSAO m "
                + "on um.ID_MISSAO = m.ID_MISSAO "
                + "where um.ID_USUARIO = ? and um.ST_MISSAO = 'CONCLUIDA' "
                + "and m.ID_USUARIO_CRIADOR is null";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao contar missões oficiais concluídas: " + e.getMessage());
            return -1;
        }
    }

    public UsuarioMissaoTO buscarPorIds(Connection conexao, Long idUsuario, Long idMissao) {
        String sql = "select * from USUARIO_MISSAO where ID_USUARIO = ? and ID_MISSAO = ? for update";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idMissao);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UsuarioMissaoTO participacao = new UsuarioMissaoTO();
                    participacao.setIdUsuario(rs.getLong("ID_USUARIO"));
                    participacao.setIdMissao(rs.getLong("ID_MISSAO"));
                    participacao.setDataInicio(rs.getDate("DT_INICIO").toLocalDate());
                    Date conclusao = rs.getDate("DT_CONCLUSAO");
                    participacao.setDataConclusao(conclusao == null ? null : conclusao.toLocalDate());
                    participacao.setStatus(rs.getString("ST_MISSAO"));
                    return participacao;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Connection conexao, UsuarioMissaoTO participacao) {
        String sql = "update USUARIO_MISSAO set DT_INICIO = ?, DT_CONCLUSAO = ?, ST_MISSAO = ? "
                + "where ID_USUARIO = ? and ID_MISSAO = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(participacao.getDataInicio()));
            ps.setDate(2, participacao.getDataConclusao() == null
                    ? null : Date.valueOf(participacao.getDataConclusao()));
            ps.setString(3, participacao.getStatus());
            ps.setLong(4, participacao.getIdUsuario());
            ps.setLong(5, participacao.getIdMissao());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<UsuarioMissaoTO> listarPorUsuario(Long idUsuario) {
        ArrayList<UsuarioMissaoTO> missoes = new ArrayList<>();
        String sql = "select * from USUARIO_MISSAO where ID_USUARIO = ? order by ID_MISSAO";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioMissaoTO usuarioMissao = new UsuarioMissaoTO();
                usuarioMissao.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuarioMissao.setIdMissao(rs.getLong("ID_MISSAO"));
                usuarioMissao.setDataInicio(rs.getDate("DT_INICIO").toLocalDate());
                Date dataConclusao = rs.getDate("DT_CONCLUSAO");
                usuarioMissao.setDataConclusao(dataConclusao == null ? null : dataConclusao.toLocalDate());
                usuarioMissao.setStatus(rs.getString("ST_MISSAO"));
                missoes.add(usuarioMissao);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return missoes;
    }

    public ArrayList<UsuarioMissaoTO> listarPorMissao(Long idMissao) {
        ArrayList<UsuarioMissaoTO> usuarios = new ArrayList<>();
        String sql = "select * from USUARIO_MISSAO where ID_MISSAO = ? order by ID_USUARIO";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idMissao);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioMissaoTO usuarioMissao = new UsuarioMissaoTO();
                usuarioMissao.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuarioMissao.setIdMissao(rs.getLong("ID_MISSAO"));
                usuarioMissao.setDataInicio(rs.getDate("DT_INICIO").toLocalDate());
                Date dataConclusao = rs.getDate("DT_CONCLUSAO");
                usuarioMissao.setDataConclusao(dataConclusao == null ? null : dataConclusao.toLocalDate());
                usuarioMissao.setStatus(rs.getString("ST_MISSAO"));
                usuarios.add(usuarioMissao);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return usuarios;
    }

    public UsuarioMissaoTO buscarPorIds(Long idUsuario, Long idMissao) {
        String sql = "select * from USUARIO_MISSAO where ID_USUARIO = ? and ID_MISSAO = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idMissao);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UsuarioMissaoTO usuarioMissao = new UsuarioMissaoTO();
                usuarioMissao.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuarioMissao.setIdMissao(rs.getLong("ID_MISSAO"));
                usuarioMissao.setDataInicio(rs.getDate("DT_INICIO").toLocalDate());
                Date dataConclusao = rs.getDate("DT_CONCLUSAO");
                usuarioMissao.setDataConclusao(dataConclusao == null ? null : dataConclusao.toLocalDate());
                usuarioMissao.setStatus(rs.getString("ST_MISSAO"));
                return usuarioMissao;
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

    public UsuarioMissaoTO inserir(UsuarioMissaoTO usuarioMissao) {
        String sql = "insert into USUARIO_MISSAO (ID_USUARIO, ID_MISSAO, DT_INICIO, DT_CONCLUSAO, ST_MISSAO) values (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, usuarioMissao.getIdUsuario());
            ps.setLong(2, usuarioMissao.getIdMissao());
            ps.setDate(3, Date.valueOf(usuarioMissao.getDataInicio()));
            ps.setDate(4, usuarioMissao.getDataConclusao() == null
                    ? null : Date.valueOf(usuarioMissao.getDataConclusao()));
            ps.setString(5, usuarioMissao.getStatus());
            if (ps.executeUpdate() > 0) {
                return usuarioMissao;
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

    public boolean excluir(Long idUsuario, Long idMissao) {
        String sql = "delete from USUARIO_MISSAO where ID_USUARIO = ? and ID_MISSAO = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idMissao);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }
}
