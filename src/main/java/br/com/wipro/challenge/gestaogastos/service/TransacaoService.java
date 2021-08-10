package br.com.wipro.challenge.gestaogastos.service;

import br.com.wipro.challenge.gestaogastos.domain.Transacao;
import br.com.wipro.challenge.gestaogastos.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository repository;

    public Transacao inserir(Transacao transacao) {
        return repository.save(transacao);
    }

    public Page<Transacao> pesquisarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Transacao> pesquisarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Transacao> pesquisarPorCodigoUsuarioEPeriodo(Long codigoUsuario, Integer quantidadeDias) {
        LocalDate dataInicial = LocalDate.now().minusDays(quantidadeDias);
        return repository.findByCodigoUsuarioAndDataAfter(codigoUsuario, LocalDateTime.from(dataInicial));
    }

    public List<Transacao> pesquisarPorCodigoUsuarioEData(Long codigoUsuario, LocalDate data) {
        LocalDateTime dataInicial = data.atStartOfDay();
        LocalDateTime dataFinal = data.atTime(LocalTime.MAX);
        return repository.findByCodigoUsuarioAndDataBetween(codigoUsuario, dataInicial, dataFinal);
    }

}
