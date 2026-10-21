package br.com.fiap.bo;

import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.UsuarioTO;
import java.util.ArrayList;

public class UsuarioBO {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public ArrayList<UsuarioTO> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public UsuarioTO buscarPorId(Long idUsuario) {
        return usuarioDAO.buscarPorId(idUsuario);
    }

    public UsuarioTO buscarPorEmail(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }

    public UsuarioTO inserir(UsuarioTO usuario) {
        return usuarioDAO.inserir(usuario);
    }

    public UsuarioTO atualizar(UsuarioTO usuario) {
        return usuarioDAO.atualizar(usuario);
    }

    public boolean excluir(Long idUsuario) {
        return usuarioDAO.excluir(idUsuario);
    }

}
