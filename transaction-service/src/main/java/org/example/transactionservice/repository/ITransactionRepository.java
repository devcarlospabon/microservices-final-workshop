package org.example.transactionservice.repository;

import org.example.transactionservice.model.Transaction;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ITransactionRepository extends R2dbcRepository<Transaction, Long> {

    Flux<Transaction> findByAccountId(Long accountId);
}
