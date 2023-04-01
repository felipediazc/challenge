package com.getontop.challenge.util;

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
}
