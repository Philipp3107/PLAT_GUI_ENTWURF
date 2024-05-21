# Terminal Codes

<style>
a{
 color: #f8aeae;
 font-size: 1.2em;
}

body {
 padding: 10px;
}

ul{
 color: #dcdcdc;
 font-size: 1.1em;
}
summary{
 font-size: 1.1em;
}

p{
 color: #dcdcdc;
 font-size: 1.1em;
 
}

</style>

Numeration of all the Terminal codes and their meaning

- **last edited 22-04-2024 - Page:24**

# Table of Contents
- [Abbreviations]
- [04 01 - Set Date and Time in ECR]
- [04 0D - Input-Request]
- [04 0E - Menu-Request]
- [04 0F - Status-Information]
- [04 FF - Intermediate Status Information]
- [05 01 - Status-Enquiry]
- [06 00 - Registration]
  - [Transmitted Data]
  - [06 00 - ECR Response]
  - [06 00 - Mentioned]
- [06 01 - Authorization]
  - [Example of the Authoristaion Process]
- [06 02 - Log off]
- [06 03 - ABR (Account Balance Request)]
- [06 04 - Activate Card]
- [06 05 - Procurement]
- [06 09 - Top-Up Prepaid-Cards]
- [06 0A - Tax Free]
- [06 0C - Book Tip]
- [06 0F - Completion]
- [06 10 - Send Turnover Totals]
- [06 12 - Reprint Receipts]
- [06 18 - Reset Terminal]
- [06 1A - Print System Configuartion]
- [06 1B - Set/Reset Terminal-ID]
- [06 1E - Abort]
- [06 20 - Repeat Receipt]
- [06 21 - Telephonic Authorisation]
- [06 22 - Pre-Authorisation / Reservation]
- [06 23 - Partial-Reversal of a Pre-Authorisation / Booking of a Reservation]
- [06 24 - Book Total]
- [06 25 - Pre-Autorisation Reversal]
- [06 30 - Reversal]
- [06 31 - Refund]
- [06 50 - End-of-Day]
- [06 51 - Send offline Transactions]
- [06 70 - Diagnosis]
- [06 79 - Selftest]
- [06 85 - Display Text (old version)]
- [06 86 - Display Text with Numerical Input (old version)]
- [06 87 - PIN-Verification for Customer-Card (old version)]
- [06 88 - Dipsplay Text with Function-Key Input (Old version)]
- [06 91 - Set Date and Time in PT]
- [06 93 - Initialisation]
- [06 95 - Change Password]
- [06 B0 - Abort]
- [06 C0 - Read Card]
- [06 D1 - Print Line on PT]
- [06 D1 - Print Line]
- [06 D3 - Print Text-Block on PT]
- [06 D3 - Print Text-Block]
- [06 D8 - Dial-Up]
- [06 D9 - Transmit Data via Dial-Up]
- [06 DA - Receive Data via Dial-Up]
- [06 DB - Hang-Up]
- [06 DD - Transparent-Mode]
- [06 E0 - Display Text]
- [06 E1 - Dipsplay Text with Function-Key Input ]
- [06 E2 - Display text with Numerical Input]
- [06 E3 - PIN-Verification for Customer-Card]
- [06 E4 - Blocked-List Query to ECR]
- [06 E5 - Mac calculation]
- [06 E6 - Card Poll with Authorization]
- [06 C6 - Send APDUs]
- [08 01 - Activate Service-Mode]
- [08 02 - Switch Protocol]
- [08 10 - Software Update]
- [08 11 - Read File]
- [08 12 - Delete File]
- [08 13 - Change Configuration]
- [08 20 - Start OPT Action]
- [08 21 - Set OPT Point-in-Time]
- [08 22 - Start OPT Pre-Initialisation]
- [08 23 - Output OPT-Data]
- [08 24 - OPT Out-of-Order]
- [08 30 - Select Language]
- [08 40 - Change Baudrate]
- [08 50 - Activate card-Reader]
- [80 00 Positive Acknowledgement]
- [84 00 Positive Acknowledgement]
- [84 xx Negative Acknowledgement]
- [84 9C Repeat Statusinfo]
- [Important Receipt Texts]
- [Event Sequence for PT in Locked Condition and for Execution of Time-Controlled Events on PT]
- [Additional Data]
- [TLV-Container]
- [Error-Messages]
- [Terminal Status Codes]
- [List of ZVT-card-type IDs]
- [Summary of utilised BMPs]
- [Summary of Commands]
- [ZVT-Charactersets]
- [References]
- [Definitions]

