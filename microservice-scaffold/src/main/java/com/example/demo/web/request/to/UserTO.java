package com.example.demo.web.request.to;

import java.util.Date;

import com.example.demo.dao.entity.User;
import com.example.demo.util.mapper.BeanMapper;

@SuppressWarnings("serial")
public class UserTO extends User{

	public static UserTO testInstance() {
		UserTO test = new UserTO();
		test.setAge(11);
		test.setName("Bob");
		test.setBirthday(new Date());
		test.setSysCreateDate(new Date());
		return test;
	}

}
