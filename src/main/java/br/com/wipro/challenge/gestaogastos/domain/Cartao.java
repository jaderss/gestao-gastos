package br.com.wipro.challenge.gestaogastos.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Cartao {

    @Id
    private Long numero;

    private LocalDate dataValidade;

    private String nomeTitular;

    private String cpfTitular;

    private Integer cvc;
}
