package br.com.fiap.dao;

import br.com.fiap.to.UsuarioTO;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {

    public UsuarioTO buscarPorIdentificadorJornada(String identificadorJornada) {
        String sql = "select * from USUARIO where DS_IDENTIFICADOR_JORNADA = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, identificadorJornada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UsuarioTO usuario = new UsuarioTO();
                usuario.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuario.setNomeUsuario(rs.getString("NM_USUARIO"));
                usuario.setEmail(rs.getString("DS_EMAIL"));
                usuario.setPontosAcumulados(rs.getInt("QT_PONTOS_ACUMULADOS"));
                usuario.setStatus(rs.getString("ST_USUARIO"));
                usuario.setIdentificadorJornada(rs.getString("DS_IDENTIFICADOR_JORNADA"));
                return usuario;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public UsuarioTO buscarPorId(Connection conexao, Long idUsuario) {
        // As operações de atividade usam esta consulta dentro da transação para
        // serializar alterações do mesmo usuário (streak, pontos e experiência).
        String sql = "select * from USUARIO where ID_USUARIO = ? for update";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UsuarioTO usuario = new UsuarioTO();
                    usuario.setIdUsuario(rs.getLong("ID_USUARIO"));
                    usuario.setNomeUsuario(rs.getString("NM_USUARIO"));
                    usuario.setEmail(rs.getString("DS_EMAIL"));
                    usuario.setPontosAcumulados(rs.getInt("QT_PONTOS_ACUMULADOS"));
                    usuario.setStatus(rs.getString("ST_USUARIO"));
                    usuario.setIdentificadorJornada(rs.getString("DS_IDENTIFICADOR_JORNADA"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }
        return null;
    }

    public boolean adicionarPontos(Connection conexao, Long idUsuario, int pontos) {
        String sql = "update USUARIO set QT_PONTOS_ACUMULADOS = QT_PONTOS_ACUMULADOS + ? where ID_USUARIO = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, pontos);
            ps.setLong(2, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pontuação: " + e.getMessage());
            return false;
        }
    }

    public UsuarioTO buscarPorEmail(Connection conexao, String email) {
        String sql = "select * from USUARIO where DS_EMAIL = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UsuarioTO usuario = new UsuarioTO();
                    usuario.setIdUsuario(rs.getLong("ID_USUARIO"));
                    usuario.setNomeUsuario(rs.getString("NM_USUARIO"));
                    usuario.setEmail(rs.getString("DS_EMAIL"));
                    usuario.setPontosAcumulados(rs.getInt("QT_PONTOS_ACUMULADOS"));
                    usuario.setStatus(rs.getString("ST_USUARIO"));
                    usuario.setIdentificadorJornada(rs.getString("DS_IDENTIFICADOR_JORNADA"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }
        return null;
    }

    public UsuarioTO inserir(Connection conexao, UsuarioTO usuario) {
        String sql = "insert into USUARIO (NM_USUARIO, DS_EMAIL, QT_PONTOS_ACUMULADOS, ST_USUARIO, DS_IDENTIFICADOR_JORNADA) values (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, usuario.getNomeUsuario());
            ps.setString(2, usuario.getEmail());
            ps.setInt(3, usuario.getPontosAcumulados());
            ps.setString(4, usuario.getStatus());
            ps.setString(5, usuario.getIdentificadorJornada());
            if (ps.executeUpdate() == 0) {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir: " + e.getMessage());
            return null;
        }
        return buscarPorEmail(conexao, usuario.getEmail());
    }

    public ArrayList<UsuarioTO> listarTodos() {
        ArrayList<UsuarioTO> usuarios = new ArrayList<>();
        String sql = "select * from USUARIO order by ID_USUARIO";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioTO usuario = new UsuarioTO();
                usuario.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuario.setNomeUsuario(rs.getString("NM_USUARIO"));
                usuario.setEmail(rs.getString("DS_EMAIL"));
                usuario.setPontosAcumulados(rs.getInt("QT_PONTOS_ACUMULADOS"));
                usuario.setStatus(rs.getString("ST_USUARIO"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return usuarios;
    }

    public UsuarioTO buscarPorId(Long idUsuario) {
        String sql = "select * from USUARIO where ID_USUARIO = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UsuarioTO usuario = new UsuarioTO();
                usuario.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuario.setNomeUsuario(rs.getString("NM_USUARIO"));
                usuario.setEmail(rs.getString("DS_EMAIL"));
                usuario.setPontosAcumulados(rs.getInt("QT_PONTOS_ACUMULADOS"));
                usuario.setStatus(rs.getString("ST_USUARIO"));
                usuario.setIdentificadorJornada(rs.getString("DS_IDENTIFICADOR_JORNADA"));
                return usuario;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public UsuarioTO buscarPorEmail(String email) {
        String sql = "select * from USUARIO where DS_EMAIL = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UsuarioTO usuario = new UsuarioTO();
                usuario.setIdUsuario(rs.getLong("ID_USUARIO"));
                usuario.setNomeUsuario(rs.getString("NM_USUARIO"));
                usuario.setEmail(rs.getString("DS_EMAIL"));
                usuario.setPontosAcumulados(rs.getInt("QT_PONTOS_ACUMULADOS"));
                usuario.setStatus(rs.getString("ST_USUARIO"));
                usuario.setIdentificadorJornada(rs.getString("DS_IDENTIFICADOR_JORNADA"));
                return usuario;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public UsuarioTO inserir(UsuarioTO usuario) {
        String sql = "insert into USUARIO (NM_USUARIO, DS_EMAIL, QT_PONTOS_ACUMULADOS, ST_USUARIO, DS_IDENTIFICADOR_JORNADA) values (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, usuario.getNomeUsuario());
            ps.setString(2, usuario.getEmail());
            int pontos = usuario.getPontosAcumulados() == null ? 0 : usuario.getPontosAcumulados();
            String status = usuario.getStatus() == null ? "A" : usuario.getStatus();
            usuario.setPontosAcumulados(pontos);
            usuario.setStatus(status);
            ps.setInt(3, pontos);
            ps.setString(4, status);
            ps.setString(5, usuario.getIdentificadorJornada());
            if (ps.executeUpdate() > 0) {
                return usuario;
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

    public UsuarioTO atualizar(UsuarioTO usuario) {
        String sql = "update USUARIO set NM_USUARIO = ?, DS_EMAIL = ?, QT_PONTOS_ACUMULADOS = ?, ST_USUARIO = ?, DS_IDENTIFICADOR_JORNADA = ? where ID_USUARIO = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, usuario.getNomeUsuario());
            ps.setString(2, usuario.getEmail());
            int pontos = usuario.getPontosAcumulados() == null ? 0 : usuario.getPontosAcumulados();
            String status = usuario.getStatus() == null ? "A" : usuario.getStatus();
            usuario.setPontosAcumulados(pontos);
            usuario.setStatus(status);
            ps.setInt(3, pontos);
            ps.setString(4, status);
            ps.setString(5, usuario.getIdentificadorJornada());
            ps.setLong(6, usuario.getIdUsuario());
            if (ps.executeUpdate() > 0) {
                return usuario;
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

    public boolean excluir(Long idUsuario) {
        String sql = "delete from USUARIO where ID_USUARIO = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }

    public boolean excluir(Connection conexao, Long idUsuario) {
        String sql = "delete from USUARIO where ID_USUARIO = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
            return false;
        }
    }
}
