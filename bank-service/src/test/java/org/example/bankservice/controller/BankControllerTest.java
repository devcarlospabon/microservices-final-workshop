package org.example.bankservice.controller;

import org.example.bankservice.model.Bank;
import org.example.bankservice.service.BankService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


class BankControllerTest {
    private BankService service;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(BankService.class);
        BankController controller = new BankController(service);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void getAllTest() {
        var bank = new Bank();
        var actualDate = LocalDateTime.now();
        bank.setId(1L);
        bank.setName("Test bank");
        bank.setAddress("123 Test St");
        bank.setCreatedAt(actualDate);
        Mockito.when(service.getAll())
                .thenReturn(Flux.just(bank));
        webTestClient.get()
                .uri("/api/banks")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Bank.class)
                .value(bankCreated -> {
                    Assertions.assertEquals(1L, bankCreated.get(0).getId());
                });

        Mockito.verify(service).getAll();

    }

    @Test
    void getByIdTest() {
        var bank = new Bank();
        var actualDate = LocalDateTime.now();
        bank.setId(1L);
        bank.setName("Test Bank");
        bank.setAddress("123 Test St");
        bank.setCreatedAt(actualDate);

        Mockito.when(service.getById(1L))
                .thenReturn(Mono.just(bank));

        webTestClient.get()
                .uri("/api/banks/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Bank.class)
                .value(result -> {
                    Assertions.assertEquals(bank.getId(), result.getId());
                    Assertions.assertEquals(bank.getName(), result.getName());
                    Assertions.assertEquals(bank.getAddress(), result.getAddress());
                    Assertions.assertEquals(bank.getCreatedAt(), result.getCreatedAt());
                });

        Mockito.verify(service).getById(1L);
    }

    @Test
    void createTest() {
        var bank = new Bank();
        var actualDate = LocalDateTime.now();
        bank.setId(1L);
        bank.setName("Test Bank");
        bank.setAddress("123 Test St");
        bank.setCreatedAt(actualDate);

        Mockito.when(service.create(Mockito.any()))
                .thenReturn(Mono.just(bank));

        webTestClient.post()
                .uri("/api/banks")
                .bodyValue(bank)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Bank.class)
                .value(result -> {
                    Assertions.assertEquals(bank.getId(), result.getId());
                    Assertions.assertEquals(bank.getName(), result.getName());
                    Assertions.assertEquals(bank.getAddress(), result.getAddress());
                    Assertions.assertEquals(bank.getCreatedAt(), result.getCreatedAt());
                });

        Mockito.verify(service).create(Mockito.any());
    }

    @Test
    void updateTest() {
        var bank = new Bank();
        var actualDate = LocalDateTime.now();
        bank.setId(1L);
        bank.setName("Updated Bank");
        bank.setAddress("456 Updated St");
        bank.setCreatedAt(actualDate);

        Mockito.when(service.update(Mockito.eq(1L), Mockito.any()))
                .thenReturn(Mono.just(bank));

        webTestClient.put()
                .uri("/api/banks/1")
                .bodyValue(bank)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Bank.class)
                .value(result -> {
                    Assertions.assertEquals(bank.getId(), result.getId());
                    Assertions.assertEquals(bank.getName(), result.getName());
                    Assertions.assertEquals(bank.getAddress(), result.getAddress());
                    Assertions.assertEquals(bank.getCreatedAt(), result.getCreatedAt());
                });

        Mockito.verify(service).update(Mockito.eq(1L), Mockito.any());
    }
}