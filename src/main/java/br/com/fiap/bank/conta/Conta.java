package br.com.fiap.bank.conta;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity(name = "contas")
@Data
public class Conta {

    @Id
    private Long numero;

    private String agencia;

    @Column(name = "nome_do_titular")
    @NotBlank(message = "Nome do titular é obrigatório")
    private String nomeTitular;

    @Column(unique = true)
    @CPF(message = "CPF Inválido")
    private String cpf;

    @PastOrPresent(message = "Data de abertura não pode ser no futuro")
    private LocalDate dataDeAbertura;

    @PositiveOrZero(message = "Saldo não pode ser negativo")
    private BigDecimal saldo;

    private boolean ativa = true;

    @Column(length = 8)
    //corrente,poupança ou salário
    @Pattern(
        regexp = "^(CORRENTE|POUPANCA|SALARIO)$",
        message = "Tipo deve ser CORRENTE, POUPANCA ou SALARIO"
    )    
    private String tipo;

    
}
