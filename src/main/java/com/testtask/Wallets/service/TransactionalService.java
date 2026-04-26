package com.testtask.Wallets.service;

import com.testtask.Wallets.entity.WalletEntity;
import com.testtask.Wallets.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionalService {
    private final WalletRepository repository;

    @Transactional
    public void saveWallet(WalletEntity entity) {
        repository.save(entity);
    }
}
