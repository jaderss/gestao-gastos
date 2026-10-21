package com.acme.expenses.gestaogastos.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "transacoes")
public class Transacao {

    @Id
    private String id;

    private String descricao;

    private BigDecimal valor;

    private Long codigoUsuario;

    private String categoria;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime data;

    public Transacao() {
        this.data = LocalDateTime.now();
    }

    public void categorizar(String categoria) {
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDateTime getData() {
        return data;
    }
}
