package com.acme.expenses.gestaogastos.service;

import com.acme.expenses.gestaogastos.domain.Cartao;
import com.acme.expenses.gestaogastos.repository.CartaoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {

    private final CartaoRepository repository;

    public CartaoService(CartaoRepository repository) {
        this.repository = repository;
    }

    public List<Cartao> pesquisarPorCpf(String cpf) {
        return repository.findByCpfTitular(cpf);
    }
}
