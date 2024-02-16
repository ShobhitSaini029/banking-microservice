package com.nagarro.account.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.nagarro.account.implementation.AccountImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.account.entity.Account;
import com.nagarro.account.service.AccountService;


@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService accountService;

	// GET Account
	@GetMapping()
	private ResponseEntity<List<Account>> getAccountById(){
		List<Account> accounts = accountService.getAccounts();
		return ResponseEntity.ok(accounts);
	}
	
	// GET ACCOUNT BY ID
	@GetMapping("/{accountId}")
	private ResponseEntity<Account> getAccount(@PathVariable int accountId){
		Account account = accountService.getAccount(accountId);
		return ResponseEntity.ok(account);
	}
	
	
	// CREATE ACCOUNT.
	@PostMapping()
	private ResponseEntity<Account> addCustomer(@RequestBody Account account){
		Account accountInfo = accountService.addAccount(account);
		return ResponseEntity.status(HttpStatus.CREATED).body(accountInfo);
	}
	
	// UPDATE CUSTOMER
	@PutMapping("/{accountId}")
	private ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable int accountId){
		Account accountInfo = accountService.updateAccount(account, accountId);
		return ResponseEntity.ok(accountInfo);
	}
	
	// DELETE CUSTOMER
	@DeleteMapping("/{accountId}")
	private ResponseEntity<String> deleteAccount(@PathVariable int accountId){
		String successMessage = "Account deleted successfully";
		accountService.deleteAccount(accountId);
		return ResponseEntity.ok(successMessage);
	}
	
	@PutMapping("/addMoney/{accountId}")
    public ResponseEntity<Account> addMoneyToAccount(@RequestBody Account account, @PathVariable int accountId) {
		BigDecimal amount = account.getAmount();
		Account updatedAccount =  accountService.addMoneyToAccount(accountId,amount);
        return ResponseEntity.ok(updatedAccount);
    }
	
	@PutMapping("/withdrawMoney/{accountId}")
    public ResponseEntity<Account> withdrawMoneyFromAccount(@RequestBody Account account, @PathVariable int accountId) {
		BigDecimal amount = account.getAmount();
		Account updatedAccount =  accountService.withdrawMoneyFromAccount(accountId, amount);
		return ResponseEntity.ok(updatedAccount);
    }
}
