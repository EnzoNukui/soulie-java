package br.com.fiap.dao;

import br.com.fiap.to.UsuarioConquistaTO;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioConquistaDAO {

    public UsuarioConquistaTO buscarPorIds(Connection conexao, Long idUsuario, Long idConquista) {
        String sql = "select * from USUARIO_CONQUISTA where ID_USUARIO = ? and ID_CONQUISTA = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idConquista);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UsuarioConquistaTO usuarioConquista = new UsuarioConquistaTO();
                    usuarioConquista.setIdUsuario(rs.getLong("ID_USUARIO"));
                    usuarioConquista.setIdConquista(rs.getLong("ID_CONQUISTA"));
                    usuarioConquista.setDataConquista(rs.getDate("DT_CONQUISTA").toLocalDate());
                    return usuarioConquista;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }
        return null;
    }

    public UsuarioConquistaTO inserir(Connection conexao, UsuarioConquistaTO usuarioConquista) {
        String sql = "insert into USUARIO_CONQUISTA (ID_USUARIO, ID_CONQUISTA, DT_CONQUISTA) values (?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, usuarioConquista.getIdUsuario());
            ps.setLong(2, usuarioConquista.getIdConquista());
            ps.setDate(3, Date.valueOf(usuarioConquista.getDataConquista()));
            if (ps.executeUpdate() > 0) {
                return usuarioConquista;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir conquista do usuário: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<UsuarioConquistaTO> listarPorUsuario(Long idUsuario) {
        ArrayList<UsuarioConquistaTO> conquistas = new ArrayList<>();
        String sql = "select * from USUARIO_CONQUISTA where ID_USUARIO = ? order by ID_CONQUISTA";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioConquistaTO usuarioConquista = new UsuarioConquistaTO();
                usuarioConquista.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuarioConquista.setIdConquista(rs.getLong("ID_CONQUISTA"));
                usuarioConquista.setDataConquista(rs.getDate("DT_CONQUISTA").toLocalDate());
                conquistas.add(usuarioConquista);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return conquistas;
    }

    public ArrayList<UsuarioConquistaTO> listarPorConquista(Long idConquista) {
        ArrayList<UsuarioConquistaTO> usuarios = new ArrayList<>();
        String sql = "select * from USUARIO_CONQUISTA where ID_CONQUISTA = ? order by ID_USUARIO";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idConquista);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioConquistaTO usuarioConquista = new UsuarioConquistaTO();
                usuarioConquista.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuarioConquista.setIdConquista(rs.getLong("ID_CONQUISTA"));
                usuarioConquista.setDataConquista(rs.getDate("DT_CONQUISTA").toLocalDate());
                usuarios.add(usuarioConquista);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return usuarios;
    }

    public UsuarioConquistaTO buscarPorIds(Long idUsuario, Long idConquista) {
        String sql = "select * from USUARIO_CONQUISTA where ID_USUARIO = ? and ID_CONQUISTA = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idConquista);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UsuarioConquistaTO usuarioConquista = new UsuarioConquistaTO();
                usuarioConquista.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuarioConquista.setIdConquista(rs.getLong("ID_CONQUISTA"));
                usuarioConquista.setDataConquista(rs.getDate("DT_CONQUISTA").toLocalDate());
                return usuarioConquista;
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

    public UsuarioConquistaTO inserir(UsuarioConquistaTO usuarioConquista) {
        String sql = "insert into USUARIO_CONQUISTA (ID_USUARIO, ID_CONQUISTA, DT_CONQUISTA) values (?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, usuarioConquista.getIdUsuario());
            ps.setLong(2, usuarioConquista.getIdConquista());
            ps.setDate(3, Date.valueOf(usuarioConquista.getDataConquista()));
            if (ps.executeUpdate() > 0) {
                return usuarioConquista;
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

    public UsuarioConquistaTO atualizar(UsuarioConquistaTO usuarioConquista) {
        String sql = "update USUARIO_CONQUISTA set DT_CONQUISTA = ? where ID_USUARIO = ? and ID_CONQUISTA = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(usuarioConquista.getDataConquista()));
            ps.setLong(2, usuarioConquista.getIdUsuario());
            ps.setLong(3, usuarioConquista.getIdConquista());
            if (ps.executeUpdate() > 0) {
                return usuarioConquista;
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

    public boolean excluir(Long idUsuario, Long idConquista) {
        String sql = "delete from USUARIO_CONQUISTA where ID_USUARIO = ? and ID_CONQUISTA = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idConquista);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }
}
