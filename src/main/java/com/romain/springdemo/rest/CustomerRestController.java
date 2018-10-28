package com.romain.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romain.springdemo.entity.Customer;
import com.romain.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
	
	// autowire the CustomerService
	@Autowired
	private CustomerService customerService;
	
	// add mapping for GET / customers
	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		return customerService.getCustomers();
	}
	
	// add mapping for GET /customers/{customerId}
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		Customer theCustomer = customerService.getCustomer(customerId);
		
		if (theCustomer == null) {
			throw new CustomerNotFoundException("Customer is not found - " + customerId);
		}
		
		return theCustomer;
	}
	
	// add mapping for POST / customers - add new customer
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer) {
		
		// set id to 9
		theCustomer.setId(0); // if Id is 0, DAO will insert into the database
		
		
		customerService.saveCustomer(theCustomer);
		return theCustomer;
	}
	
	// add mapping for PUT / customers - update existing customer
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		customerService.saveCustomer(theCustomer);
		return theCustomer;
	}
	
	// add mapping for DELETE /customers/{customerId} - delete customer
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable  int customerId) {
		Customer tempCustomer = customerService.getCustomer(customerId);
		customerService.deleteCustomer(customerId);
		if (tempCustomer == null) {
			throw new CustomerNotFoundException("Customer is not found - " + customerId);
		}
		return "Deleted customer id - " + customerId;
	}
}
