package com.nagarro.customer.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nagarro.customer.controller.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	

	// Find Customer By Account Id.
	List<Customer> findByAccountId(int accountId);
}
