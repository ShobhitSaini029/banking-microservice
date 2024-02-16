package com.nagarro.customer.service;

import java.util.List;

import com.nagarro.customer.controller.entity.Customer;

public interface CustomerService {
	
	// Add customer details.
	Customer addCustomer(Customer customer);
	
	// Get all the customers.
	List<Customer> getAllCostomers();
	
	// Get the customer by id.
	Customer getCustomer(int customerId);
	
	// Update the customer details.
	Customer updateCustomer(int customerId, Customer customer); 
	
	// Delete customer by id.
	boolean deleteCustomer(int customerId);
	
	// Get Customer By Account Id.
	List<Customer> getByAccountId(int accountId);
	
}
