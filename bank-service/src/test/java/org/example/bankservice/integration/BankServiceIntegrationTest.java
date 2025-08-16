package org.example.bankservice.integration;

import org.example.bankservice.exception.BusinessException;
import org.example.bankservice.model.Bank;
import org.example.bankservice.repository.IBankRepository;
import org.example.bankservice.service.BankService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
class BankServiceIntegrationTest {


    @Autowired
    private BankService bankService;

    @Autowired
    private IBankRepository repository;

    @Autowired
    private DatabaseClient databaseClient;

    @Test
    void getAllTest() {
        var bank = new Bank();
        bank.setName("Test Bank");
        bank.setAddress("123 Test St");
        bank.setCreatedAt(LocalDateTime.now());

        repository.save(bank).block();

        StepVerifier.create(bankService.getAll())
                .assertNext(result -> {
                    Assertions.assertEquals(bank.getName(), result.getName());
                    Assertions.assertEquals(bank.getAddress(), result.getAddress());
                })
                .verifyComplete();
        databaseClient.sql("DELETE FROM banks").then().block();
    }

    @Test
    void getByIdTest() {
        var bank = new Bank();
        bank.setName("Test Bank");
        bank.setAddress("123 Test St");
        bank.setCreatedAt(LocalDateTime.now());

        var savedBank = repository.save(bank).block();

        StepVerifier.create(bankService.getById(savedBank.getId()))
                .assertNext(result -> {
                    Assertions.assertEquals(savedBank.getName(), result.getName());
                    Assertions.assertEquals(savedBank.getAddress(), result.getAddress());
                })
                .verifyComplete();
        databaseClient.sql("DELETE FROM banks").then().block();
    }

    @Test
    void getByIdNotFoundTest() {
        StepVerifier.create(bankService.getById(999L))
                .expectErrorMatches(throwable -> throwable instanceof BusinessException &&
                                                 throwable.getMessage().equals("Bank not found"))
                .verify();
    }

    @Test
    void createTest() {
        var bank = new Bank();
        bank.setName("New Bank");
        bank.setAddress("456 New St");

        StepVerifier.create(bankService.create(bank))
                .assertNext(result -> {
                    Assertions.assertNotNull(result.getId());
                    Assertions.assertEquals(bank.getName(), result.getName());
                    Assertions.assertEquals(bank.getAddress(), result.getAddress());
                })
                .verifyComplete();
        databaseClient.sql("DELETE FROM banks").then().block();
    }

    @Test
    void updateTest() {
        var bank = new Bank();
        bank.setName("Old Bank");
        bank.setAddress("789 Old St");
        bank.setCreatedAt(LocalDateTime.now());

        var savedBank = repository.save(bank).block();

        var updatedBank = new Bank();
        updatedBank.setName("Updated Bank");
        updatedBank.setAddress("123 Updated St");

        StepVerifier.create(bankService.update(savedBank.getId(), updatedBank))
                .assertNext(result -> {
                    Assertions.assertEquals("Updated Bank", result.getName());
                    Assertions.assertEquals("123 Updated St", result.getAddress());
                })
                .verifyComplete();
        databaseClient.sql("DELETE FROM banks").then().block();
    }

    @Test
    void updateNotFoundTest() {
        var bank = new Bank();
        bank.setName("Nonexistent Bank");
        bank.setAddress("000 Nowhere St");

        StepVerifier.create(bankService.update(999L, bank))
                .expectErrorMatches(throwable -> throwable instanceof BusinessException &&
                                                 throwable.getMessage().equals("Bank not found for update"))
                .verify();
        databaseClient.sql("DELETE FROM banks").then().block();
    }
}
