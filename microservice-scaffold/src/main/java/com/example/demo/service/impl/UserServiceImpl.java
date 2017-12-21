package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.entity.User;
import com.example.demo.dao.repository.UserRepository;
import com.example.demo.exception.BizException;
import com.example.demo.service.UserService;
import com.example.demo.util.mapper.BeanMapper;
import com.example.demo.web.request.to.UserTO;
import com.example.demo.web.response.vo.UserVO;
import com.google.common.collect.Lists;

@Service
public class UserServiceImpl extends BaseService implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<UserVO> getUserList() throws BizException {
		Iterable<User> it = userRepository.findAll();
		List<User> tmplist = Lists.newArrayList(it);
		List<UserVO> list = BeanMapper.mapList(tmplist, UserVO.class);
		return list;
	}

	@Override
	public UserVO getUser(Integer id) throws BizException {
		User user = userRepository.findOne(id);
		UserVO userVO = BeanMapper.map(user, UserVO.class);
		return userVO;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public UserVO addUser(UserTO userTO) throws BizException {
		User addUser = BeanMapper.map(userTO, User.class);
		User newUser = userRepository.save(addUser);
		UserVO userVO = BeanMapper.map(newUser, UserVO.class);
		return userVO;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UserVO editUser(UserTO userTO) throws BizException {
		User editUser = BeanMapper.map(userTO, User.class);
		User newUser = userRepository.save(editUser);
		UserVO userVO = BeanMapper.map(newUser, UserVO.class);
		return userVO;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void removeUser(Integer id) throws BizException {
		userRepository.delete(id);
	}

}
