package org.example.bankservice.service;

import org.example.bankservice.exception.BusinessException;
import org.example.bankservice.model.Bank;
import org.example.bankservice.repository.IBankRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;


@ExtendWith(MockitoExtension.class)
class BankServiceTest {

    @Mock
    private IBankRepository repository;

    @InjectMocks
    private BankService service;

    @Test
    void getAllTest() {
        var bank = new Bank();
        var actualDate = LocalDateTime.now();
        bank.setId(1L);
        bank.setName("Test bank");
        bank.setAddress("123 Test St");
        bank.setCreatedAt(actualDate);

        Mockito.when(repository.findAll())
                .thenReturn(Flux.just(bank));
        StepVerifier.create(service.getAll())
                .assertNext(result -> {
                    Assertions.assertEquals(bank.getName(), result.getName());
                    Assertions.assertEquals(bank.getAddress(), result.getAddress());
                    Assertions.assertEquals(bank.getId(), result.getId());
                    Assertions.assertEquals(actualDate, result.getCreatedAt());
                })
                .verifyComplete();
        Mockito.verify(repository).findAll();
    }

    @Test
    void getByIdTest() {
        var bank = new Bank();
        var actualDate = LocalDateTime.now();
        bank.setId(1L);
        bank.setName("Test bank");
        bank.setAddress("123 Test St");
        bank.setCreatedAt(actualDate);

        Mockito.when(repository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(bank));
        StepVerifier.create(service.getById(1L))
                .assertNext(result -> {
                    Assertions.assertEquals(bank.getName(), result.getName());
                    Assertions.assertEquals(bank.getAddress(), result.getAddress());
                    Assertions.assertEquals(bank.getId(), result.getId());
                    Assertions.assertEquals(actualDate, result.getCreatedAt());
                })
                .verifyComplete();
        Mockito.verify(repository).findById(Mockito.anyLong());
    }

    @Test
    void getByIdErrorTest() {
        var bank = new Bank();
        var actualDate = LocalDateTime.now();
        bank.setId(1L);
        bank.setName("Test bank");
        bank.setAddress("123 Test St");
        bank.setCreatedAt(actualDate);

        Mockito.when(repository.findById(Mockito.anyLong()))
                .thenReturn(Mono.empty());
        StepVerifier.create(service.getById(1L))
                .expectError(BusinessException.class)
                .verify();

        Mockito.verify(repository).findById(Mockito.anyLong());
    }

    @Test
    void createTest() {
        var bank = new Bank();
        var actualDate = LocalDateTime.now();
        bank.setId(1L);
        bank.setName("Test bank");
        bank.setAddress("123 Test St");
        bank.setCreatedAt(actualDate);
        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(Mono.just(bank));
        StepVerifier.create(service.create(bank))
                .assertNext(result -> {
                    Assertions.assertEquals(bank.getName(), result.getName());
                    Assertions.assertEquals(bank.getAddress(), result.getAddress());
                    Assertions.assertEquals(bank.getId(), result.getId());
                    Assertions.assertEquals(actualDate, result.getCreatedAt());
                })
                .verifyComplete();
        Mockito.verify(repository).save(Mockito.any());

    }

    @Test
    void updateTest() {
        var bank = new Bank();
        var actualDate = LocalDateTime.now();
        bank.setId(1L);
        bank.setName("Test bank");
        bank.setAddress("123 Test St");
        bank.setCreatedAt(actualDate);
        Mockito.when(repository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(bank));
        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(Mono.just(bank));

        StepVerifier.create(service.update(1L, bank))
                .assertNext(result -> {
                    Assertions.assertEquals(bank.getName(), result.getName());
                    Assertions.assertEquals(bank.getAddress(), result.getAddress());
                    Assertions.assertEquals(bank.getId(), result.getId());
                    Assertions.assertEquals(actualDate, result.getCreatedAt());
                })
                .verifyComplete();
        Mockito.verify(repository).findById(Mockito.anyLong());
        Mockito.verify(repository).save(Mockito.any());

    }

    @Test
    void updateErrorTest() {
        var bank = new Bank();
        var actualDate = LocalDateTime.now();
        bank.setId(1L);
        bank.setName("Test bank");
        bank.setAddress("123 Test St");
        bank.setCreatedAt(actualDate);
        Mockito.when(repository.findById(Mockito.anyLong()))
                .thenReturn(Mono.empty());


        StepVerifier.create(service.update(1L, bank))
                .expectError(BusinessException.class)
                .verify();
        Mockito.verify(repository).findById(Mockito.anyLong());

    }
}