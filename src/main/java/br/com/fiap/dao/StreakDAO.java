package br.com.fiap.dao;

import br.com.fiap.to.StreakTO;

import java.sql.*;
import java.util.ArrayList;

public class StreakDAO {

    public ArrayList<StreakTO> listarTodos() {
        ArrayList<StreakTO> streaks = new ArrayList<>();
        String sql = "select * from STREAK order by ID_STREAK";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StreakTO streak = new StreakTO();
                streak.setIdStreak(rs.getLong("ID_STREAK"));
                streak.setIdUsuario(rs.getLong("ID_USUARIO"));
                streak.setDiasConsecutivos(rs.getInt("QT_DIAS_CONSECUTIVOS"));
                streak.setDataUltimaAtividade(rs.getDate("DT_ULTIMO_ACESSO").toLocalDate());
                streaks.add(streak);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return streaks;
    }

    public StreakTO buscarPorId(Long idStreak) {
        String sql = "select * from STREAK where ID_STREAK = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idStreak);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                StreakTO streak = new StreakTO();
                streak.setIdStreak(rs.getLong("ID_STREAK"));
                streak.setIdUsuario(rs.getLong("ID_USUARIO"));
                streak.setDiasConsecutivos(rs.getInt("QT_DIAS_CONSECUTIVOS"));
                streak.setDataUltimaAtividade(rs.getDate("DT_ULTIMO_ACESSO").toLocalDate());
                return streak;
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

    public StreakTO buscarPorUsuario(Long idUsuario) {
        String sql = "select * from STREAK where ID_USUARIO = ? order by ID_STREAK";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                StreakTO streak = new StreakTO();
                streak.setIdStreak(rs.getLong("ID_STREAK"));
                streak.setIdUsuario(rs.getLong("ID_USUARIO"));
                streak.setDiasConsecutivos(rs.getInt("QT_DIAS_CONSECUTIVOS"));
                streak.setDataUltimaAtividade(rs.getDate("DT_ULTIMO_ACESSO").toLocalDate());
                return streak;
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

    public StreakTO inserir(StreakTO streak) {
        String sql = "insert into STREAK (ID_USUARIO, QT_DIAS_CONSECUTIVOS, DT_ULTIMO_ACESSO) values (?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, streak.getIdUsuario());
            ps.setInt(2, streak.getDiasConsecutivos() == null ? 0 : streak.getDiasConsecutivos());
            ps.setDate(3, Date.valueOf(streak.getDataUltimaAtividade()));
            if (ps.executeUpdate() > 0) {
                return streak;
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

    public StreakTO atualizar(StreakTO streak) {
        String sql = "update STREAK set ID_USUARIO = ?, QT_DIAS_CONSECUTIVOS = ?, DT_ULTIMO_ACESSO = ? where ID_STREAK = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, streak.getIdUsuario());
            ps.setInt(2, streak.getDiasConsecutivos() == null ? 0 : streak.getDiasConsecutivos());
            ps.setDate(3, Date.valueOf(streak.getDataUltimaAtividade()));
            ps.setLong(4, streak.getIdStreak());
            if (ps.executeUpdate() > 0) {
                return streak;
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

    public boolean excluir(Long idStreak) {
        String sql = "delete from STREAK where ID_STREAK = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idStreak);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }
}
