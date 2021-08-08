package br.com.wipro.challenge.gestaogastos.service;

import br.com.wipro.challenge.gestaogastos.domain.Transacao;
import br.com.wipro.challenge.gestaogastos.repository.TransacaoRepository;
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