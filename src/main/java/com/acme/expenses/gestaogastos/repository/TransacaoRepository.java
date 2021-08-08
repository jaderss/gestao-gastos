package com.acme.expenses.gestaogastos.repository;

import com.acme.expenses.gestaogastos.domain.Transacao;
import org.apache.maven.lifecycle.internal.LifecycleStarter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends CrudRepository<Transacao, Long> {

    List<Transacao> findByCodigoUsuarioAndDataAfter(Long codigoUsuario, LocalDateTime dataInicial);
}
