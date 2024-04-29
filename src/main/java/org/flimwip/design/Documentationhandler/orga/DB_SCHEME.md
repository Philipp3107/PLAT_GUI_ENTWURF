# Database Schema

List of all Schemes needed for the Database

## Users

- ID: Int -> PrimaryKey
- Username: Text
- Password: Text
- Vorname: Text
- Nachname: Text
- ProfilePicture: BLOB?

## NL Stats

- ID: Int -> PrimaryKey
- Checkout_ID: Int -> Checkout
- Standort_ID: Int -> Standort
- Timestamp: Timestamp
- warning: int
- error: int
- critical: int
-

## PosUsers
- ID: Int -> PrimaryKey
- Username: Text (hash)
- Description: Text
- Password: Text (hash)
-

## Checkouts

- ID: Int -> PrimaryKey
- Standort_ID: Int -> Standort
- Version: Text
- 

## NL Standorte

- ID: Int -> PrimaryKey
- Name: Text
- Kassenanzahl: Int
- Bundesland: Text
- 

## JobHistoryItems

- ID: Int -> PrimaryKey
- User_ID: Int -> User
- Timestamp: Timestamp
- Checkouts: Longteyt -> Checkout_ID's
- Files: Longtext -> "[{File: file, Type: .exe}, {...}, ...]"

## Downloaded Files

