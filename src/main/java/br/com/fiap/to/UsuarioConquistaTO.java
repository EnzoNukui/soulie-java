package br.com.fiap.to;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public class UsuarioConquistaTO {
    @NotNull
    @Positive
    private Long idUsuario;
    @NotNull
    @Positive
    private Long idConquista;
    @NotNull
    @PastOrPresent
    private LocalDate dataConquista;

    public UsuarioConquistaTO() {
    }

    public UsuarioConquistaTO(Long idUsuario, Long idConquista, LocalDate dataConquista) {
        this.idUsuario = idUsuario;
        this.idConquista = idConquista;
        this.dataConquista = dataConquista;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdConquista() {
        return idConquista;
    }

    public void setIdConquista(Long idConquista) {
        this.idConquista = idConquista;
    }

    public LocalDate getDataConquista() {
        return dataConquista;
    }

    public void setDataConquista(LocalDate dataConquista) {
        this.dataConquista = dataConquista;
    }
}
