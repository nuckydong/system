package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.Customer;
import com.gopher.system.model.vo.request.CustomerPageRequst;

public interface CustomerDAO{
    Integer insert(Customer customer);

    void update(Customer customer);

    List<Customer> findList(Customer customer);
    
    List<Customer> findPage(CustomerPageRequst customerPageRequst);
    
    int count(CustomerPageRequst customerPageRequst);
    
    Customer findOne(Customer customer);

}
