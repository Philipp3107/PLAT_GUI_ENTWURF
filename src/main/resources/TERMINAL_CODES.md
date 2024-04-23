# Terminal Codes

Numeration of all the Terminal codes and their meaning

- **last edited 22-04-2024 - Page:24**

# Table of Contents
- [04 01 - Set Date and Time in ECR](#04-01---set-date-and-time-in-ecr)
- [04 0D - Input-Request](#04-0d---input-request)
- [04 0E - Menu-Request](#04-0e---menu-request)
- [04 0F - Status-Information](#04-0f---status-information)
- [04 FF - Intermediate Status Information ](#04-ff---intermediate-status-information-)
- [05 01 - Status-Enquiry](#05-01---status-enquiry)
- [06 00 - Registration](#06-00---registration)
  - [Transmitted Data](#06-00---transmitted-data)
- [06 01 - Authorization](#06-01---authorization)
- [06 02 - Log off](#06-02---log-off)
- [06 03 - ABR (Account Balance Request)](#06-03---abr-account-balance-request)
- [06 04 - Activate Card](#06-04---activate-card)
- [06 05 - Procurement](#06-05---procurement)
- [06 09 - Top-Up Prepaid-Cards](#06-09---top-up-prepaid-cards)
- [06 0A - Tax Free](#06-0a---tax-free)
- [06 0C - Book Tip](#06-0c---book-tip)
- [06 0F - Completion](#06-0f---completion)
- [06 10 - Send Turnover Totals](#06-10---send-turnover-totals)
- [06 12 - Reprint Receipts](#06-12---reprint-receipts)
- [06 18 - Reset Terminal](#06-18---reset-terminal)
- [06 1A - Print System Configuartion](#06-1a---print-system-configuartion)
- [06 1B - Set/Reset Terminal-ID](#06-1b---setreset-terminal-id)
- [06 1E - Abort](#06-1e---abort)
- [06 20 - Repeat Receipt](#06-20---repeat-receipt)
- [06 21 - Telephonic Authorisation](#06-21---telephonic-authorisation)
- [06 22 - Pre-Authorisation / Reservation](#06-22---pre-authorisation--reservation)
- [06 23 - Partial-Reversal of a Pre-Authorisation / Booking of a Reservation](#06-23---partial-reversal-of-a-pre-authorisation--booking-of-a-reservation)
- [06 24 - Book Total](#06-24---book-total)
- [06 25 - Pre-Autorisation Reversal](#06-25---pre-autorisation-reversal)
- [06 30 - Reversal](#06-30---reversal)
- [06 31 - Refund](#06-31---refund)
- [06 50 - End-of-Day](#06-50---end-of-day)
- [06 51 - Send offline Transactions](#06-51---send-offline-transactions)
- [06 70 - Diagnosis](#06-70---diagnosis)
- [06 79 - Selftest](#06-79---selftest)
- [06 85 - Display Text (old version)](#06-85---display-text-old-version)
- [06 86 - Display Text with Numerical Input (old version)](#06-86---display-text-with-numerical-input-old-version)
- [06 87 - PIN-Verification for Customer-Card (old version)](#06-87---pin-verification-for-customer-card-old-version)
- [06 88 - Dipsplay Text with Function-Key Input (Old version)](#06-88---dipsplay-text-with-function-key-input-old-version)
- [06 91 - Set Date and Time in PT](#06-91---set-date-and-time-in-pt)
- [06 93 - Initialisation](#06-93---initialisation)
- [06 95 - Change Password](#06-95---change-password)
- [06 B0 - Abort](#06-b0---abort)
- [06 C0 - Read Card](#06-c0---read-card)
- [06 D1 - Print Line on PT](#06-d1---print-line-on-pt)
- [06 1D - Print Line](#06-1d---print-line)
- [06 D3 - Print Text-Block on PT](#06-d3---print-text-block-on-pt)
- [06 D3 - Print Text-Block](#06-d3---print-text-block)
- [06 D8 - Dial-Up](#06-d8---dial-up)
- [06 D9 - Transmit Data via Dial-Up](#06-d9---transmit-data-via-dial-up)
- [06 DA - Receive Data via Dial-Up](#06-da---receive-data-via-dial-up)
- [06 DB - Hang-Up](#06-db---hang-up)
- [06 DD - Transparent-Mode](#06-dd---transparent-mode)
- [06 E0 - Display Text](#06-e0---display-text)
- [06 E1 - Dipsplay Text with Function-Key Input ](#06-e1---dipsplay-text-with-function-key-input-)
- [06 E2 - Display text with Numerical Input](#06-e2---display-text-with-numerical-input)
- [06 E3 - PIN-Verification for Customer-Card](#06-e3---pin-verification-for-customer-card)
- [06 E4 - Blocked-List Query to ECR](#06-e4---blocked-list-query-to-ecr)
- [06 E5 - Mac calculation](#06-e5---mac-calculation)
- [06 E6 - Card Poll with Authorization](#06-e6---card-poll-with-authorization)
- [06 C6 - Send APDUs](#06-c6---send-apdus)
- [08 01 - Activate Service-Mode](#08-01---activate-service-mode)
- [08 02 - Switch Protocol](#08-02---switch-protocol)
- [08 10 - Software Update](#08-10---software-update)
- [08 11 - Read File](#08-11---read-file)
- [08 12 - Delete File](#08-12---delete-file)
- [08 13 - Change Configuration](#08-13---change-configuration)
- [08 20 - Start OPT Action](#08-20---start-opt-action)
- [08 21 - Set OPT Point-in-Time](#08-21---set-opt-point-in-time)
- [08 22 - Start OPT Pre-Initialisation](#08-22---start-opt-pre-initialisation)
- [08 23 - Output OPT-Data](#08-23---output-opt-data)
- [08 24 - OPT Out-of-Order](#08-24---opt-out-of-order)
- [08 30 - Select Language](#08-30---select-language)
- [08 40 - Change Baudrate](#08-40---change-baudrate)
- [08 50 - Activate card-Reader](#08-50---activate-card-reader)
- [Important Receipt Texts](#important-receipt-texts)
- [Event Sequence for PT in Locked Condition and for Execution of Time-Controlled Events on PT](#event-sequence-for-pt-in-locked-condition-and-for-execution-of-time-controlled-events-on-pt)
- [Additional Data](#additional-data)
- [TLV-Container](#tlv-container)
- [Error-Messages](#error-messages)
- [Terminal Status Codes](#terminal-status-codes)
- [List of ZVT-card-type IDs](#list-of-zvt-card-type-ids)
- [Summary of utilised BMPs](#summary-of-utilised-bmps)
- [Summary of Commands](#summary-of-commands)
- [ZVT-Charactersets](#zvt-charactersets)
- [References](#references)
- [Definition of config bytes](#definition-of-config-bytes)

## 04 01 - Set Date and Time in ECR
## 04 0D - Input-Request
## 04 0E - Menu-Request
## 04 0F - Status-Information
## 04 FF - Intermediate Status Information 
## 05 01 - Status-Enquiry
## 06 00 - Registration

Using the command Registration the ECR can set up different configurations on the PT and also control the
current status of the PT.

### 06 00 - Transmitted Data:
Class       : 06 <br>
INST        : 00 <br>
Length      : xx <br>
Data        : 
- password : 3 byte BCD
- config-byte: Bit-field, 1 byte
- CC : 2 byte
- 03 service-byte: Bit-field 1 byte
- 06 TLV-Conainer (posiible tags: 10,11,12,14,1A,26,27,28,29,2A,40,1F04,1F05)


## 06 01 - Authorization
## 06 02 - Log off
## 06 03 - ABR (Account Balance Request)
## 06 04 - Activate Card
## 06 05 - Procurement
## 06 09 - Top-Up Prepaid-Cards
## 06 0A - Tax Free
## 06 0C - Book Tip
## 06 0F - Completion
## 06 10 - Send Turnover Totals
## 06 12 - Reprint Receipts
## 06 18 - Reset Terminal
## 06 1A - Print System Configuartion
## 06 1B - Set/Reset Terminal-ID
## 06 1E - Abort
## 06 20 - Repeat Receipt
## 06 21 - Telephonic Authorisation
## 06 22 - Pre-Authorisation / Reservation
## 06 23 - Partial-Reversal of a Pre-Authorisation / Booking of a Reservation
## 06 24 - Book Total
## 06 25 - Pre-Autorisation Reversal
## 06 30 - Reversal
## 06 31 - Refund
## 06 50 - End-of-Day
## 06 51 - Send offline Transactions
## 06 70 - Diagnosis
## 06 79 - Selftest
## 06 85 - Display Text (old version)
The new version can be found at [06 E0](#06-e0---display-text)

## 06 86 - Display Text with Numerical Input (old version)
The new version can be found at [06 E2](#06-e2---display-text-with-numerical-input)

## 06 87 - PIN-Verification for Customer-Card (old version)
The new version can be found at [06 E3](#06-e3---pin-verification-for-customer-card)

## 06 88 - Dipsplay Text with Function-Key Input (Old version)
The new version can be found at [06 E1](#06-e1---dipsplay-text-with-function-key-input-)

## 06 91 - Set Date and Time in PT
## 06 93 - Initialisation
## 06 95 - Change Password
## 06 B0 - Abort
## 06 C0 - Read Card
## 06 D1 - Print Line on PT
## 06 1D - Print Line
## 06 D3 - Print Text-Block on PT
## 06 D3 - Print Text-Block
## 06 D8 - Dial-Up
## 06 D9 - Transmit Data via Dial-Up
## 06 DA - Receive Data via Dial-Up
## 06 DB - Hang-Up
## 06 DD - Transparent-Mode
## 06 E0 - Display Text
The old version can be found at [06 85](#06-85---display-text-old-version)

## 06 E1 - Dipsplay Text with Function-Key Input 
The old version can be found at [06 88](#06-88---dipsplay-text-with-function-key-input-old-version)

## 06 E2 - Display text with Numerical Input
The old version can be found at [06 86](#06-86---display-text-with-numerical-input-old-version)

## 06 E3 - PIN-Verification for Customer-Card
The old version can be found at [06 86](#06-87---pin-verification-for-customer-card-old-version)
## 06 E4 - Blocked-List Query to ECR
## 06 E5 - Mac calculation
## 06 E6 - Card Poll with Authorization
## 06 C6 - Send APDUs
## 08 01 - Activate Service-Mode
## 08 02 - Switch Protocol
## 08 10 - Software Update
## 08 11 - Read File
## 08 12 - Delete File
## 08 13 - Change Configuration
## 08 20 - Start OPT Action
## 08 21 - Set OPT Point-in-Time
## 08 22 - Start OPT Pre-Initialisation
## 08 23 - Output OPT-Data
## 08 24 - OPT Out-of-Order
## 08 30 - Select Language
## 08 40 - Change Baudrate
## 08 50 - Activate card-Reader

## Important Receipt Texts
## Event Sequence for PT in Locked Condition and for Execution of Time-Controlled Events on PT
## Additional Data
## TLV-Container
## Error-Messages
## Terminal Status Codes
## List of ZVT-card-type IDs
## Summary of utilised BMPs
## Summary of Commands
## ZVT-Charactersets
## References

## Definition of config bytes

| config byte | Definition                                                                    |
|------------| ----------------------------------------------------------------------------- |
 | 0000 000x  | RFU |
| 0000 0010 | ECR assumes receipt-printout for payment funxtions <br>  0 : payment receipt not printed <br> 1: payment receipt printed | 
| 0000 0100 | ECR assumes receipt-printout for administration functions <br> 0: administration receipt not printed by ECR <br> 1: administration receipt printed by ECR |
