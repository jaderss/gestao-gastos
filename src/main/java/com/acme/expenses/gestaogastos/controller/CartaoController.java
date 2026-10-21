package com.acme.expenses.gestaogastos.controller;

import com.acme.expenses.gestaogastos.domain.Cartao;
import com.acme.expenses.gestaogastos.service.CartaoService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoService service;

    public CartaoController(CartaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cartao> pesquisarCartoesPorCPF(@RequestParam String cpf) {
        return service.pesquisarPorCpf(cpf);
    }
}
