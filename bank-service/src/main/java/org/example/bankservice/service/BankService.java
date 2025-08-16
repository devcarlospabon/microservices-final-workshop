package org.example.bankservice.service;

import lombok.RequiredArgsConstructor;
import org.example.bankservice.exception.BusinessException;
import org.example.bankservice.model.Bank;
import org.example.bankservice.repository.IBankRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BankService {

    private final IBankRepository repository;

    public Flux<Bank> getAll() {
        return repository.findAll();
    }

    public Mono<Bank> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException("Bank not found")));
    }

    public Mono<Bank> create(Bank bank) {
        bank.setId(null);
        bank.setCreatedAt(LocalDateTime.now());
        return repository.save(bank);
    }

    public Mono<Bank> update(Long id, Bank bank) {
        return repository.findById(id)
                .flatMap(existingBank -> {
                    existingBank.setName(bank.getName());
                    existingBank.setAddress(bank.getAddress());
                    existingBank.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existingBank);
                })
                .switchIfEmpty(Mono.error(new BusinessException("Bank not found for update")));
    }

}
