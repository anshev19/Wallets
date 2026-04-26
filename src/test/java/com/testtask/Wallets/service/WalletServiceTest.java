package com.testtask.Wallets.service;

import com.testtask.Wallets.entity.WalletEntity;
import com.testtask.Wallets.exception.InsufficientFundsException;
import com.testtask.Wallets.enums.OperationType;
import com.testtask.Wallets.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    WalletRepository repository;

    @Mock
    TransactionalService transactionalService;

    @InjectMocks
    WalletService walletService;

    private WalletEntity wallet;

    private UUID walletId;

    @BeforeEach
    void setup() {
        walletId = UUID.randomUUID();
        wallet = WalletEntity.builder()
                .id(walletId)
                .balance(0L)
                .build();
    }

    @Test
    void PerformDepositOperationTest() {
        when(repository.findById(walletId)).thenReturn(Optional.ofNullable(wallet));
        walletService.performOperation(wallet.getId(), 1000, OperationType.DEPOSIT);
        assertEquals(1000, wallet.getBalance(), "Значения баланса не совпадают");
    }

    @Test
    void PerformWithdrawOperationTest() {
        when(repository.findById(walletId)).thenReturn(Optional.ofNullable(wallet));
        wallet.setBalance(1000L);
        walletService.performOperation(wallet.getId(), 1000L, OperationType.WITHDRAW);
        assertEquals(0, wallet.getBalance(), "Значения баланса не совпадают");
    }

    @Test
    void insufficientFoundExceptionTest() {
        when(repository.findById(walletId)).thenReturn(Optional.ofNullable(wallet));
        assertThrows(InsufficientFundsException.class, () -> walletService.performOperation(wallet.getId(), 1000L, OperationType.WITHDRAW));
    }

    @Test
    void getBalanceTest() {
        when(repository.findById(walletId)).thenReturn(Optional.ofNullable(wallet));
        wallet.setBalance(1000L);
        Long balance = walletService.getWalletBalance(wallet.getId());
        assertEquals(1000L, balance);
    }

    @Test
    void saveWalletTest() {
        walletService.createAndSaveWallet();
        verify(transactionalService).saveWallet(any());
    }
}
