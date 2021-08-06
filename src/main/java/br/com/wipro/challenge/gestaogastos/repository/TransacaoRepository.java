package br.com.wipro.challenge.gestaogastos.repository;

import br.com.wipro.challenge.gestaogastos.domain.Transacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends CrudRepository<Transacao, Long> {
}
