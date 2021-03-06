package com.example.demo.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Profiles;
import com.example.demo.exception.BizException;
import com.example.demo.web.request.to.UserTO;
import com.example.demo.web.response.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles(Profiles.DEV)
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class})
//@ContextConfiguration(locations = { "classpath*:/applicationContext.xml", "classpath*:/applicationContext-import.xml" })
//@TransactionConfiguration(transactionManager = "transactionManagerJDBCWrite")
@Transactional
public class UserServiceTest {

	@Autowired
	UserService userService;
	
	@Test
	@Rollback(value=false)
	public void testAddUser() {
		UserTO userTO = UserTO.testInstance(); 
		try {
			UserVO userVO = userService.addUser(userTO);
			System.out.println(userVO.toString());
		} catch (BizException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Rollback(false)
	public void testAddTwoUser() throws BizException {
		Integer size1 = userService.getUserList().size();
		
		UserTO user = new UserTO();
		user.setAge(10);
		user.setName("Bob");
		user.setCreateTime(new Date());
		userService.addTwoUser(user);
		
		Integer size2 = userService.getUserList().size();
		
		Assert.assertEquals(new Integer(size1+2), size2);
	}
}
