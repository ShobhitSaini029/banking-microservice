package com.nagarro.account.entity;

import java.math.BigDecimal;
import java.util.ArrayList;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;
	private int accountNumber;
	private BigDecimal amount;
//	private int customerId;

	@Transient
    private ArrayList<Customer> customers;
	
}
