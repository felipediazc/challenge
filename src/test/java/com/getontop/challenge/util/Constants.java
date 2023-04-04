package com.getontop.challenge.util;

import com.getontop.challenge.db.entity.Account;
import com.getontop.challenge.db.entity.Transaction;
import com.getontop.challenge.db.entity.Wallet;
import com.getontop.challenge.dto.BalanceResponseDto;
import com.getontop.challenge.dto.WalletResponseDto;

import java.time.Instant;

public class Constants {
    public static final String PAYMENT_SUCCESS_BODY = """
            {
                "source": {
                    "type": "COMPANY",
                    "sourceInformation": {
                        "name": "ONTOP INC"
                    },
                    "account": {
                        "accountNumber": "0245253419",
                        "currency": "USD",
                        "routingNumber": "028444018"
                    }
                },
                "destination": {
                    "name": "TONY STARK",
                    "account": {
                        "accountNumber": "1885226711",
                        "currency": "USD",
                        "routingNumber": "211927207"
                    }
                },
                "amount": 1000
            }
            """;

    public static final String PAYMENT_INVALID_BODY = """
            {
                "source": {
                    "type": "COMPANY",
                    "sourceInformation": {
                        "name": "ONTOP INC"
                    },
                    "account": {
                        "accountNumber": "0245253419",
                        "currency": "USD",
                        "routingNumber": "028444018"
                    }
                },
                "destination": {
                    "name": "TONY STARK",
                    "account": {
                        "currency": "USD",
                        "routingNumber": "211927207"
                    }
                },
                "amount": 1000
            }
            """;

    public static final String PAYMENT_REJECTED_BODY = """
            {
                "source": {
                    "type": "COMPANY",
                    "sourceInformation": {
                        "name": "ONTOP INC"
                    },
                    "account": {
                        "accountNumber": "0245253419",
                        "currency": "USD",
                        "routingNumber": "028444018"
                    }
                },
                "destination": {
                    "name": "JAMES FAILED",
                    "account": {
                        "accountNumber": "1885226711",
                        "currency": "USD",
                        "routingNumber": "211927207"
                    }
                },
                "amount": 1000
            }
            """;

    public static final String PAYMENT_TIMEOUT_BODY = """
            {
                "source": {
                    "type": "COMPANY",
                    "sourceInformation": {
                        "name": "ONTOP INC"
                    },
                    "account": {
                        "accountNumber": "0245253419",
                        "currency": "USD",
                        "routingNumber": "028444018"
                    }
                },
                "destination": {
                    "name": "JAMES TIMEOUT",
                    "account": {
                        "accountNumber": "1885226711",
                        "currency": "USD",
                        "routingNumber": "211927207"
                    }
                },
                "amount": 1000
            }
            """;

    public static final String PAYMENT_SUCCESS_FULL_TEST_RESPONSE = """
            {"source":{"type":"COMPANY","sourceInformation":{"name":"ONTOP INC"},"account":{"accountNumber":"0245253419","currency":"USD","routingNumber":"028444018"}},"destination":{"name":"TONY STARK","account":{"accountNumber":"1885226711","currency":"USD","routingNumber":"211927207"}},"amount":20.0}
                    """;
    public static final String PAYMENT_SUCCESS_RESPONSE = """
            {
                "requestInfo": {
                    "status": "Processing"
                },
                "paymentInfo": {
                    "amount": 1000,
                    "id": "70cfe468-91b9-4e04-8910-5e8257dfadfa"
                }
            }
            """;

    public static final String PAYMENT_REJECTED_RESPONSE = """
            {
                "requestInfo": {
                    "status": "Failed",
                    "error": "bank rejected payment"
                },
                "paymentInfo": {
                    "amount": 1000,
                    "id": "7633f4c9-51e4-4b62-97b0-51156966f1d7"
                }
            }
            """;

    public static final String PAYMENT_INVALID_BODY_RESPONSE = """
            {
                "error": "body is invalid, check postman collection example"
            }
            """;
    public static final String PAYMENT_TIMEOUT_RESPONSE = """
            {
                "requestInfo": {
                    "status": "Failed",
                    "error": "timeout"
                },
                "paymentInfo": {
                    "amount": 1000,
                    "id": "3656ee8e-caff-4f2b-9ed3-2ba9fb938fb7"
                }
            }
            """;

    public static final String WALLET_GOOD_PAYLOAD = """
            {
                "amount": -20,
                "user_id": 1
            }
            """;

    public static final String WALLET_INCOMPLETE_PAYLOAD = """
            {
                "amount": 2000
            }
            """;

    public static final String WALLET_INVALID_USER_PAYLOAD = """
            {
                "amount": 2000,
                "user_id": 404
            }
            """;
    public static final String WALLET_GENERIC_ERROR_PAYLOAD = """
            {
                "amount": 2000,
                "user_id": 500
            }
            """;

    public static final String BALANCE_RESPONSE = """
            {
                "balance": 2500,
                "user_id": 1000
            }
            """;

    public static final String WALLET_SUCCESS_RESPONSE = """
            {
                "wallet_transaction_id": 75040,
                "amount": 2000,
                "user_id": 1000
            }
            """;

    public static final String WALLET_INVALID_BODY_RESPONSE = """
            {
                "code": "INVALID_BODY",
                "message": "amount and user_id must not be null"
            }
            """;

    public static final String WALLET_GENERIC_ERROR_RESPONSE = """
            {
                "code": "GENERIC_ERROR",
                "message": "something bad happened"
            }
            """;
    public static Account setAccount(Integer id, String name, String accountNumber,
                                     String routingNumber) {
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setAccountnumber(accountNumber);
        account.setRoutingnumber(routingNumber);
        return account;
    }


    public static Wallet setWallet(Integer id, String name, String lastname, String accountNumber,
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
        return wallet;
    }

    public static Transaction setTransaction(Account account, Wallet wallet) {
        Transaction transaction = new Transaction();
        transaction.setAccountid(account);
        transaction.setAmount(20.0);
        transaction.setStatus("DONE");
        transaction.setDescription("no description");
        transaction.setPeertransactionid("PTid");
        transaction.setLocaltransactionid("LTid");
        transaction.setTransactiondate(Instant.now());
        transaction.setWalletid(wallet);
        transaction.setId(1);
        return transaction;
    }

    public static BalanceResponseDto getPositiveBalanceDto() {
        return new BalanceResponseDto(100.0, 1);
    }

    public static WalletResponseDto getPositiveWalletResponse() {
        WalletResponseDto walletResponseDto = new WalletResponseDto();
        walletResponseDto.setWalletId(1);
        walletResponseDto.setWalletTransactionId(1);
        walletResponseDto.setAmount(20.0);
        return walletResponseDto;
    }
}
