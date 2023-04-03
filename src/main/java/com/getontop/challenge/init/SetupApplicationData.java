package com.getontop.challenge.init;

import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.domain.AccountService;
import com.getontop.challenge.domain.WalletService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SetupApplicationData {

    private final AccountService accountService;
    private final WalletService walletService;

    public SetupApplicationData(AccountService accountService, WalletService walletService) {

        this.accountService = accountService;
        this.walletService = walletService;
    }

    @PostConstruct
    public void init() {
        log.info("************************** SETTING INITIAL DATA ***********************");
        Account account = setAccount(1, "ONTOP INC", "0245253419",
                "028444018");
        setWallet(1, "TONY", "STARK", "1885226711",
                "211927207", "1111111111", account, "BANK1");
        setWallet(2, "TONY", "STARK", null,
                "211927207", "1111111111", account, "BANK1");
        setWallet(3, "JAMES", "FAILED", "1885226711",
                "211927207", "1111111112", account, "BANK1");
        setWallet(4, "JAMES", "TIMEOUT", "1885226711",
                "211927207", "1111111113", account, "BANK1");
    }

    private Account setAccount(Integer id, String name, String accountNumber,
                               String routingNumber) {
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setAccountnumber(accountNumber);
        account.setRoutingnumber(routingNumber);
        return accountService.save(account);
    }

    private void setWallet(Integer id, String name, String lastname, String accountNumber,
                                         String routingNumber, String nationalNumber, Account account,
                                         String bankName) {
        Wallet wallet = new Wallet();
        wallet.setId(id);
        wallet.setName(name);
        wallet.setLastname(lastname);
        wallet.setAccountnumber(accountNumber);
        wallet.setRoutingnumber(routingNumber);
        wallet.setNationalidnumber(nationalNumber);
        wallet.setAccountid(account);
        wallet.setBankname(bankName);
        walletService.save(wallet);
    }
}
