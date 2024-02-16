package com.nagarro.customer.controller;

import java.util.List;

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

import com.nagarro.customer.controller.entity.Customer;
import com.nagarro.customer.service.CustomerService;


@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	// GET CUSTOMER
	@GetMapping()
	private ResponseEntity<List<Customer>> getCustomerById(){
		List<Customer> customers = customerService.getAllCostomers();
		return ResponseEntity.ok(customers);
	}
	
	// GET CUSTOMER BY ID
	@GetMapping("/{customerId}")
	private ResponseEntity<Customer> getCustomer(@PathVariable int customerId){
		Customer customer = customerService.getCustomer(customerId);
		return ResponseEntity.ok(customer);
	}
	
	
	// CREATE CUSTOMER
	@PostMapping()
	private ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
		Customer customerInfo = customerService.addCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerInfo);
	}
	
	// UPDATE CUSTOMER
	@PutMapping("/{customerId}")
	private ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable int customerId){
		Customer customerInfo = customerService.updateCustomer(customerId, customer);
		return ResponseEntity.ok(customerInfo);
	}
	
	// DELETE CUSTOMER
	@DeleteMapping("/{customerId}")
	private ResponseEntity<String> deleteCustomer(@PathVariable int customerId){
		String successMessage = "Customer deleted successfully";
		customerService.deleteCustomer(customerId);
		return ResponseEntity.ok(successMessage);
	}
	
	@GetMapping("/account/{accountId}")
	private ResponseEntity<List<Customer>> getByAccountId(@PathVariable int accountId){
//		System.out.println(" accountid :"+ accountId);
		List<Customer> customer = customerService.getByAccountId(accountId);
		return ResponseEntity.ok(customer);
	}

}
