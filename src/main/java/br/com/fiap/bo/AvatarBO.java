package br.com.fiap.bo;

import br.com.fiap.dao.AvatarDAO;
import br.com.fiap.to.AvatarTO;
import java.util.ArrayList;

public class AvatarBO {
    private AvatarDAO avatarDAO = new AvatarDAO();

    public ArrayList<AvatarTO> listarTodos() {
        return avatarDAO.listarTodos();
    }

    public AvatarTO buscarPorId(Long idAvatar) {
        return avatarDAO.buscarPorId(idAvatar);
    }

    public AvatarTO buscarPorUsuario(Long idUsuario) {
        return avatarDAO.buscarPorUsuario(idUsuario);
    }

    public AvatarTO inserir(AvatarTO avatar) {
        return avatarDAO.inserir(avatar);
    }

    public AvatarTO atualizar(AvatarTO avatar) {
        return avatarDAO.atualizar(avatar);
    }

    public boolean excluir(Long idAvatar) {
        return avatarDAO.excluir(idAvatar);
    }

}
