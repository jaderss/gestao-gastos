package br.com.wipro.challenge.gestaogastos.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Document(collection = "transacoes")
public class Transacao {

    @Id
    private String id;

    @Setter
    private String descricao;

    @Setter
    private BigDecimal valor;

    @Setter
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
}
