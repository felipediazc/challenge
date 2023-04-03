package com.getontop.challenge.db.repository;

import com.getontop.challenge.db.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
}