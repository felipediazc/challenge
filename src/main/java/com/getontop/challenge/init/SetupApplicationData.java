package com.getontop.challenge.init;

import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Accountdestination;
import com.getontop.challenge.domain.AccountService;
import com.getontop.challenge.domain.AccountdestinationService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SetupApplicationData {

    private final AccountService accountService;
    private final AccountdestinationService accountdestinationService;

    public SetupApplicationData(AccountService accountService, AccountdestinationService accountdestinationService) {

        this.accountService = accountService;
        this.accountdestinationService = accountdestinationService;
    }

    @PostConstruct
    public void init() {
        log.info("************************** SETTING INITIAL DATA ***********************");
        getAccount(1, "Felipe", "Diaz", "0245253419",
                "028444018", "94453827");
        getAccountDestination(1, "TONY", "STARK", "1885226711",
                "211927207", "1111111111", 1);
        getAccountDestination(2, "TONY", "STARK", null,
                "211927207", "1111111111", 1);
        getAccountDestination(3, "JAMES", "FAILED", "1885226711",
                "211927207", "1111111112", 1);
        getAccountDestination(4, "JAMES", "TIMEOUT", "1885226711",
                "211927207", "1111111113", 1);
    }

    private Account getAccount(Integer id, String name, String lastname, String accountNumber,
                               String routingNumber, String nationalNumber) {
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setLastname(lastname);
        account.setAccountnumber(accountNumber);
        account.setRoutingnumber(routingNumber);
        account.setNationalnumber(nationalNumber);
        return accountService.save(account);
    }

    private Accountdestination getAccountDestination(Integer id, String name, String lastname, String accountNumber,
                                                     String routingNumber, String nationalNumber, Integer accountId) {
        Accountdestination account = new Accountdestination();
        account.setId(id);
        account.setName(name);
        account.setLastname(lastname);
        account.setAccountnumber(accountNumber);
        account.setRoutingnumber(routingNumber);
        account.setNationalnumber(nationalNumber);
        account.setAccountid(accountId);
        return accountdestinationService.save(account);
    }
}
