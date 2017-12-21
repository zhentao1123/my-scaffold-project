package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.BizException;
import com.example.demo.web.request.to.UserTO;
import com.example.demo.web.response.vo.UserVO;

@Service
public interface UserService {
	
	public List<UserVO> getUserList() throws BizException;
	
	public UserVO getUser(Integer id) throws BizException;
	
	public UserVO addUser(UserTO user) throws BizException;
	
	public UserVO editUser(UserTO user) throws BizException;
	
	public void removeUser(Integer id) throws BizException;
	
	public void addTwoUser(UserTO userTO) throws BizException;
}
