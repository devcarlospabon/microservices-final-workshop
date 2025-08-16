package org.example.accountservice.repository;

import org.example.accountservice.model.Account;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface IAccountRepository extends R2dbcRepository<Account, Long> {
}
