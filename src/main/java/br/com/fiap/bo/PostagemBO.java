package br.com.fiap.bo;

import br.com.fiap.dao.PostagemDAO;
import br.com.fiap.dao.ComunidadeDAO;
import br.com.fiap.dao.UsuarioComunidadeDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.PostagemTO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class PostagemBO {
    private PostagemDAO postagemDAO = new PostagemDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ComunidadeDAO comunidadeDAO = new ComunidadeDAO();
    private UsuarioComunidadeDAO usuarioComunidadeDAO = new UsuarioComunidadeDAO();
    private static final ZoneId ZONA_NEGOCIO = ZoneId.of("America/Sao_Paulo");

    public ArrayList<PostagemTO> listarTodas() {
        return postagemDAO.listarTodas();
    }

    public PostagemTO buscarPorId(Long idPostagem) {
        return postagemDAO.buscarPorId(idPostagem);
    }

    public ArrayList<PostagemTO> listarPorUsuario(Long idUsuario) {
        return postagemDAO.listarPorUsuario(idUsuario);
    }

    public ArrayList<PostagemTO> listarPorComunidade(Long idComunidade) {
        return postagemDAO.listarPorComunidade(idComunidade);
    }

    public PostagemTO inserir(PostagemTO postagem) {
        if (postagem == null || postagem.getIdUsuario() == null || postagem.getIdComunidade() == null) {
            return null;
        }
        if (usuarioDAO.buscarPorId(postagem.getIdUsuario()) == null
                || comunidadeDAO.buscarPorId(postagem.getIdComunidade()) == null
                || usuarioComunidadeDAO.buscarPorIds(postagem.getIdUsuario(), postagem.getIdComunidade()) == null) {
            return null;
        }

        postagem.setDataPostagem(LocalDateTime.now(ZONA_NEGOCIO));
        return postagemDAO.inserir(postagem);
    }

    public PostagemTO atualizar(PostagemTO postagem) {
        if (postagem == null || postagem.getIdPostagem() == null) {
            return null;
        }
        PostagemTO postagemExistente = postagemDAO.buscarPorId(postagem.getIdPostagem());
        if (postagemExistente == null) {
            return null;
        }

        // Autor, comunidade e data de publicação não podem ser trocados pela edição.
        postagem.setIdUsuario(postagemExistente.getIdUsuario());
        postagem.setIdComunidade(postagemExistente.getIdComunidade());
        postagem.setDataPostagem(postagemExistente.getDataPostagem());
        return postagemDAO.atualizar(postagem);
    }

    public boolean excluir(Long idPostagem) {
        return postagemDAO.excluir(idPostagem);
    }

}
