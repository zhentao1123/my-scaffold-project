package com.example.demo.web.request;

import com.example.demo.web.request.to.UserTO;

public class EditUserRequest extends BaseRequest{
	
	UserTO user;

	public UserTO getUser() {
		return user;
	}

	public void setUser(UserTO user) {
		this.user = user;
	}
	
}
