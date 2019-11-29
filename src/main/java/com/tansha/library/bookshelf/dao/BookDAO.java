package com.tansha.library.bookshelf.dao;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDAO {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	/*public List<Object[]> selectSearchBooks() {

        List<Object[]> customers = new ArrayList<Object[]>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL);

        for (Map<String, Object> row : rows) 
        {
             Customer customer = new Customer();
             customer.setCustNo((int)row.get("Cust_id"));
             customer.setCustName((String)row.get("Cust_name"));
             customer.setCountry((String)row.get("Country"));

             customers.add(customer);
         }

       return customers;
   }*/
	
}
