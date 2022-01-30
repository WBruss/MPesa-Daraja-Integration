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
public class CheckoutAcknowledgement {
    private String MerchantRequestID;
    private String CheckoutRequestID;
    private String ResponseCode;
    private String ResponseDescription;
    private String CustomerMessage;
}
