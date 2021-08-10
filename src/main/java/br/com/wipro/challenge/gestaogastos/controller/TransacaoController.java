package br.com.wipro.challenge.gestaogastos.controller;

import br.com.wipro.challenge.gestaogastos.domain.Transacao;
import br.com.wipro.challenge.gestaogastos.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
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

    @GetMapping("/ultimos/{quantidadeDias}")
    public List<Transacao> pesquisarPorCodigoUsuarioEPeriodo(@RequestParam(name = "codigoUsuario") Long codigoUsuario,
                                                             @PathParam("quantidadeDias") Integer quantidadeDias) {
        return service.pesquisarPorCodigoUsuarioEPeriodo(codigoUsuario, quantidadeDias);
    }

    @GetMapping
    public List<Transacao> pesquisarPorCodigoUsuarioEData(@RequestParam(name = "codigoUsuario") Long codigoUsuario,
                                                          @RequestParam(name = "dataTransacao")
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataTransacao) {
        return service.pesquisarPorCodigoUsuarioEData(codigoUsuario, dataTransacao);
    }

}