## Abbreviations
 | SAbbreviation   |                                                    Meaning                                                    |
 |-----------------|:-------------------------------------------------------------------------------------------------------------:|
 | APDU            |                       Application Protocol Data Unit (= a complete Request or Response)                       |
 | BMP             |                                        Bitmap, pre-defined data field                                         | 
 | CC              |                               Currency Code accordin to ISO 4271, 09 78 = Euro                                |
 | CCRC            |                                         Concurrency Code Return Code                                          |
 | ECR             |                                           Electronic Cash Register                                            |
 | PS              |                                          Personalisation System Host                                          |
 | PT              |                                               Payment Terminal                                                |
 | RC              |                                                  Return-Code                                                  |
 | RFU             |                                            Reserved for Future Use                                            |
 | TCS             |                                         Terminal Configuration Server                                         |
 | TID             |                                      Terminal-ID, 8 character numerical                                       |
 | xx              |                                 Any Value / undefined / dependent on the data                                 |
 | ZVT             |                              Zahlungsverkehrterminal (= Point of Sale Terminal)                               |
 | \<field\>       | A Parameter shown in angled-brackets is a place-holder. The playce-holder is explained in the following text. |
 | \[ \<field\> \] |                               A Parameter shown in square-brackets is optional                                |

## 04 01 - Set Date and Time in ECR
## 04 0D - Input-Request
## 04 0E - Menu-Request
## 04 0F - Status-Information
## 04 FF - Intermediate Status Information 
## 05 01 - Status-Enquiry
## 06 00 - Registration

Using the command Registration the ECR can set up different configurations on the [PT] and also control the
current status of the PT.

---

### 06 00 - Transmitted Data:

<table>
<tr>
<th scope="col" colspan="4" align="left"> ECR->PT </th>
</tr>
<tr>
<th scope="col" colspan="4">APDU</th>
</tr>
<tr>
<th scope="col" colspan="2">Control Field</th>
<td>Length</td>
<td>Data-Block</td>
</tr>
<tr>
<tr>
<td>Class</td>
<td>INSTR</td>
<td></td>
</tr>
<tr>
<td>06</td>
<td>00</td>
<td>xx</td>
<td>< password > < config-byte > [ < CC > [ 03 < service-byte >] [ 06 < TLV-container >] ]"</td>
</tr>
</table>

- password : 3 byte BCD
- config-byte: Bit-field, 1 byte
- CC : 2 byte
- 03 service-byte: Bit-field 1 byte
- 06 TLV-Conainer (posiible tags: 10,11,12,14,1A,26,27,28,29,2A,40,1F04,1F05)

---
 ### If the concurrency code is correct the Payment Terminal answers with:

<table>
<tr>
<th scope="col" colspan="4" align="left"> PT -> ECR </th>
</tr>
<tr>
<th scope="col" colspan="4">APDU</th>
</tr>
<tr>
<th scope="col" colspan="2">Control Field</th>
<td>Length</td>
<th>Data-Block</th>
</tr>
<tr>
<tr>
<td>Class</td>
<td>INSTR</td>
<td></td>
</tr>
<tr>
<td>80</td>
<td>00</td>
<td>00</td>
<td style="white-space: pre">                                                                                                                                                 </td>
</tr>
</table>

---

### If the currency-code is incorrect the Payment Terminal answers with:

