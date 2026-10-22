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
        // O avatar é criado junto com o usuário para manter a jornada consistente.
        return null;
    }

    public AvatarTO atualizar(AvatarTO avatar) {
        if (avatar == null || avatar.getIdAvatar() == null) {
            return null;
        }

        AvatarTO avatarAtual = avatarDAO.buscarPorId(avatar.getIdAvatar());
        if (avatarAtual == null) {
            return null;
        }

        // Evolução e experiência são atualizadas pelo registro de atividades.
        avatar.setIdUsuario(avatarAtual.getIdUsuario());
        avatar.setNivelEvolucao(avatarAtual.getNivelEvolucao());
        avatar.setExperiencia(avatarAtual.getExperiencia());
        return avatarDAO.atualizar(avatar);
    }

    public boolean excluir(Long idAvatar) {
        // O avatar faz parte da jornada e não pode ser removido isoladamente.
        return false;
    }

}
