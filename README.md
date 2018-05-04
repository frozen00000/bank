# Bank service

## Description

This service manages money transfer between accounts.
There are two entities: Account and Transaction. Account has an id and current balance. Transaction has an id of itself, id of source account and id of target account. To make money transfer you need to create a transaction.

## API

Path              | Method | Description                   | Body                                                                                                                             | Response
----------------- | ------- | ---------------------------- | -------------------------------------------------------------------------------------------------------------------------------- | ---------------------
/account          | POST    | Creates new account.         | Should contain a JSON with only one field "balance" that is used as initial balance of new account. Example: `{"balance": 10}`   | Id of created account.
/account/{id}     | GET     | Retrieves account by id.     | Empty.                                                                                                                           | Account in JSON format.
/transaction      | POST    | Creates new transaction.     | Should contain a JSON with following fields: "sourceAccountId", "targetAccountId", "amount".                                     | Id of created transaction.
/transaction/{id} | GET     | Retrieves transaction by id. | Empty.                                                                                                                           | Transaction in JSON format.

## Build

Required Java version: `8`.

To build the project execute following command:

`mvn package`
