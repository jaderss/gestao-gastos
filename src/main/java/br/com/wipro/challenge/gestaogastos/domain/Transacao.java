package br.com.wipro.challenge.gestaogastos.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RedisHash("Transacao")
@Getter
@Setter
public class Transacao {

    @Id
    private Long id;

    private String descricao;

    private BigDecimal valor;

    private Long codigoUsuario;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime data;
}
