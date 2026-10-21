package br.com.fiap.to;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class UsuarioTO {
    private Long idUsuario;
    @NotBlank
    @Size(max = 50)
    private String nomeUsuario;
    @NotBlank
    @Email
    @Size(max = 100)
    private String email;
    @PositiveOrZero
    private Integer pontosAcumulados;
    @Pattern(regexp = "[AI]")
    private String status;
    @Size(max = 100)
    private String identificadorJornada;

    public UsuarioTO() {
    }

    public UsuarioTO(Long idUsuario, String nomeUsuario, String email, Integer pontosAcumulados,
                     String status, String identificadorJornada) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.pontosAcumulados = pontosAcumulados;
        this.status = status;
        this.identificadorJornada = identificadorJornada;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPontosAcumulados() {
        return pontosAcumulados;
    }

    public void setPontosAcumulados(Integer pontosAcumulados) {
        this.pontosAcumulados = pontosAcumulados;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdentificadorJornada() {
        return identificadorJornada;
    }

    public void setIdentificadorJornada(String identificadorJornada) {
        this.identificadorJornada = identificadorJornada;
    }
}