<table>
<tr>
<th scope="col" colspan="4" align="left"> PT -> ECR </th>
</tr>
<tr>
<th scope="col" colspan="4">APDU</th>
</tr>
<tr>
<th scope="col" colspan="2">Control Field</th>
<td>Length</td>
<th>Data-Block</th>
</tr>
<tr>
<tr>
<td>Class</td>
<td>INSTR</td>
<td></td>
</tr>
<tr>
<td>80</td>
<td>00</td>
<td>00</td>
<td style="white-space: pre">                                                                                                                                                 </td>
</tr>
</table>



The [PT] only sends a currency-code to the [ECR], if the [ECR] had also sent a currency-code in its request.

### If the concurrency code check is positive, the Completion takes place whereupon the Electronic Cash Register receives the "masster-rights" back:

<table>
<tr>
<th scope="col" colspan="4" align="left"> PT -> ECR </th>
</tr>
<tr>
<th scope="col" colspan="4">APDU</th>
</tr>
<tr>
<th scope="col" colspan="2">Control Field</th>
<td>Length</td>
<th>Data-Block</th>
</tr>
<tr>
<tr>
<td>Class</td>
<td>INSTR</td>
<td></td>
</tr>
<tr>
<td>06</td>
<td>0F</td>
<td>xx</td>
<td style="white-space: pre">[19 < status-byte >] [29< TID >] [49 < CC >] [06< TLV-Container> ]                                       </td>
</tr>
</table>


