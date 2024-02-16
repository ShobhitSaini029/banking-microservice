package com.nagarro.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.account.entity.Account;

public interface AccountRepo extends JpaRepository<Account, Integer> {

	// Custom finder methods.
//	List<Account> findByCustomerId(int customerId);
//	void deleteByCustomerId(int customerId);
	void deleteByAccountNumber(int acountNumber);
}
