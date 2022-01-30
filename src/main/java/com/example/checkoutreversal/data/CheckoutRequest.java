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
public class CheckoutRequest {
    private Long amount;
    private Long phoneNumber;
    private String callbackUrl;
    private String accountReference;
    private String transactionDesc;
}
