package com.acme.expenses.gestaogastos.repository;

import com.acme.expenses.gestaogastos.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    List<Cartao> findByCpfTitular(String cpf);
}
