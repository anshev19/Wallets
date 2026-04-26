package com.testtask.Wallets.service;

import com.testtask.Wallets.entity.WalletEntity;
import com.testtask.Wallets.enums.OperationType;
import com.testtask.Wallets.exception.InsufficientFundsException;
import com.testtask.Wallets.exception.WalletNotFoundException;
import com.testtask.Wallets.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final TransactionalService transactionalService;
    private final WalletRepository repository;

    public void performOperation(UUID walletId, long amount, OperationType operationType) throws WalletNotFoundException {
        WalletEntity wallet = getWalletEntityById(walletId);
        switch(operationType) {
            case DEPOSIT -> performDepositOperation(wallet, amount);
            case WITHDRAW -> performWithdrawOperation(wallet, amount);
            default -> throw new IllegalArgumentException(String.format("Unknown operation name: %s", operationType.getOperationName()));
        }
    }

    @Synchronized
    public Long getWalletBalance(UUID walletId) throws WalletNotFoundException {
        WalletEntity wallet = getWalletEntityById(walletId);
        return wallet.getBalance();
    }

    @Synchronized
    public UUID createAndSaveWallet() {
        WalletEntity newEntity = WalletEntity.builder()
                .balance(0L)
                .build();
        transactionalService.saveWallet(newEntity);
        return newEntity.getId();
    }

    @Synchronized
    private void performDepositOperation(WalletEntity wallet, long amount) {
        wallet.setBalance(wallet.getBalance() + amount);
        transactionalService.saveWallet(wallet);
    }

    @Synchronized
    private void performWithdrawOperation(WalletEntity wallet, long amount) {
        long newBalance = wallet.getBalance() - amount;
        if (newBalance < 0) {
            throw new InsufficientFundsException(
                    String.format("Wallet with walletId = %s has insufficient founds", wallet.getId())
            );
        }
        wallet.setBalance(newBalance);
        transactionalService.saveWallet(wallet);
    }

    @Synchronized
    private WalletEntity getWalletEntityById(UUID walletId) throws WalletNotFoundException {
        return repository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(String.format("Wallet with id = %s not found", walletId)));
    }
}
