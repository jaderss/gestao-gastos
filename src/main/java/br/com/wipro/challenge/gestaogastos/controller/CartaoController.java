package br.com.wipro.challenge.gestaogastos.controller;

import br.com.wipro.challenge.gestaogastos.domain.Cartao;
import br.com.wipro.challenge.gestaogastos.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartaoController {

    private final CartaoRepository repository;

    @GetMapping
    public List<Cartao> pesquisarCartoesPorCPF(@RequestParam String cpf) {
        return repository.findByCpfTitular(cpf);
    }
}
