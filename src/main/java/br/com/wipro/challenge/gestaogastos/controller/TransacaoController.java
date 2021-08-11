package br.com.wipro.challenge.gestaogastos.controller;

import br.com.wipro.challenge.gestaogastos.domain.Transacao;
import br.com.wipro.challenge.gestaogastos.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
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
    public Transacao buscarPorId(@PathVariable(name = "id") String id) {
        return service.pesquisarPorId(id).orElseThrow();
    }

    @PostMapping("/{id}/categorizar")
    public Transacao buscarPorId(@PathVariable(name = "id") String id,
                                 @RequestBody @Validated @Length(max = 50) String categoria) {
        return service.categorizar(id, categoria);
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

    @GetMapping("/categorias")
    public List<String> pesquisarCategorias(@RequestParam(name = "filtro") String filtro) {
        return service.pesquisarCategorias(filtro);
    }

}
