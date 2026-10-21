package com.acme.expenses.gestaogastos.repository;

import com.acme.expenses.gestaogastos.domain.Cartao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    List<Cartao> findByCpfTitular(String cpf);
}
