package br.com.fiap.dao;

import br.com.fiap.to.PostagemTO;

import java.sql.*;
import java.util.ArrayList;

public class PostagemDAO {

    public ArrayList<PostagemTO> listarTodas() {
        ArrayList<PostagemTO> postagens = new ArrayList<>();
        String sql = "select * from POSTAGEM order by ID_POSTAGEM";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PostagemTO postagem = new PostagemTO();
                postagem.setIdPostagem(rs.getLong("ID_POSTAGEM"));
                postagem.setIdUsuario(rs.getLong("ID_USUARIO"));
                postagem.setIdComunidade(rs.getLong("ID_COMUNIDADE"));
                postagem.setTitulo(rs.getString("NM_POSTAGEM"));
                postagem.setConteudo(rs.getString("DS_CONTEUDO_POSTAGEM"));
                postagem.setDataPostagem(rs.getTimestamp("DT_POSTAGEM").toLocalDateTime());
                postagens.add(postagem);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return postagens;
    }

    public PostagemTO buscarPorId(Long idPostagem) {
        String sql = "select * from POSTAGEM where ID_POSTAGEM = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idPostagem);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PostagemTO postagem = new PostagemTO();
                postagem.setIdPostagem(rs.getLong("ID_POSTAGEM"));
                postagem.setIdUsuario(rs.getLong("ID_USUARIO"));
                postagem.setIdComunidade(rs.getLong("ID_COMUNIDADE"));
                postagem.setTitulo(rs.getString("NM_POSTAGEM"));
                postagem.setConteudo(rs.getString("DS_CONTEUDO_POSTAGEM"));
                postagem.setDataPostagem(rs.getTimestamp("DT_POSTAGEM").toLocalDateTime());
                return postagem;
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

    public ArrayList<PostagemTO> listarPorUsuario(Long idUsuario) {
        ArrayList<PostagemTO> postagens = new ArrayList<>();
        String sql = "select * from POSTAGEM where ID_USUARIO = ? order by DT_POSTAGEM desc";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PostagemTO postagem = new PostagemTO();
                postagem.setIdPostagem(rs.getLong("ID_POSTAGEM"));
                postagem.setIdUsuario(rs.getLong("ID_USUARIO"));
                postagem.setIdComunidade(rs.getLong("ID_COMUNIDADE"));
                postagem.setTitulo(rs.getString("NM_POSTAGEM"));
                postagem.setConteudo(rs.getString("DS_CONTEUDO_POSTAGEM"));
                postagem.setDataPostagem(rs.getTimestamp("DT_POSTAGEM").toLocalDateTime());
                postagens.add(postagem);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return postagens;
    }

    public ArrayList<PostagemTO> listarPorComunidade(Long idComunidade) {
        ArrayList<PostagemTO> postagens = new ArrayList<>();
        String sql = "select * from POSTAGEM where ID_COMUNIDADE = ? order by DT_POSTAGEM desc";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idComunidade);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PostagemTO postagem = new PostagemTO();
                postagem.setIdPostagem(rs.getLong("ID_POSTAGEM"));
                postagem.setIdUsuario(rs.getLong("ID_USUARIO"));
                postagem.setIdComunidade(rs.getLong("ID_COMUNIDADE"));
                postagem.setTitulo(rs.getString("NM_POSTAGEM"));
                postagem.setConteudo(rs.getString("DS_CONTEUDO_POSTAGEM"));
                postagem.setDataPostagem(rs.getTimestamp("DT_POSTAGEM").toLocalDateTime());
                postagens.add(postagem);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return postagens;
    }

    public PostagemTO inserir(PostagemTO postagem) {
        String sql = "insert into POSTAGEM (DS_CONTEUDO_POSTAGEM, NM_POSTAGEM, ID_USUARIO, ID_COMUNIDADE, DT_POSTAGEM) values (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, postagem.getConteudo());
            ps.setString(2, postagem.getTitulo());
            ps.setLong(3, postagem.getIdUsuario());
            ps.setLong(4, postagem.getIdComunidade());
            ps.setTimestamp(5, Timestamp.valueOf(postagem.getDataPostagem()));
            if (ps.executeUpdate() > 0) {
                return postagem;
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

    public PostagemTO atualizar(PostagemTO postagem) {
        String sql = "update POSTAGEM set DS_CONTEUDO_POSTAGEM = ?, NM_POSTAGEM = ?, ID_USUARIO = ?, ID_COMUNIDADE = ?, DT_POSTAGEM = ? where ID_POSTAGEM = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, postagem.getConteudo());
            ps.setString(2, postagem.getTitulo());
            ps.setLong(3, postagem.getIdUsuario());
            ps.setLong(4, postagem.getIdComunidade());
            ps.setTimestamp(5, Timestamp.valueOf(postagem.getDataPostagem()));
            ps.setLong(6, postagem.getIdPostagem());
            if (ps.executeUpdate() > 0) {
                return postagem;
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

    public boolean excluir(Long idPostagem) {
        String sql = "delete from POSTAGEM where ID_POSTAGEM = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idPostagem);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }
}
