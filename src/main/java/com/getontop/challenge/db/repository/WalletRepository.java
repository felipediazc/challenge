package com.getontop.challenge.db.repository;

import com.getontop.challenge.db.entity.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, Integer> {
}