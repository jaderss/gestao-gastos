package com.acme.expenses.gestaogastos.repository;

import com.acme.expenses.gestaogastos.domain.Transacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends CrudRepository<Transacao, Long> {
}
