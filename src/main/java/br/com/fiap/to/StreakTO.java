package br.com.fiap.to;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class StreakTO {
    private Long idStreak;
    @NotNull
    @Positive
    private Long idUsuario;
    @NotNull
    @PositiveOrZero
    private Integer diasConsecutivos;
    @NotNull
    @PastOrPresent
    private LocalDate dataUltimaAtividade;

    public StreakTO() {
    }

    public StreakTO(Long idStreak, Long idUsuario, Integer diasConsecutivos, LocalDate dataUltimaAtividade) {
        this.idStreak = idStreak;
        this.idUsuario = idUsuario;
        this.diasConsecutivos = diasConsecutivos;
        this.dataUltimaAtividade = dataUltimaAtividade;
    }

    public Long getIdStreak() {
        return idStreak;
    }

    public void setIdStreak(Long idStreak) {
        this.idStreak = idStreak;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getDiasConsecutivos() {
        return diasConsecutivos;
    }

    public void setDiasConsecutivos(Integer diasConsecutivos) {
        this.diasConsecutivos = diasConsecutivos;
    }

    public LocalDate getDataUltimaAtividade() {
        return dataUltimaAtividade;
    }

    public void setDataUltimaAtividade(LocalDate dataUltimaAtividade) {
        this.dataUltimaAtividade = dataUltimaAtividade;
    }
}
