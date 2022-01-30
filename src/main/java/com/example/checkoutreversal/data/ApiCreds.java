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
public class ApiCreds {
    private String access_token;
    private String expires_in;
}
