# Bank service
This service manages money transfer beetween accounts.
There are two entities: Account and Transaction. Account has an id and current balance. Transaction has an id of itself, id of source account and id of target account. To make moneny transfer you need to create a transaction.

Path                 | Method | Description                   | Body                                                                                                                             | Response
-------------------- | ------- | ---------------------------- | -------------------------------------------------------------------------------------------------------------------------------- | ---------------------
/v0/account          | POST    | Creates new account.         | Should contain a JSON with only one field "balance" that is used as initial balance of new account. Example: `{"balance": 10}`   | Id of created account.
/v0/account/{id}     | GET     | Retreives account by id.     | Empty.                                                                                                                           | Account in JSON format.
/v0/transaction      | POST    | Creates new transaction.     | Should contain a JSON with folowing fields: "sourceAccountId", "targetAccountId", "amount".                                      | Id of created transaction.
/v0/transaction/{id} | GET     | Retreives transaction by id. | Empty.                                                                                                                           | Transaction in JSON format.
