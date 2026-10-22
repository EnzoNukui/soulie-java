package br.com.fiap.to;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class AtividadeTO {
    private Long idRegistro;
    @NotNull
    @Positive
    private Long idUsuario;
    @NotNull
    @Positive
    private Long idMissao;
    private LocalDate dataRegistro;
    @Size(max = 200)
    private String observacao;

    public AtividadeTO() {
    }

    public AtividadeTO(Long idRegistro, Long idUsuario, Long idMissao, LocalDate dataRegistro,
                       String observacao) {
        this.idRegistro = idRegistro;
        this.idUsuario = idUsuario;
        this.idMissao = idMissao;
        this.dataRegistro = dataRegistro;
        this.observacao = observacao;
    }

    public Long getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Long idRegistro) {
        this.idRegistro = idRegistro;
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

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
