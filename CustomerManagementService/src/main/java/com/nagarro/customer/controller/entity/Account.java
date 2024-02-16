package com.nagarro.customer.controller.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
	private int accountId;
	private int accountNumber;
	private BigDecimal ammount;
	private Customer customer;

}
