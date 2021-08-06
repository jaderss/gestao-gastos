package com.acme.expenses.gestaogastos.service;

import com.acme.expenses.gestaogastos.domain.Transacao;
import com.acme.expenses.gestaogastos.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository repository;

    public Transacao inserir(Transacao transacao) {
        return repository.save(transacao);
    }

    public Optional<Transacao> pesquisarPorId(Long id) {
        return repository.findById(id);
    }

}
