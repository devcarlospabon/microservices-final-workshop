package org.example.transactionservice.service;

import lombok.RequiredArgsConstructor;
import org.example.transactionservice.dto.GetAccountDto;
import org.example.transactionservice.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final WebClient.Builder webClientBuilder;
    @Value("${account-service.url}")
    private String accountServiceUrl;

    public Mono<GetAccountDto> getInfoAccountByAccountId(Long accountId) {
        return webClientBuilder.build()
                .get()
                .uri(accountServiceUrl + "/{id}", accountId)
                .retrieve()
                .bodyToMono(GetAccountDto.class)
                .onErrorResume(e -> Mono.error(new BusinessException("Account not found with ID: " + accountId)));
    }

    public Mono<GetAccountDto> updateBalance(Long accountId, GetAccountDto accountDto) {
        return webClientBuilder.build()
                .put()
                .uri(accountServiceUrl+"/{id}", accountId)
                .body(Mono.just(accountDto), GetAccountDto.class)
                .retrieve()
                .bodyToMono(GetAccountDto.class)
                .onErrorResume(e -> Mono.error(new BusinessException("Error updating account balance for ID: " + accountId)));
    }


}
