package com.getontop.challenge.db.repository;

import com.getontop.challenge.db.entity.Transaction;
import org.springframework.data.repository.CrudRepository;


public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}