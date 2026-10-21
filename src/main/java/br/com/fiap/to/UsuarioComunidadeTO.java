package br.com.fiap.to;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public class UsuarioComunidadeTO {
    @NotNull
    @Positive
    private Long idUsuario;
    @NotNull
    @Positive
    private Long idComunidade;
    @NotNull
    @PastOrPresent
    private LocalDate dataIngresso;

    public UsuarioComunidadeTO() {
    }

    public UsuarioComunidadeTO(Long idUsuario, Long idComunidade, LocalDate dataIngresso) {
        this.idUsuario = idUsuario;
        this.idComunidade = idComunidade;
        this.dataIngresso = dataIngresso;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdComunidade() {
        return idComunidade;
    }

    public void setIdComunidade(Long idComunidade) {
        this.idComunidade = idComunidade;
    }

    public LocalDate getDataIngresso() {
        return dataIngresso;
    }

    public void setDataIngresso(LocalDate dataIngresso) {
        this.dataIngresso = dataIngresso;
    }
}
