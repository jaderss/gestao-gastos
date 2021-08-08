package br.com.wipro.challenge.gestaogastos.controller;

import br.com.wipro.challenge.gestaogastos.domain.Transacao;
import br.com.wipro.challenge.gestaogastos.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public List<Transacao> pesquisarPorCodigoUsuario(@RequestParam(name = "codigoUsuario") Long codigoUsuario,
                                                     @RequestParam(name = "quantidadeDias") Integer quantidadeDias) {
        return service.pesquisarPorCodigoUsuario(codigoUsuario, quantidadeDias);
    }

}
