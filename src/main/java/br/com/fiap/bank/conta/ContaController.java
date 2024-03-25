package br.com.fiap.bank.conta;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.bank.movimentacao.MovimentacaoRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("conta")
public class ContaController {

    ContaService service;

    public ContaController(ContaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Conta create(@RequestBody @Valid Conta conta) {
        return service.criar(conta);
    }

    @GetMapping
    public List<Conta> index(@RequestParam(required=false) String cpf) {
        if (cpf == null)
            return service.buscarTodas();

        return service.buscarPorCpf(cpf);
    }

    @GetMapping("{id}")
    public Conta getById(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.encerrar(id);
    }

    @PostMapping("{id}/deposito")
    public Conta depositar(@PathVariable Long id, @RequestBody MovimentacaoRequest movimentacao){
        return service.depositar(id, movimentacao.valor());
    }

    @PostMapping("{idOrigem}/pix")
    public Conta pix(@PathVariable Long idOrigem, @RequestBody MovimentacaoRequest movimentacao){
        return service.pix(idOrigem, movimentacao.contaDestino(), movimentacao.valor());
    }

}
