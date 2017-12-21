package com.example.demo.dao.impl;

import org.springframework.stereotype.Repository;

import com.example.demo.dao.UserDao;
import com.example.demo.dao.entity.User;
import com.example.demo.exception.DBException;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao {

	@Override
	public void jdbcAdd(User user) throws DBException {
		String sql = "INSERT INTO user (name, age, create_time) VALUES (?, ?, NOW())";
		try {
			this.jdbcTemplate.update(sql, user.getName(), user.getAge());
		}catch(Exception e) {
			throw new DBException(e);
		}
	}

}