- 19 < status-byte >: Bit-filed, 1 byte. See: [status-bytes]
- 06 < TLV-Container >: Possible Tags are 10, 11, 12, 14, 1A, 26, 27, 28. Using teh 26 the [PT] can communicate its implementation level to the [ECR


### 06 00 - [ECR] Response

<table>
<tr>
<th scope="col" colspan="4" align="left"> ECR -> PT </th>
</tr>
<tr>
<th scope="col" colspan="4">APDU</th>
</tr>
<tr>
<th scope="col" colspan="2">Control Field</th>
<td>Length</td>
<th>Data-Block</th>
</tr>
<tr>
<tr>
<td>Class</td>
<td>INSTR</td>
<td></td>
</tr>
<tr>
<td>80</td>
<td>00</td>
<td>00</td>
<td style="white-space: pre">                                                                                                                       </td>
</tr>
</table>


### 06 00 - Mentioned
[PT], [ECR], [CC], [TLV-Container], [TID], [service-bytes], [config-bytes] ,[status-bytes]

## 06 01 - Authorization

This command initiates a payment process and transamits the amount from the [ECR] to [PT]. The result of the payment process os reported to the [ECR] after completion of the booking process.

### Example of the Authoristaion Process

1. Start via call from [ECR]
2. The [PT] reads the card, if the [ECR] did not send card-data with the start.
3. The [PT] executes the transaction
4. Depending on the configuration the [PT] sends **Intermediate Status-Information** during the transaction to the [ECR], so that the [ECR] knows that the transaction is still running.
5. Release Card
6. The [PT] sends a **Status-Information** with the transaction result (successful or not)
7. (overlook)
8. Response to Status-Information with the transaction result.
9. Payment Reversal vie [PT] if the issue of goods was not successful.
10. Receipt printout 
11. Completion

<table>
<tr>
<th scope="col" colspan="4" align="left"> ECR -> PT </th>
</tr>
<tr>
<th scope="col" colspan="4">APDU</th>
</tr>
<tr>
<th scope="col" colspan="2">Control Field</th>
<td>Length</td>
<th>Data-Block</th>
</tr>
<tr>
<tr>
<td>Class</td>
<td>INSTR</td>
<td></td>
</tr>
<tr>
<td>06</td>
<td>01</td>
<td>xx</td>
<td >[04 < amount >][48 < CC >][19 < payment-type >][2D < track 1 data >][0E < expiry-date >][22 < card-number >][23 < track 2 data >][24< track 3 data >] [01 < timeout >][02 < max. status-infos >][05 < pump no. >][3A < CVV/CVC >] [3C < additional-data >][8A < card type >][06 < TL-Conatiner > ]</td>
</tr>
</table>

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
## 06 D1 - Print Line
## 06 D3 - Print Text-Block on PT


## 06 D3 - Print Text-Block

The [PT] sends Data to the [ECR] to print.

<table>
<tr>
<th scope="col" colspan="4" align="left"> ECR -> PT </th>
</tr>
<tr>
<th scope="col" colspan="4">APDU</th>
</tr>
<tr>
<th scope="col" colspan="2">Control Field</th>
<td>Length</td>
<th>Data-Block</th>
</tr>
<tr>
<tr>
<td>Class</td>
<td>INSTR</td>
<td></td>
</tr>
<tr>
<td>06</td>
<td>01</td>
<td>xx</td>
<td >[06 < TLV-Container >]</td>
</tr>
</table>

Possible Tags:
- 14
- 25
- 1f 07
- 1f 37

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
## 80 00 - Positive Acknowledgement
## 84 00 - Positive Acknowledgement
## 84 xx - Negative Acknowledgement
## 84 9C - Repeat Statusinfo

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

## Definitions
### service bytes

 | service-bytes | Definition                                                                                                                                              |
 |---------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
 | xxxx xxx1     | [PT] service-menu may not be assigned to [PT] function-key                                                                                              |
 | xxxx xxx0     | The [PT] service-menu may be assigned to [PT] function-key (=default if MBP03 omitted)                                                                  |
 | xxxx xx1x     | The display texts for the Commands Authorisation, <br> Pre-initialisation and Reversal will be displayed in capitals.                                   |
 | xxxx xx0x     | The display texts for the Commands Authorisation, <br> Pre-initialisation and Reversal will be displayed in standard font (= default if BMP03 omitted). |
 | Remainder     | The [RFU]                                                                                                                                               |

### config bytes

 | config byte | Definition                                                                                                                                                        |
 |-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
 | 0000 000x   | [RFU]                                                                                                                                                             |
 | 0000 0010   | [ECR] assumes receipt-printout for payment functions <br>  0 : payment receipt not printed <br> 1: payment receipt printed                                        | 
 | 0000 0100   | [ECR] assumes receipt-printout for administration functions <br> 0: administration receipt not printed by [ECR] <br> 1: administration receipt printed by [ECR]   |
 | 0000 1000   | [ECR] assumes requires intermediate status-information. <br> The [PT] sends no intermediate status-information of not logged-on                                   |
 | 0001 0000   | [ECR] controls payment function <br> 0: Amount input on [PT] possible <br> 1: Amount input on [PT] not possible                                                   |
 | 0010 0000   | [ECR] controls administartion function <br> 0: Start of administration on [PT] possible <br> 0: Start of administration on [PT] not possible                      |  
 | 0100 0000   | [RFU]                                                                                                                                                             |
 | 1000 0000   | [ECR] print-type : <br> 0: [ECR] compiles receipts itself from the status-information data <br> 1: Receipt-printout via [ECR] using commmand [06 D1 - Print Line] |
 | 0xxx x00x   | Receipt-printout on [PT]                                                                                                                                          |
 | 0xxx x11x   | Receipt-printout on the [ECR], whereby the [ECR] constructs the receipt <br> itself from the statusinformation; the [PT] prints nothing                           |
 | 0xxx x01x   | self constructed Payment Receipt by the [ECR]; The [PT] prints the administration receipts                                                                        |
 | 0xxx x10x   | [ECR] constructs the Administatration receipt by itself and the [PT] prints it                                                                                    |
 | 1xxx x00x   | Receipt-printout on [PT]                                                                                                                                          |
 | 1xxx x11x   | Receipt-printout on [ECR] using [06 D1 - Print Line]                                                                                                              |
 | 1xxx x01x   | Payment receipt-printout on [ECR] using [06 D1 - Print Line]; [PT] pritns the Administration receipts                                                             |
 | 1xxx x10x   | Administration receipt-printout on [ECR] using [06 D1 - Print Line]; [PT] pritns the payment receipts                                                             |


### status bytes

| status-bytes | Definition                             | 
|--------------|----------------------------------------|
| xxxx xxx1    | [PT] initialisation necessary          |
| xxxx xx1x    | Diagnosis necessary                    |
| xxxx x1xx    | OPT action necessary                   |
| xxxx 1xxx    | [PT] functions in filling station mode |
| xxx1 xxxx    | [PT] functions in vending machine mode |
| xx1x xxxx    | [RFU]                                  |
| x1xx xxxx    | [RFU]                                  |
| 1xxx xxxx    | [RFU]                                  | 
<br>

## links

[ECR]: #abbreviations
[PT]: #abbreviations
[RFU]: #abbreviations
[CC]: #abbreviations
[TID]: #abbreviations
[CCRC]: #abbreviations


[Abbreviations]: #abbreviations
[04 01 - Set Date and Time in ECR]: #04-01---set-date-and-time-in-ecr
[04 0D - Input-Request]: #04-0d---input-request
[04 0E - Menu-Request]: #04-0e---menu-request
[04 0F - Status-Information]: #04-0f---status-information
[04 FF - Intermediate Status Information]: #04-ff---intermediate-status-information-
[05 01 - Status-Enquiry]: #05-01---status-enquiry
[06 00 - Registration]: #06-00---registration
[Transmitted Data]: #06-00---transmitted-data
[06 00 - ECR Response]: #06-00---ecr-response
[06 00 - Mentioned]: #06-00---mentioned
[06 01 - Authorization]: #06-01---authorization
[Example of the Authoristaion Process]: #example-of-the-authoristaion-process
[06 02 - Log off]: #06-02---log-off
[06 03 - ABR (Account Balance Request)]: #06-03---abr-account-balance-request
[06 04 - Activate Card]: #06-04---activate-card
[06 05 - Procurement]: #06-05---procurement
[06 09 - Top-Up Prepaid-Cards]: #06-09---top-up-prepaid-cards
[06 0A - Tax Free]: #06-0a---tax-free
[06 0C - Book Tip]: #06-0c---book-tip
[06 0F - Completion]: #06-0f---completion
[06 10 - Send Turnover Totals]: #06-10---send-turnover-totals
[06 12 - Reprint Receipts]: #06-12---reprint-receipts
[06 18 - Reset Terminal]: #06-18---reset-terminal
[06 1A - Print System Configuartion]: #06-1a---print-system-configuartion
[06 1B - Set/Reset Terminal-ID]: #06-1b---setreset-terminal-id
[06 1E - Abort]: #06-1e---abort
[06 20 - Repeat Receipt]: #06-20---repeat-receipt
[06 21 - Telephonic Authorisation]: #06-21---telephonic-authorisation
[06 22 - Pre-Authorisation / Reservation]: #06-22---pre-authorisation--reservation
[06 23 - Partial-Reversal of a Pre-Authorisation / Booking of a Reservation]: #06-23---partial-reversal-of-a-pre-authorisation--booking-of-a-reservation
[06 24 - Book Total]: #06-24---book-total
[06 25 - Pre-Autorisation Reversal]: #06-25---pre-autorisation-reversal
[06 30 - Reversal]: #06-30---reversal
[06 31 - Refund]: #06-31---refund
[06 50 - End-of-Day]: #06-50---end-of-day
[06 51 - Send offline Transactions]: #06-51---send-offline-transactions
[06 70 - Diagnosis]: #06-70---diagnosis
[06 79 - Selftest]: #06-79---selftest
[06 85 - Display Text (old version)]: #06-85---display-text-old-version
[06 86 - Display Text with Numerical Input (old version)]: #06-86---display-text-with-numerical-input-old-version
[06 87 - PIN-Verification for Customer-Card (old version)]: #06-87---pin-verification-for-customer-card-old-version
[06 88 - Dipsplay Text with Function-Key Input (Old version)]: #06-88---dipsplay-text-with-function-key-input-old-version
[06 91 - Set Date and Time in PT]: #06-91---set-date-and-time-in-pt
[06 93 - Initialisation]: #06-93---initialisation
[06 95 - Change Password]: #06-95---change-password
[06 B0 - Abort]: #06-b0---abort
[06 C0 - Read Card]: #06-c0---read-card
[06 D1 - Print Line on PT]: #06-d1---print-line-on-pt
[06 D1 - Print Line]: #06-d1---print-line
[06 D3 - Print Text-Block on PT]: #06-d3---print-text-block-on-pt
[06 D3 - Print Text-Block]: #06-d3---print-text-block
[06 D8 - Dial-Up]: #06-d8---dial-up
[06 D9 - Transmit Data via Dial-Up]: #06-d9---transmit-data-via-dial-up
[06 DA - Receive Data via Dial-Up]: #06-da---receive-data-via-dial-up
[06 DB - Hang-Up]: #06-db---hang-up
[06 DD - Transparent-Mode]: #06-dd---transparent-mode
[06 E0 - Display Text]: #06-e0---display-text
[06 E1 - Dipsplay Text with Function-Key Input ]: #06-e1---dipsplay-text-with-function-key-input-
[06 E2 - Display text with Numerical Input]: #06-e2---display-text-with-numerical-input
[06 E3 - PIN-Verification for Customer-Card]: #06-e3---pin-verification-for-customer-card
[06 E4 - Blocked-List Query to ECR]: #06-e4---blocked-list-query-to-ecr
[06 E5 - Mac calculation]: #06-e5---mac-calculation
[06 E6 - Card Poll with Authorization]: #06-e6---card-poll-with-authorization
[06 C6 - Send APDUs]: #06-c6---send-apdus
[08 01 - Activate Service-Mode]: #08-01---activate-service-mode
[08 02 - Switch Protocol]: #08-02---switch-protocol
[08 10 - Software Update]: #08-10---software-update
[08 11 - Read File]: #08-11---read-file
[08 12 - Delete File]: #08-12---delete-file
[08 13 - Change Configuration]: #08-13---change-configuration
[08 20 - Start OPT Action]: #08-20---start-opt-action
[08 21 - Set OPT Point-in-Time]: #08-21---set-opt-point-in-time
[08 22 - Start OPT Pre-Initialisation]: #08-22---start-opt-pre-initialisation
[08 23 - Output OPT-Data]: #08-23---output-opt-data
[08 24 - OPT Out-of-Order]: #08-24---opt-out-of-order
[08 30 - Select Language]: #08-30---select-language
[08 40 - Change Baudrate]: #08-40---change-baudrate
[08 50 - Activate card-Reader]: #08-50---activate-card-reader
[80 00 Positive Acknowledgement]: #80-00---positive-acknowledgement
[84 00 Positive Acknowledgement]: #84-00---positive-acknowledgement
[84 xx Negative Acknowledgement]: #84-xx---negative-acknowledgement
[84 9C Repeat Statusinfo]: #84-9c---repeat-statusinfo
[Important Receipt Texts]: #important-receipt-texts
[Event Sequence for PT in Locked Condition and for Execution of Time-Controlled Events on PT]: #event-sequence-for-pt-in-locked-condition-and-for-execution-of-time-controlled-events-on-pt
[Additional Data]: #additional-data
[TLV-Container]: #tlv-container
[Error-Messages]: #error-messages
[Terminal Status Codes]: #terminal-status-codes
[List of ZVT-card-type IDs]: #list-of-zvt-card-type-ids
[Summary of utilised BMPs]: #summary-of-utilised-bmps
[Summary of Commands]: #summary-of-commands
[ZVT-Charactersets]: #zvt-charactersets
[References]: #references
[Definitions]: #definitions
[service-bytes]: #service-bytes
[config-bytes]: #config-bytes
[status-bytes]: #status-bytes

## emojis

✔️ - erledigt <br>
💻 - In arbeit <br>
❌ - noch nicht implementiert <br>