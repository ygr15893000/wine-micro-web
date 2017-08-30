package com.siran.wine.dao;


import com.siran.wine.model.Customer;

public interface ICustomerDao
{
	public void insert(Customer customer);
	public Customer findByCustomerId(int custId);
}




