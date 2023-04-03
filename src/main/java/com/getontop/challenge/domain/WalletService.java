package com.getontop.challenge.domain;

import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.db.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Optional<Wallet> getWalletById(Integer walletId) {
        return walletRepository.findById(walletId);
    }

    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }
}
