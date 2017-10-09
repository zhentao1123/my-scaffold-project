package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.BusinessException;
import com.example.demo.web.request.to.UserTO;
import com.example.demo.web.response.vo.UserVO;

@Service
public interface UserService {
	
	public List<UserVO> getUserList() throws BusinessException;
	
	public UserVO getUser(Integer id) throws BusinessException;
	
	public UserVO addUser(UserTO user) throws BusinessException;
	
	public UserVO editUser(UserTO user) throws BusinessException;
	
	public void removeUser(Integer id) throws BusinessException;
	
}
