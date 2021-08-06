package br.com.wipro.challenge.gestaogastos.service;

import br.com.wipro.challenge.gestaogastos.domain.Cartao;
import br.com.wipro.challenge.gestaogastos.repository.CartaoRepository;
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
