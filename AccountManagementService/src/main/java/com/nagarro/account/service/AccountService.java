package com.nagarro.account.service;

import java.math.BigDecimal;
import java.util.List;

import com.nagarro.account.entity.Account;

public interface AccountService {
	
	// Get all accounts.
	List<Account> getAccounts();
	
	// Get single account.
	Account getAccount(int accountId);
	
	// Add account.
	Account addAccount(Account account);
	
	// Update account.
	Account updateAccount(Account account, int accountId);
	
	// Delete account
	boolean deleteAccount(int accountId);

	// Add amount to account.
	Account addMoneyToAccount(int accountId, BigDecimal amount);

	// Withdraw amount from account.
	Account withdrawMoneyFromAccount(int accountId, BigDecimal amount);
	

}
