package com.example.checkoutreversal.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author pwainaina
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MpesaCheckoutRequest {
    private int BusinessShortCode;
    private String Password;
    private String Timestamp;
    private String TransactionType;
    private Long Amount;
    private Long PartyA;
    private Long PartyB;
    private Long PhoneNumber;
    private String CallBackURL;
    private String AccountReference;
    private String TransactionDesc;
}
