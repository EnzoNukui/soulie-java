package br.com.fiap.to;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class AvatarTO {
    private Long idAvatar;
    @NotNull
    @Positive
    private Long idUsuario;
    @Positive
    private Integer nivelEvolucao;
    @PositiveOrZero
    private Integer experiencia;
    @Size(max = 70)
    private String temperamento;

    public AvatarTO() {
    }

    public AvatarTO(Long idAvatar, Long idUsuario, Integer nivelEvolucao, Integer experiencia, String temperamento) {
        this.idAvatar = idAvatar;
        this.idUsuario = idUsuario;
        this.nivelEvolucao = nivelEvolucao;
        this.experiencia = experiencia;
        this.temperamento = temperamento;
    }

    public Long getIdAvatar() {
        return idAvatar;
    }

    public void setIdAvatar(Long idAvatar) {
        this.idAvatar = idAvatar;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getNivelEvolucao() {
        return nivelEvolucao;
    }

    public void setNivelEvolucao(Integer nivelEvolucao) {
        this.nivelEvolucao = nivelEvolucao;
    }

    public Integer getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Integer experiencia) {
        this.experiencia = experiencia;
    }

    public String getTemperamento() {
        return temperamento;
    }

    public void setTemperamento(String temperamento) {
        this.temperamento = temperamento;
    }
}
