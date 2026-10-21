package com.acme.expenses.gestaogastos.controller;

import com.acme.expenses.gestaogastos.domain.Transacao;
import com.acme.expenses.gestaogastos.service.TransacaoService;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @PostMapping
    public Transacao inserir(@RequestBody Transacao transacao) {
        return service.inserir(transacao);
    }

    @GetMapping("/{id}")
    public Transacao buscarPorId(@PathVariable(name = "id") String id) {
        return service.pesquisarPorId(id).orElseThrow();
    }

    @PostMapping("/{id}/categorizar")
    public Transacao buscarPorId(
            @PathVariable(name = "id") String id, @RequestBody @Validated @Length(max = 50) String categoria) {
        return service.categorizar(id, categoria);
    }

    @GetMapping("/ultimos/{quantidadeDias}")
    public List<Transacao> pesquisarPorCodigoUsuarioEPeriodo(
            @RequestParam(name = "codigoUsuario") Long codigoUsuario,
            @PathVariable("quantidadeDias") Integer quantidadeDias) {
        return service.pesquisarPorCodigoUsuarioEPeriodo(codigoUsuario, quantidadeDias);
    }

    @GetMapping
    public List<Transacao> pesquisarPorCodigoUsuarioEData(
            @RequestParam(name = "codigoUsuario") Long codigoUsuario,
            @RequestParam(name = "dataTransacao") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate dataTransacao) {
        return service.pesquisarPorCodigoUsuarioEData(codigoUsuario, dataTransacao);
    }

    @GetMapping("/categorias")
    public List<String> pesquisarCategorias(@RequestParam(name = "filtro") String filtro) {
        return service.pesquisarCategorias(filtro);
    }
}
