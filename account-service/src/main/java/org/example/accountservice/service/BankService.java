package org.example.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.example.accountservice.dto.GetBankDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BankService {
    private final WebClient.Builder webClientBuilder;
    @Value("${bank-service.url}")
    private String bankServiceUrl;

    public Mono<Boolean> isBankExists(Long bankId) {
        return webClientBuilder.build()
                .get()
                .uri(bankServiceUrl + "/{id}", bankId)
                .retrieve()
                .bodyToMono(GetBankDto.class)
                .map(response -> true)
                .onErrorReturn(false);
    }

}
