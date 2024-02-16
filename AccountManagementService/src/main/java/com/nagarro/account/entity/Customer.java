package com.nagarro.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {

	private int customerId;
    private String name;
    private String email;
    private String address;
    private int accountId;
}
