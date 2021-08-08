package com.acme.expenses.gestaogastos.service;

import com.acme.expenses.gestaogastos.domain.Transacao;
import com.acme.expenses.gestaogastos.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    public List<Transacao> pesquisarPorCodigoUsuario(Long codigoUsuario, Integer quantidadeDias) {
        LocalDate dataInicial = LocalDate.now().minusDays(quantidadeDias);
        return repository.findByCodigoUsuarioAndDataAfter(codigoUsuario, LocalDateTime.from(dataInicial));
    }

}
