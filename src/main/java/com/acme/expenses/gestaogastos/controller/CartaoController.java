package com.acme.expenses.gestaogastos.controller;

import com.acme.expenses.gestaogastos.domain.Cartao;
import com.acme.expenses.gestaogastos.service.CartaoService;
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

    private final CartaoService service;

    @GetMapping
    public List<Cartao> pesquisarCartoesPorCPF(@RequestParam String cpf) {
        return service.pesquisarPorCpf(cpf);
    }
}
