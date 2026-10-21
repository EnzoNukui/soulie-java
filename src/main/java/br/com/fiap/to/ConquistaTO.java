package br.com.fiap.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ConquistaTO {
    private Long idConquista;
    @NotBlank
    @Size(max = 70)
    private String nome;
    @Size(max = 80)
    private String descricao;

    public ConquistaTO() {
    }

    public ConquistaTO(Long idConquista, String nome, String descricao) {
        this.idConquista = idConquista;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getIdConquista() {
        return idConquista;
    }

    public void setIdConquista(Long idConquista) {
        this.idConquista = idConquista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
