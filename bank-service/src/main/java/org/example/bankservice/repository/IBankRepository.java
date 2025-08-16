package org.example.bankservice.repository;

import org.example.bankservice.model.Bank;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface IBankRepository extends R2dbcRepository<Bank, Long> {
}
