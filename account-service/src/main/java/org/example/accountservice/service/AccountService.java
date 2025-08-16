package org.example.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.example.accountservice.exception.BusinessException;
import org.example.accountservice.model.Account;
import org.example.accountservice.repository.IAccountRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {

    public static final String ACCOUNT_NOT_FOUND = "Account not found";
    private final IAccountRepository repository;
    private final BankService bankService;

    public Flux<Account> getAll() {
        return repository.findAll();
    }

    public Mono<Account> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException(ACCOUNT_NOT_FOUND)));
    }

    public Mono<Account> create(Account account) {
        account.setCreatedAt(LocalDateTime.now());
        return bankService.isBankExists(account.getBankId())
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new BusinessException("Bank not found with ID: " + account.getBankId())))
                .flatMap(unused -> repository.save(account));
    }

    public Mono<Account> update(Long id, Account account) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException(ACCOUNT_NOT_FOUND)))
                .flatMap(existingAccount -> {
                    existingAccount.setAccountNumber(account.getAccountNumber());
                    existingAccount.setAccountHolderName(account.getAccountHolderName());
                    existingAccount.setBalance(account.getBalance());
                    existingAccount.setUpdatedAt(LocalDateTime.now());
                    return repository.save(existingAccount);
                });
    }

}
