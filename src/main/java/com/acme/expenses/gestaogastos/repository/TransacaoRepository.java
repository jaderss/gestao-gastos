package com.acme.expenses.gestaogastos.repository;

import com.acme.expenses.gestaogastos.domain.Transacao;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends MongoRepository<Transacao, String> {

    List<Transacao> findByCodigoUsuarioAndDataAfter(Long codigoUsuario, LocalDateTime dataInicial);

    List<Transacao> findByCodigoUsuarioAndDataBetween(
            Long codigoUsuario, LocalDateTime dataInicial, LocalDateTime dataFinal);

    List<Transacao> findByCategoriaStartingWith(String filtro);
}
