package br.com.fiap.bank.conta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByCpf(String cpf);

    
}
