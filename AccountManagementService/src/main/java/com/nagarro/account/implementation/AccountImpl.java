package com.nagarro.account.implementation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.nagarro.account.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagarro.account.axception.InsufficientFundsException;
import com.nagarro.account.entity.Account;
import com.nagarro.account.exception.ResourceNotFoundException;
import com.nagarro.account.repository.AccountRepo;
import com.nagarro.account.service.AccountService;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountImpl implements AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountImpl.class);
	String message = "Account not found, kindly check Account Id :";

	@Autowired
	AccountRepo accountRepo;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public List<Account> getAccounts() {
		List<Account> accounts = accountRepo.findAll();
		logger.info("Accounts data has been fetched from the database.");

		// Getting the Customer Linked this Account.
		for (Account account : accounts) {
			int accountId = account.getAccountId();

			@SuppressWarnings("unchecked")
			ArrayList<Customer> customer = restTemplate
					.getForObject("http://CUSTOMER-SERVICE/customer/account/" + accountId, ArrayList.class);
			account.setCustomers(customer);
		}
		return accounts;
	}

	@Override
	public Account getAccount(int accountId) {

		// Fetching the account data by account Id.
		Account accountInfo = accountRepo.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException(message + accountId));
		logger.info("Accounts data has been fetched by account id from the database.");

		// Fetching the customer id who has above account from CUSTOMER SERVICE.
		@SuppressWarnings("unchecked")
		ArrayList<Customer> customer = restTemplate
				.getForObject("http://CUSTOMER-SERVICE/customer/account/" + accountInfo.getAccountId(), ArrayList.class);
		logger.info("{}", customer);
		accountInfo.setCustomers(customer);
		return accountInfo;
	}

	@Override
	public Account addAccount(Account account) {
		Account savedAccount = accountRepo.save(account);
		logger.info("Accounts data has been saved into the database.");
		return savedAccount;
	}

	@Override
	public Account updateAccount(Account account, int accountId) {
		Account accountInfo = accountRepo.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException(message + accountId));
		accountInfo.setAccountNumber(account.getAccountNumber());
		accountInfo.setAmount(account.getAmount());
		logger.info("Accounts deatails has been updated into the database.");
		accountRepo.save(accountInfo);
		logger.info("Update details has been saved into the database.");
		return accountInfo;
	}

	@Transactional
	@Override
	public boolean deleteAccount(int accountId) {

		// Checking the account is present or not.
		accountRepo.findById(accountId).orElseThrow(() -> new ResourceNotFoundException(message + accountId));

		// Deleting the account.
		accountRepo.deleteById(accountId);
		logger.info("Accounts data has been deleted from the database.");
		return true;
	}

	@Override
	public Account withdrawMoneyFromAccount(int accountId, BigDecimal amount) {
		Account accountInfo = accountRepo.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException(message + accountId));
		logger.info("Accounts data has been fetched by account id from the database.");

		try {
			// Fetch customer details using Customer Management Service API
			@SuppressWarnings("unchecked")
			ArrayList<Customer> customerResponse = restTemplate.getForObject(
					"http://CUSTOMER-SERVICE/customer/account/" + accountInfo.getAccountId(), ArrayList.class);

			logger.info("Customer Verification.");
			if (customerResponse != null && !customerResponse.isEmpty()) {
				// Withdraw money from the account
				BigDecimal currentBalance = accountInfo.getAmount();
				BigDecimal withdrawalAmount = amount;

				if (currentBalance.compareTo(withdrawalAmount) >= 0) {
					BigDecimal updatedAmount = currentBalance.subtract(withdrawalAmount);
					accountInfo.setAmount(updatedAmount);
					accountInfo.setCustomers(customerResponse);
					accountRepo.save(accountInfo);
					logger.info("Amount has been updated.");
					return accountInfo;
				} else {
					throw new InsufficientFundsException("Insufficient funds in the account");
				}
			} else {
				throw new ResourceNotFoundException("Customer not found for account with id: " + accountId);
			}
		} catch (InsufficientFundsException e) {
			throw new InsufficientFundsException("Insufficient funds in the account");
		}
	}

	@Override
	public Account addMoneyToAccount(int accountId, BigDecimal amount) {
		Account accountInfo = accountRepo.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException(message + accountId));
		logger.info("Accounts data has been fetched by account id from the database.");

		try {

			// Fetch customer details using Customer Management Service
			@SuppressWarnings("unchecked")
			ArrayList<Customer> customerResponse = restTemplate.getForObject(
					"http://CUSTOMER-SERVICE/customer/account/" + accountInfo.getAccountId(), ArrayList.class);

			logger.info("Customer Verification.");
			if (customerResponse != null && !customerResponse.isEmpty()) {

				// Adding money to the account
				BigDecimal updatedAmount = accountInfo.getAmount().add(amount);
				accountInfo.setAmount(updatedAmount);
				accountInfo.setCustomers(customerResponse);
				accountRepo.save(accountInfo);
				logger.info("Amount has been updated.");
			} else {
				throw new ResourceNotFoundException("Customer not found for account with id: " + accountId);
			}
		} catch (Exception e) {
			throw new ResourceNotFoundException(message + accountId);
		}
		return accountInfo;
	}
}
