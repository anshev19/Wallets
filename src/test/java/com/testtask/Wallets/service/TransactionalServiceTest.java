package com.testtask.Wallets.service;

import com.testtask.Wallets.entity.WalletEntity;
import com.testtask.Wallets.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionalServiceTest {
    @Mock
    WalletRepository repository;

    @InjectMocks
    TransactionalService service;

    @Test
    void saveWallet() {
        var wallet = WalletEntity.builder()
                .id(UUID.randomUUID())
                .balance(0L)
                .build();
        service.saveWallet(wallet);
        verify(repository).save(wallet);
    }
}
