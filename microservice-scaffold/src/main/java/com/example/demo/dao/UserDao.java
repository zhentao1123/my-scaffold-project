package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.dao.entity.User;
import com.example.demo.exception.DBException;

@Repository
public interface UserDao {
	
	public void jdbcAdd(User user) throws DBException;
	
}
