package com.sbs.service;
import com.sbs.model.Customer;

public interface CustomerService {
	public void addCustomer(Customer customer);
	public void removeCustomer(Customer customer);
	public void updateCustomer(Customer customer);	
}

