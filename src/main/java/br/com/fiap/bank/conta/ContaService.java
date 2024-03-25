package br.com.fiap.bank.conta;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@Service
public class ContaService {

    ContaRepository repository;

    public ContaService(ContaRepository repository) {
        this.repository = repository;
    }
    
    public Conta depositar(Long numero, BigDecimal valor){
        var conta = buscarPorId(numero);
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new ResponseStatusException(BAD_REQUEST, "Valor deve ser maior que zero");
        }
        conta.setSaldo(conta.getSaldo().add(valor));
        return repository.save(conta);
    }

    public Conta criar(@Valid Conta conta) {
        return repository.save(conta);
    }

    public List<Conta> buscarTodas() {
        return repository.findAll();
    }

    public List<Conta> buscarPorCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public Conta buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new ResponseStatusException(NOT_FOUND, "Conta não encontrada")
        );
    }

    public void encerrar(Long id) {
        var conta = buscarPorId(id);
        conta.setAtiva(false);
        repository.save(conta);
    }

    public Conta pix(Long idOrigem, Long contaDestino, BigDecimal valor) {
        var contaOrigem = buscarPorId(idOrigem);
        var contaDestinoObj = buscarPorId(contaDestino);
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new ResponseStatusException(BAD_REQUEST, "Valor deve ser maior que zero");
        }
        if (contaOrigem.getSaldo().compareTo(valor) < 0){
            throw new ResponseStatusException(BAD_REQUEST, "Saldo insuficiente");
        }
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestinoObj.setSaldo(contaDestinoObj.getSaldo().add(valor));
        repository.save(contaOrigem);
        return repository.save(contaDestinoObj);
    }

}
