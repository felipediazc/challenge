# ABOUT THE TECHNICAL TEST

Please review the documentation contained in the "Ontop’s Challenge for backend with Java (English)" PDF file

# SOLUTION DESIGN

There are two actors identified in this code challenge: USERr who is the client of ONTOP and ONTOP who also has shares in the software  

##USER use cases

![Class Diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/felipediazc/UML-in-Markdown-Public/main/UML/ONTOP_Challenge_usecase1.puml)

**Add bank account:** the User add its bank account details in order to do money transfers with ONTOP. This data must be stred in some place

**Transfer money:** The user transfers money from his wallet to his bank account. This operation has to consult if there are funds for transfer in the user's wallet (it uses an external webservice for this purpose), then it uses an external web service for money transfer and finales uses an other web service to update wallet balance. Not all bank transactions are sucessfully, so there must be an trasfer status for comision discount or for rever the transaction  

**Consult bank data:** This is used by the "Transfer money case", the idea is not to ask all bank data every time a transaction is going to be made. 

**View transactions:** All transactions must be stored in some place for reporting purposes

##ONTOP use cases

![Class Diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/felipediazc/UML-in-Markdown-Public/main/UML/ONTOP_Challenge_usecase2.puml)

**Debit 10% per transaction:** When a transaction is done, ONTOP gets 10% of it. These are the posible scenarios:
* A transaction is done with status "Processing". the comision is billed but it stays with IN_PROGRESS status. The transaction information is saved for tracking purposes  
* A transaction is done with status "Success". the comision is billed with COMPLETED status. The transaction information is saved for tracking purposes.
* A transaction is failed. No comision is billed. The transaction information is saved for tracking purposes

**Update transaction status:** The system looks for pending transactions (status = IN_PROGRESS) and uses a webservice to consult the transaction status. These are the posible scenarios:
* The transaction change its status to "Success", then the ONTOP transaction record must be updated to COMPLETED
* The transaction change its status to "Failed", then the ONTOP transaction record must be updated to FAILED and the transaction must be reverted (money returns to the user wallet without comision billed)

**Update USER wallet balance** Update this data accordinf the sucessfull, pending, failed and reverted transactions

##Hexagonal architecture

We have identified 3 domains: Payments, transactions and wallets
the domains interact with different data providers:
1. A mock which works as an endpoint to all related with the user wallet and data transfers
2. A data store provider whose type is unknown (NoSQL, SQL, etc) and we also don't know the design of the data model.

Bearing in mind that the mock could be changed to a real solution and that the data store could be anything, the best aproach is to use the port - adapter pattern.

##Wallet 

```mermaid
classDiagram
Wallet --> OnTopData
Wallet --> WalletController
WalletController <|-- WalletControllerImpl
OnTopData <|-- OnTopDataImpl
class Wallet {
  <<domain>>
}
class OnTopData {
  <<port>>
  Interface
}  
class WalletController {
  <<port>>
  Interface
}
class WalletControllerImpl {
  <<adapter>>
  RestController for enable and endpoint
  to save and recover bank account data  
}
class OnTopDataImpl {
  <<adapter>>
  Implementation with database storage
  to keep user wallet and bank account data
}
```

##Payment 

```mermaid
classDiagram
Payment --> OnTopData
Payment --> ExternalEndpointIntegration
Payment --> PaymentController
PaymentController <|-- PaymentControllerImpl
ExternalEndpointIntegration <|-- OnToEndpointMockIntegrationImpl
OnTopData <|-- OnTopDataImpl
class Payment {
  <<domain>>
}
class OnTopData {
  <<port>>
  Interface
}  
class ExternalEndpointIntegration {
  <<port>>
  Interface
}
class PaymentController {
  <<port>>
  Interface
}
class PaymentControllerImpl {
  <<adapter>>
  RestController to serve as endpoint
  for bank transactions
}
class OnToEndpointMockIntegrationImpl {
  <<adapter>>
  Connection to and external endpoint
}
class OnTopDataImpl {
  <<adapter>>
  Implementation with database storage
  to keep user transactions data
}
```
##Transaction 

```mermaid
classDiagram
Transaction --> OnTopData
Transaction --> TransactionController
TransactionController <|-- TransactionControllerImpl
OnTopData <|-- OnTopDataImpl
class Transaction {
  <<domain>>
}
class OnTopData {
  <<port>>
  Interface
}  
class TransactionController {
  <<port>>
  Interface
}
class TransactionControllerImpl {
  <<adapter>>
  RestController to serve as endpoint
  for get transactions history
}
class OnTopDataImpl {
  <<adapter>>
  Implementation with database storage
  to keep user transactions data
}
```

##Payment flowchart

The payment operation is the most complex of this entire project, since it interacts with several points, it must calculate the commission and it must also be persisted in the database.

```mermaid
graph LR
A[User] -->|sends accountId,\n walletId, amount,\n currency|B(Payment domain)
    B --> C{accountId exist}
    C -->|Yes| D{walletId exist}
    C -->|No| E[Error - exit]
    D -->|Yes| F{there is balance\n for debit}
    D -->|No| E[Error - exit]
    F -->|No| E[Error - exit]
    F -->|Yes| G[Do Payment]
    G -->H[Update wallet]
```



##Database model

```mermaid
erDiagram
    ACCOUNTS ||--o{ TRANSACTIONS : could_have
    ACCOUNTS {
      int id
      string name
      string routingnumber
      string accountnumber
    }
    ACCOUNTS ||--o{ WALLETS : could_have
    WALLETS {
      int id
      string name
      string lastname
      string routingnumber
      string nationalidnumber
      string accountnumber
      string bankname
      int accountid
    }
    WALLETS ||--o{ TRANSACTIONS : could_have
    TRANSACTIONS {
      int id
      int accountid
      int walletid
      string description
      double amount
      string peertransactionid
      string localtransactionid      
      timestamp transactiondate
      string status
    }
    TRANSACTIONS ||--|{ ONTOPCOMMISSIONS : has
    ONTOPCOMMISSIONS {
      int id
      int transactionid
      timestamp commissiondate
      double comission
      double amount
      string peertransactionid
      string localtransactionid      
      string status
    }
```