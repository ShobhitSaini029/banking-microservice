package com.nagarro.customer.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.customer.controller.entity.Customer;
import com.nagarro.customer.exception.ResourceNotFoundException;
import com.nagarro.customer.repository.CustomerRepository;
import com.nagarro.customer.service.CustomerService;

@Service
public class CustomerServiceImp implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImp.class);

	String message = "Customer not found, kindly check customer Id :";

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	RestTemplate restTemplate;

	// To add customers.
	@Override
	public Customer addCustomer(Customer customer) {
		Customer savedCustomer = customerRepo.save(customer);
		System.out.println(savedCustomer);
		logger.info("Customer data is saved into the database.");
		return savedCustomer;
	}

	@Override
	public List<Customer> getAllCostomers() {
		List<Customer> customers = customerRepo.findAll();
		logger.info("Customers data has been fetched from the database.");
		return customers;
	}

	@Override
	public Customer getCustomer(int customerId) {

		// Getting Customer By customer Id.
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException(message + customerId));
		logger.info("Customers data has been fetched by customer Id.");
		return customer;
	}

	@Override
	public Customer updateCustomer(int customerId, Customer customer) {
		Customer customerData = customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException(message + customerId));
		logger.info("Customers data has been fetched by customer Id.");
		customerData.setName(customer.getName());
		customerData.setEmail(customer.getEmail());
		customerData.setAddress(customer.getAddress());
		customerData.setAccountId(customer.getAccountId());
		customerRepo.save(customerData);
		logger.info("Customers data is updated");
		return customerData;
	}

	@Override
	public boolean deleteCustomer(int customerId) {
		Customer customer = customerRepo.findById(customerId)
		        .orElseThrow(() -> new ResourceNotFoundException(message + customerId));

		int accountId = customer.getAccountId();
		System.out.println(accountId);

		try {
		    // Deleting the Account details of the customer By customerID.
		    customerRepo.deleteById(customerId);
		    logger.info("Customer deleted successfully");

		    // Delete the associated account
		    ResponseEntity<Void> accountResponse = restTemplate.exchange(
		            "http://ACCOUNT-SERVICE/account/" + accountId,
		            org.springframework.http.HttpMethod.DELETE, null, Void.class);

		    if (accountResponse.getStatusCode() == HttpStatus.OK) {
		        logger.info("Account details deleted successfully for customer {}", accountId);
		        throw new ResourceNotFoundException("The Customer and customer account is deleted" + accountId);
		    } else {
		        throw new ResourceNotFoundException(
		                "This Customer is deleted, but no account associated to this customer, accountId:" + accountId);
		    }
		} catch (Exception e) {
		    throw new ResourceNotFoundException(
		            "This Customer is deleted, but no account associated to this customer accountId:" + accountId);
		} 

	}

	@Override
	public List<Customer> getByAccountId(int accountId) {
		List<Customer> customer = customerRepo.findByAccountId(accountId);
		return customer;
	}

}
