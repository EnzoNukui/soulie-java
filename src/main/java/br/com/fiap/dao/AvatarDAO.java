package br.com.fiap.dao;

import br.com.fiap.to.AvatarTO;

import java.sql.*;
import java.util.ArrayList;

public class AvatarDAO {

    public ArrayList<AvatarTO> listarTodos() {
        ArrayList<AvatarTO> avatares = new ArrayList<>();
        String sql = "select * from AVATAR order by ID_AVATAR";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AvatarTO avatar = new AvatarTO();
                avatar.setIdAvatar(rs.getLong("ID_AVATAR"));
                avatar.setIdUsuario(rs.getLong("ID_USUARIO"));
                avatar.setNivelEvolucao(rs.getInt("NR_NIVEL_EVOLUCAO"));
                avatar.setExperiencia(rs.getInt("QT_EXPERIENCIA"));
                avatar.setTemperamento(rs.getString("DS_TEMPERAMENTO"));
                avatares.add(avatar);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return avatares;
    }

    public AvatarTO buscarPorId(Long idAvatar) {
        String sql = "select * from AVATAR where ID_AVATAR = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idAvatar);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AvatarTO avatar = new AvatarTO();
                avatar.setIdAvatar(rs.getLong("ID_AVATAR"));
                avatar.setIdUsuario(rs.getLong("ID_USUARIO"));
                avatar.setNivelEvolucao(rs.getInt("NR_NIVEL_EVOLUCAO"));
                avatar.setExperiencia(rs.getInt("QT_EXPERIENCIA"));
                avatar.setTemperamento(rs.getString("DS_TEMPERAMENTO"));
                return avatar;
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

    public AvatarTO buscarPorUsuario(Long idUsuario) {
        String sql = "select * from AVATAR where ID_USUARIO = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AvatarTO avatar = new AvatarTO();
                avatar.setIdAvatar(rs.getLong("ID_AVATAR"));
                avatar.setIdUsuario(rs.getLong("ID_USUARIO"));
                avatar.setNivelEvolucao(rs.getInt("NR_NIVEL_EVOLUCAO"));
                avatar.setExperiencia(rs.getInt("QT_EXPERIENCIA"));
                avatar.setTemperamento(rs.getString("DS_TEMPERAMENTO"));
                return avatar;
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

    public AvatarTO inserir(AvatarTO avatar) {
        String sql = "insert into AVATAR (ID_USUARIO, NR_NIVEL_EVOLUCAO, QT_EXPERIENCIA, DS_TEMPERAMENTO) values (?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, avatar.getIdUsuario());
            int nivel = avatar.getNivelEvolucao() == null ? 1 : avatar.getNivelEvolucao();
            int experiencia = avatar.getExperiencia() == null ? 0 : avatar.getExperiencia();
            avatar.setNivelEvolucao(nivel);
            avatar.setExperiencia(experiencia);
            ps.setInt(2, nivel);
            ps.setInt(3, experiencia);
            ps.setString(4, avatar.getTemperamento());
            if (ps.executeUpdate() > 0) {
                return avatar;
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

    public AvatarTO atualizar(AvatarTO avatar) {
        String sql = "update AVATAR set ID_USUARIO = ?, NR_NIVEL_EVOLUCAO = ?, QT_EXPERIENCIA = ?, DS_TEMPERAMENTO = ? where ID_AVATAR = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, avatar.getIdUsuario());
            int nivel = avatar.getNivelEvolucao() == null ? 1 : avatar.getNivelEvolucao();
            int experiencia = avatar.getExperiencia() == null ? 0 : avatar.getExperiencia();
            avatar.setNivelEvolucao(nivel);
            avatar.setExperiencia(experiencia);
            ps.setInt(2, nivel);
            ps.setInt(3, experiencia);
            ps.setString(4, avatar.getTemperamento());
            ps.setLong(5, avatar.getIdAvatar());
            if (ps.executeUpdate() > 0) {
                return avatar;
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

    public boolean excluir(Long idAvatar) {
        String sql = "delete from AVATAR where ID_AVATAR = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idAvatar);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }
}
