## REST API MoneyTransfer

A standalone application rest api for money transfer between accounts, no server/dataBase is nescessary to run and test.

---

## Requeriments

1. Java 8.
2. MAVEN.

---

## How to test

1. {project path} mvn clean verify

---

## How to run

1. {project path} mvn clean package.
2. {project path}/traget java -jar money.transfer-jar-with-dependencies.jar.
3. Server link http://localhost:4567.

---

## Request

**Create Account:**

amount (Decimal [19,2]) = Total amount to be included in a new account.

**Transfer between Accounts:**

accountDebit (alphanumeric) = This is the account that will be debited the value of the transfer.

accountCredit (alphanumeric) = This is the account that will be credited the value of the transfer.

value (Decimal [19,2]) = This is the value of the transfer.

---

## EndPoints

**Create account**

endpoin: /account

method: post

paylod: 

    {
     "amount": 2000.65
    }

return:201

    {
     "id": "4b6121be-35fd-452a-b509-6fa895a63cc8",
     "accountNumber": "2453",
     "amount": 2000.65
    }

**Get all accounts**

endpoin: /accounts

method: get

return: 200  

	{
      "id": "4b6121be-35fd-452a-b509-6fa895a63cc8",
      "accountNumber": "2453",
      "amount": 2000.6
    }    
	{    
	  "id": "4b6121be-35fd-452a-b509-6fa895a63cc8",
      "accountNumber": "2453",
      "amount": 2000.6        
	}    
   

**Money Transfer**

endpoin: /transfer

method: post

paylod:

    {
     "accountDebit": "5823", 
     "accountCredit": "4963", 
     "value": 122
    }

return: 200 (accountDebit return)

    {
    "id": "85dc54e2-6b96-4049-b923-3980415adff7",
    "accountNumber": "5823",
    "amount": 1756.6
    }

**Delete all accounts**

endpoin: /accounts/delete

method: post

return:
    
     {
    	"httpStatusCode": 204,
   	"message": "No Content - deleted 2 account(s)"
     }
