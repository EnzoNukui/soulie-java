package br.com.fiap.dao;

import br.com.fiap.to.ComunidadeTO;

import java.sql.*;
import java.util.ArrayList;

public class ComunidadeDAO {

    public ArrayList<ComunidadeTO> listarTodas() {
        ArrayList<ComunidadeTO> comunidades = new ArrayList<>();
        String sql = "select * from COMUNIDADE order by ID_COMUNIDADE";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ComunidadeTO comunidade = new ComunidadeTO();
                comunidade.setIdComunidade(rs.getLong("ID_COMUNIDADE"));
                comunidade.setNome(rs.getString("NM_COMUNIDADE"));
                comunidade.setTema(rs.getString("DS_TEMA"));
                comunidades.add(comunidade);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return comunidades;
    }

    public ComunidadeTO buscarPorId(Long idComunidade) {
        String sql = "select * from COMUNIDADE where ID_COMUNIDADE = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idComunidade);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ComunidadeTO comunidade = new ComunidadeTO();
                comunidade.setIdComunidade(rs.getLong("ID_COMUNIDADE"));
                comunidade.setNome(rs.getString("NM_COMUNIDADE"));
                comunidade.setTema(rs.getString("DS_TEMA"));
                return comunidade;
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

    public ComunidadeTO inserir(ComunidadeTO comunidade) {
        String sql = "insert into COMUNIDADE (NM_COMUNIDADE, DS_TEMA) values (?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, comunidade.getNome());
            ps.setString(2, comunidade.getTema());
            if (ps.executeUpdate() > 0) {
                return comunidade;
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

    public ComunidadeTO atualizar(ComunidadeTO comunidade) {
        String sql = "update COMUNIDADE set NM_COMUNIDADE = ?, DS_TEMA = ? where ID_COMUNIDADE = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, comunidade.getNome());
            ps.setString(2, comunidade.getTema());
            ps.setLong(3, comunidade.getIdComunidade());
            if (ps.executeUpdate() > 0) {
                return comunidade;
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

    public boolean excluir(Long idComunidade) {
        String sql = "delete from COMUNIDADE where ID_COMUNIDADE = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idComunidade);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }
}
