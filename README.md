# Code Challenge: Authorizer 

You are tasked with implementing an application that authorizes a transaction for a specific account following a set of predefined rules.

## Installation

1. To run the application you need, the [Java virtual machine](https://www.java.com/es/download/) (JVM), version 1.8 or higher. 
2. If the JAR file is to be run from the command line, change to the directory where the file is and run it. Once inside it, execute "java -jar Nubanck\out\artifacts\Nubanck_jar\Nubanck.jar". This is equivalent to double-clicking the file inside of a graphic environment.


```bash
'java -jar Nubanck\out\artifacts\Nubanck_jarNubanck.jar'
```

## Documentation
**Functional Requirements**

The challenge aims at meeting the following functional requirements:

*Account creation* 

- Input. Creates the account with ​available-limit​ and ​active-card​ set. 

- Output. The created account's current state + all business logic violations.

- Business rules. Once created, the account should not be updated or recreated: account-already-initialized

*Transaction authorization*

- Input. tries to authorize a transaction for a particular ​merchant​, ​amount​ and ​time​ given the account's state and last authorized transactions.

- Output. The account's current state + any business logic violations.

- Business rules. No transaction should be accepted without a properly initialized account: account-not-initialized. No transaction should be accepted when the card is not active: ​card-not-active. The transaction amount should not exceed available limit: ​insufficient-limit. There should not be more than 3 transactions on a 2-minute-interval: high-frequency-small-interval. There should not be more than 1 similar transactions in a 2-minute-interval: ​doubled-transaction

**Architecture**

For the design of the application some principles of clean architecture were considered. So, three layers were established.

*Entity.* Is used to receive data from the user. The entities created are: 
- Account
- InputAccount
- OutputAccount
- Transaction
- InputTransaction

*Application.* In this layer are the use cases, which represent the business rules of the application.
These use cases follow the flow to ensure that the defined rules are enforced; they only define how the system behaves, defining the necessary input data, and what will be its output. The classes created are: 
- AuthorizeTransaction
- CreateAccount
- BusinessRules

*Infrastructure.* This layer is in charge of receiving the input data (json) from the user and sending it to the use cases to execute the business rules; and once they receive the output data, they send it to the console in the format expected by the user. The classes created in this layer are: 
- ControllerData
- Wiew (main class)


**Running the Tests**

The unit tests were carried out to verify if the business rules developed in the use cases produced the expected results. The classes and methods created within the test.application package are the following:

*CreateAccountTest* 

- account_already_initialized_when_account_recreated 

*TestAuthorizeTransaction*
- account_not_initialized_when_account_is_null
- card_not_active_when_account_is_not_active
- insufficient_limit_when_amount_transaction_greater_available_limit_account
- high_frequency_small_interval_when_3_transaction_occurred_in_2_minutes

**Coding**

The Programming Language used for the development of the application was Java, under the object-oriented paradigm. In addition, the libraries were used:
- gson-2.8.2 to transform a character string in json format to a java class and vice versa.
- junit-4.12 and hamcrest-core-1.3, to perform the unit tests 

## Authors

Ing. Andreina Díaz - andreinadc@gmail.com
