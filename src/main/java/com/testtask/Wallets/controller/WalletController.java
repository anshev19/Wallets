package com.testtask.Wallets.controller;

import com.testtask.Wallets.dto.WalletDto;
import com.testtask.Wallets.exception.WalletNotFoundException;
import com.testtask.Wallets.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Validated
public class WalletController {

    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @PostMapping("/wallet")
    public ResponseEntity<?> performOperation(@Valid @RequestBody WalletDto dto) throws WalletNotFoundException {
        service.performOperation(dto.getWalletId(), dto.getAmount(), dto.getOperationType());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<?> getWalletBalance(@PathVariable("id") UUID walletId) {
        Long balance = service.getWalletBalance(walletId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWallet() {
        UUID id = service.createAndSaveWallet();
        return ResponseEntity.ok(id);
    }
}
