package br.com.fiap.bo;

import br.com.fiap.dao.PostagemDAO;
import br.com.fiap.to.PostagemTO;
import java.util.ArrayList;

public class PostagemBO {
    private PostagemDAO postagemDAO = new PostagemDAO();

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
        return postagemDAO.inserir(postagem);
    }

    public PostagemTO atualizar(PostagemTO postagem) {
        return postagemDAO.atualizar(postagem);
    }

    public boolean excluir(Long idPostagem) {
        return postagemDAO.excluir(idPostagem);
    }

}
