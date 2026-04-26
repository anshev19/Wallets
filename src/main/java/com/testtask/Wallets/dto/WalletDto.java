package com.testtask.Wallets.dto;

import com.testtask.Wallets.enums.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WalletDto {

    @NotNull(message = "Отсутствует walletId или указано некорректное значение")
    private UUID walletId;

    @NotNull(message = "Отсутствует имя операции или указано некорректное значение")
    OperationType operationType;

    @Positive(message = "Значение должно быть положительным")
    private Long amount;
}
