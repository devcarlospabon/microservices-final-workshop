package org.example.bankservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.bankservice.model.Bank;
import org.example.bankservice.service.BankService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/banks")
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;

    @GetMapping
    public Flux<Bank> getAll() {
        return bankService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Bank> getById(@PathVariable Long id) {
        return bankService.getById(id);
    }

    @PostMapping
    public Mono<Bank> create(@RequestBody Bank bank) {
        return bankService.create(bank);
    }

    @PutMapping("/{id}")
    public Mono<Bank> update(@PathVariable Long id, @RequestBody Bank bank) {
        return bankService.update(id, bank);
    }

}
