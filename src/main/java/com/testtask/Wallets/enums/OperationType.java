package com.testtask.Wallets.enums;

import lombok.Getter;

@Getter
public enum OperationType {
    DEPOSIT("DEPOSIT"),
    WITHDRAW("WITHDRAW");

    private final String operationName;

    OperationType(String operationName) {
        this.operationName = operationName;
    }
}
