package br.com.fiap.to;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class PostagemTO {
    private Long idPostagem;
    @NotNull
    @Positive
    private Long idUsuario;
    @NotNull
    @Positive
    private Long idComunidade;
    @NotBlank
    @Size(max = 80)
    private String titulo;
    @NotBlank
    @Size(max = 200)
    private String conteudo;
    @NotNull
    @PastOrPresent
    private LocalDateTime dataPostagem;

    public PostagemTO() {
    }

    public PostagemTO(Long idPostagem, Long idUsuario, Long idComunidade, String titulo,
                      String conteudo, LocalDateTime dataPostagem) {
        this.idPostagem = idPostagem;
        this.idUsuario = idUsuario;
        this.idComunidade = idComunidade;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataPostagem = dataPostagem;
    }

    public Long getIdPostagem() {
        return idPostagem;
    }

    public void setIdPostagem(Long idPostagem) {
        this.idPostagem = idPostagem;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(LocalDateTime dataPostagem) {
        this.dataPostagem = dataPostagem;
    }
}
