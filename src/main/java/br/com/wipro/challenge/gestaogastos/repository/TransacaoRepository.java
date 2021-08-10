package br.com.wipro.challenge.gestaogastos.repository;

import br.com.wipro.challenge.gestaogastos.domain.Transacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends MongoRepository<Transacao, Long> {

    List<Transacao> findByCodigoUsuarioAndDataAfter(Long codigoUsuario, LocalDateTime dataInicial);

    List<Transacao> findByCodigoUsuarioAndDataBetween(Long codigoUsuario, LocalDateTime dataInicial, LocalDateTime dataFinal);
}
