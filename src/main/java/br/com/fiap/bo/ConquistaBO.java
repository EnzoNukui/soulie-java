package br.com.fiap.bo;

import br.com.fiap.dao.ConquistaDAO;
import br.com.fiap.to.ConquistaTO;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

public class ConquistaBO {
    static final String PRIMEIROS_PASSOS = "Primeiros passos";
    static final String CONSISTENCIA = "Consistência";
    static final String SEMANA_SUSTENTAVEL = "Semana sustentável";
    static final String COMPROMISSO_VERDE = "Compromisso verde";
    static final String SOULIE_EVOLUIDA = "Soulie evoluída";
    private static final Set<String> CONQUISTAS_AUTOMATICAS = Set.of(
            PRIMEIROS_PASSOS.toLowerCase(Locale.ROOT),
            CONSISTENCIA.toLowerCase(Locale.ROOT),
            SEMANA_SUSTENTAVEL.toLowerCase(Locale.ROOT),
            COMPROMISSO_VERDE.toLowerCase(Locale.ROOT),
            SOULIE_EVOLUIDA.toLowerCase(Locale.ROOT));
    private ConquistaDAO conquistaDAO = new ConquistaDAO();

    public ArrayList<ConquistaTO> listarTodas() {
        return conquistaDAO.listarTodas();
    }

    public ConquistaTO buscarPorId(Long idConquista) {
        return conquistaDAO.buscarPorId(idConquista);
    }

    public ConquistaTO inserir(ConquistaTO conquista) {
        if (conquista == null || eAutomatica(conquista.getNome())) {
            return null;
        }
        return conquistaDAO.inserir(conquista);
    }

    public ConquistaTO atualizar(ConquistaTO conquista) {
        if (conquista == null || conquista.getIdConquista() == null
                || eAutomatica(conquista.getNome())) {
            return null;
        }
        ConquistaTO conquistaAtual = conquistaDAO.buscarPorId(conquista.getIdConquista());
        if (conquistaAtual == null || eAutomatica(conquistaAtual.getNome())) {
            return null;
        }
        return conquistaDAO.atualizar(conquista);
    }

    public boolean excluir(Long idConquista) {
        if (idConquista == null) {
            return false;
        }
        ConquistaTO conquista = conquistaDAO.buscarPorId(idConquista);
        if (conquista == null || eAutomatica(conquista.getNome())) {
            return false;
        }
        return conquistaDAO.excluir(idConquista);
    }

    static boolean eAutomatica(String nome) {
        return nome != null && CONQUISTAS_AUTOMATICAS.contains(nome.trim().toLowerCase(Locale.ROOT));
    }

}
