package br.com.fiap.bank.movimentacao;

import java.math.BigDecimal;

public record MovimentacaoRequest(BigDecimal valor, Long contaDestino){}


