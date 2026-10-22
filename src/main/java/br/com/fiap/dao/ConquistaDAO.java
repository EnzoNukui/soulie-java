package br.com.fiap.dao;

import br.com.fiap.to.ConquistaTO;

import java.sql.*;
import java.util.ArrayList;

public class ConquistaDAO {

    public ConquistaTO buscarPorNome(Connection conexao, String nome) {
        String sql = "select * from CONQUISTA where NM_CONQUISTA = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ConquistaTO conquista = new ConquistaTO();
                    conquista.setIdConquista(rs.getLong("ID_CONQUISTA"));
                    conquista.setNome(rs.getString("NM_CONQUISTA"));
                    conquista.setDescricao(rs.getString("DS_CONQUISTA"));
                    return conquista;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar conquista: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<ConquistaTO> listarTodas() {
        ArrayList<ConquistaTO> conquistas = new ArrayList<>();
        String sql = "select * from CONQUISTA order by ID_CONQUISTA";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ConquistaTO conquista = new ConquistaTO();
                conquista.setIdConquista(rs.getLong("ID_CONQUISTA"));
                conquista.setNome(rs.getString("NM_CONQUISTA"));
                conquista.setDescricao(rs.getString("DS_CONQUISTA"));
                conquistas.add(conquista);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return conquistas;
    }

    public ConquistaTO buscarPorId(Long idConquista) {
        String sql = "select * from CONQUISTA where ID_CONQUISTA = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idConquista);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ConquistaTO conquista = new ConquistaTO();
                conquista.setIdConquista(rs.getLong("ID_CONQUISTA"));
                conquista.setNome(rs.getString("NM_CONQUISTA"));
                conquista.setDescricao(rs.getString("DS_CONQUISTA"));
                return conquista;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public ConquistaTO inserir(ConquistaTO conquista) {
        String sql = "insert into CONQUISTA (NM_CONQUISTA, DS_CONQUISTA) values (?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, conquista.getNome());
            ps.setString(2, conquista.getDescricao());
            if (ps.executeUpdate() > 0) {
                return conquista;
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

    public ConquistaTO atualizar(ConquistaTO conquista) {
        String sql = "update CONQUISTA set NM_CONQUISTA = ?, DS_CONQUISTA = ? where ID_CONQUISTA = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, conquista.getNome());
            ps.setString(2, conquista.getDescricao());
            ps.setLong(3, conquista.getIdConquista());
            if (ps.executeUpdate() > 0) {
                return conquista;
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

    public boolean excluir(Long idConquista) {
        String sql = "delete from CONQUISTA where ID_CONQUISTA = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idConquista);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }
}
