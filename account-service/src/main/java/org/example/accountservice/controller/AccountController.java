package org.example.accountservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.accountservice.dto.GetTransactionDto;
import org.example.accountservice.grpc.TransactionConsumer;
import org.example.accountservice.model.Account;
import org.example.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransactionConsumer transactionConsumer;

    @GetMapping
    public Flux<Account> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Account> getById(@PathVariable Long id) {
        return accountService.getById(id);
    }

    @PostMapping
    public Mono<Account> create(@RequestBody Account account) {
        return accountService.create(account);
    }

    @PutMapping("/{id}")
    public Mono<Account> update(@PathVariable Long id, @RequestBody Account account) {
        return accountService.update(id, account);
    }

    @GetMapping("/history/{id}")
    public Flux<GetTransactionDto> getHistoryById(@PathVariable Long id) {
        return transactionConsumer.getTransactionHistory(id);
    }

}
