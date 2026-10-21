package br.com.fiap.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class MissaoTO {
    private Long idMissao;
    @NotBlank
    @Size(max = 60)
    private String nome;
    @NotBlank
    @Size(max = 200)
    private String descricao;
    @NotNull
    @PositiveOrZero
    private Integer pontosRecompensa;
    @Positive
    private Long idUsuarioCriador;
    @NotNull
    @Positive
    private Integer metaDias;

    public MissaoTO() {
    }

    public MissaoTO(Long idMissao, String nome, String descricao, Integer pontosRecompensa,
                    Long idUsuarioCriador, Integer metaDias) {
        this.idMissao = idMissao;
        this.nome = nome;
        this.descricao = descricao;
        this.pontosRecompensa = pontosRecompensa;
        this.idUsuarioCriador = idUsuarioCriador;
        this.metaDias = metaDias;
    }

    public Long getIdMissao() {
        return idMissao;
    }

    public void setIdMissao(Long idMissao) {
        this.idMissao = idMissao;
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

    public Integer getPontosRecompensa() {
        return pontosRecompensa;
    }

    public void setPontosRecompensa(Integer pontosRecompensa) {
        this.pontosRecompensa = pontosRecompensa;
    }

    public Long getIdUsuarioCriador() {
        return idUsuarioCriador;
    }

    public void setIdUsuarioCriador(Long idUsuarioCriador) {
        this.idUsuarioCriador = idUsuarioCriador;
    }

    public Integer getMetaDias() {
        return metaDias;
    }

    public void setMetaDias(Integer metaDias) {
        this.metaDias = metaDias;
    }
}
