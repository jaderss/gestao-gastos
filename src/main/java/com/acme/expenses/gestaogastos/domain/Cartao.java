package com.acme.expenses.gestaogastos.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigoUsuario;

    private Long numero;

    private LocalDate dataValidade;

    private String nomeTitular;

    private String cpfTitular;

    private Integer cvc;

    public Long getCodigoUsuario() {
        return codigoUsuario;
    }

    public Long getNumero() {
        return numero;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public String getCpfTitular() {
        return cpfTitular;
    }

    public Integer getCvc() {
        return cvc;
    }
}
