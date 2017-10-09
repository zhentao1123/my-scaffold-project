package com.example.demo.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.BusinessException;
import com.example.demo.service.UserService;
import com.example.demo.web.request.AddUserRequest;
import com.example.demo.web.request.DeleteUserRequest;
import com.example.demo.web.request.EditUserRequest;
import com.example.demo.web.request.GetUserRequest;
import com.example.demo.web.request.VoidRequest;
import com.example.demo.web.request.to.UserTO;
import com.example.demo.web.response.CommResponse;
import com.example.demo.web.response.vo.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/user")
@Api(hidden=false, tags="User接口")
public class UserController extends BaseController{
	
	static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;
	
	/**
	 * 获取用户列表
	 * @return
	 * @throws BusinessException
	 */
	@ApiOperation(value="获取用户列表", notes="")
	@ApiImplicitParam(name = "request", value = "请求对象", required = true, dataType = "VoidRequest")
	@RequestMapping(value="/getUserList", method=RequestMethod.POST)
	public CommResponse<List<UserVO>> getUserList(VoidRequest request) throws BusinessException{
		List<UserVO> list = userService.getUserList();
		return CommResponse.getInstances4Succeed(list);
	}
	
	/**
	 * 创建用户
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "request", value = "请求对象", required = true, dataType = "AddUserRequest")
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	public CommResponse<UserVO> addUser(@RequestBody AddUserRequest request) throws BusinessException{
		UserTO userTO = request.getUser();
		UserVO userVO = userService.addUser(userTO);
		return CommResponse.getInstances4Succeed(userVO);
	}
	
	/**
	 * 获取用户详细信息
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ApiOperation(value="获取用户详细信息", notes="id来获取用户详细信息")
    @ApiImplicitParam(name = "request", value = "请求对象", required = true, dataType = "GetUserRequest")
	@RequestMapping(value="/getUser", method=RequestMethod.POST)
	public CommResponse<UserVO> getUser(@RequestBody GetUserRequest request) throws BusinessException{
		Integer id = request.getId();
		UserVO userVO = userService.getUser(id);
		return CommResponse.getInstances4Succeed(userVO);
	}
	
	/**
	 * 更新用户详细信息
	 * @param id
	 * @param user
	 * @return
	 * @throws BusinessException
	 */
	@ApiOperation(value="更新用户详细信息", notes="根据user对象来更新用户详细信息")
	@ApiImplicitParam(name = "request", value = "请求对象", required = true, dataType = "EditUserRequest")
	@RequestMapping(value="/editUser", method=RequestMethod.POST)
	public CommResponse<UserVO> editUser(@RequestBody EditUserRequest request) throws BusinessException{
		UserTO userTO = request.getUser();
		UserVO userVO = userService.editUser(userTO);
		return CommResponse.getInstances4Succeed(userVO);
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ApiOperation(value="删除用户", notes="根据id来指定删除对象")
	@ApiImplicitParam(name = "request", value = "请求对象", required = true, dataType = "DeleteUserRequest")
	@RequestMapping(value="/deleteUser", method=RequestMethod.POST)
	public CommResponse<Void> deleteUser(@RequestBody DeleteUserRequest request) throws BusinessException {
		Integer id = request.getId();
		userService.removeUser(id);
		return CommResponse.getInstances4Succeed(null);
	}
}

