package org.example.transactionservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.transactionservice.model.Transaction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ExternalTransferDto {
    @JsonDeserialize
    Transaction transaction;
    @JsonDeserialize
    GetAccountDto account;
}
