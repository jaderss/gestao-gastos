package com.acme.expenses.gestaogastos.controller;

import com.acme.expenses.gestaogastos.domain.Transacao;
import com.acme.expenses.gestaogastos.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService service;

    @PostMapping
    public Transacao inserir(@RequestBody Transacao transacao) {
        return service.inserir(transacao);
    }

    @GetMapping("/{id}")
    public Transacao buscarPorId(@PathVariable(name = "id") Long id) {
        return service.pesquisarPorId(id).orElseThrow();
    }
}
