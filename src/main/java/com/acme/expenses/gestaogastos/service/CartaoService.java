package com.acme.expenses.gestaogastos.service;

import com.acme.expenses.gestaogastos.domain.Cartao;
import com.acme.expenses.gestaogastos.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository repository;

    public List<Cartao> pesquisarPorCpf(String cpf) {
        return repository.findByCpfTitular(cpf);
    }
}
