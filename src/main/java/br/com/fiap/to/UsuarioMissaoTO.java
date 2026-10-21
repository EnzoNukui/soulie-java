package br.com.fiap.to;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class UsuarioMissaoTO {
    @NotNull
    @Positive
    private Long idUsuario;
    @NotNull
    @Positive
    private Long idMissao;
    @NotNull
    @PastOrPresent
    private LocalDate dataInicio;
    @PastOrPresent
    private LocalDate dataConclusao;
    @Size(max = 15)
    private String status;

    public UsuarioMissaoTO() {
    }

    public UsuarioMissaoTO(Long idUsuario, Long idMissao, LocalDate dataInicio,
                           LocalDate dataConclusao, String status) {
        this.idUsuario = idUsuario;
        this.idMissao = idMissao;
        this.dataInicio = dataInicio;
        this.dataConclusao = dataConclusao;
        this.status = status;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdMissao() {
        return idMissao;
    }

    public void setIdMissao(Long idMissao) {
        this.idMissao = idMissao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
