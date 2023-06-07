package com.demo.Shop.admin.service;
import com.Shop.common.entity.*;

import java.util.List;

import org.springframework.data.domain.Page;

import com.demo.Shop.admin.error.CustomerNotFoundException;
import com.demo.Shop.admin.paging.PagingAndSortingHelper;


public interface ICustomerService {

    public void listByPage(int pageNum, PagingAndSortingHelper helper);
    public void updateCustomerEnabledStatus(Integer id, boolean enabled);

    public boolean isEmailUnique(Integer id, String email);
    public void save(Customer customerInForm);
    public void delete(Integer id) throws CustomerNotFoundException;
    public Customer get(Integer id) throws CustomerNotFoundException;
    List<Customer> listAll();
}