package br.com.fiap.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ComunidadeTO {
    private Long idComunidade;
    @NotBlank
    @Size(max = 50)
    private String nome;
    @Size(max = 90)
    private String tema;

    public ComunidadeTO() {
    }

    public ComunidadeTO(Long idComunidade, String nome, String tema) {
        this.idComunidade = idComunidade;
        this.nome = nome;
        this.tema = tema;
    }

    public Long getIdComunidade() {
        return idComunidade;
    }

    public void setIdComunidade(Long idComunidade) {
        this.idComunidade = idComunidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}
