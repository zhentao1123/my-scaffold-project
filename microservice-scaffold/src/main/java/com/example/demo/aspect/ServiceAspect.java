package com.example.demo.aspect;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.annotation.NeedValidation;
import com.example.demo.exception.BizException;
import com.example.demo.util.validation.ValidationUtil;
import com.example.demo.util.validation.ValidationUtil.ValidationResult;

/**
 * Service层切面处理 
 * 1）公共方法参数检查 
 * 2）公共方法参数授权校验
 */
@Aspect
@Order(6)
@Component
public class ServiceAspect {

	private static final Logger logger = LoggerFactory.getLogger(ServiceAspect.class);
	
	@Autowired
	//AccountDAO accountDAO;
	
	@Pointcut("execution(public * com.example.demo.service.impl.*.*(..))")
	public void validation() {}

	@Before("validation()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		
		Method targetMethod = ((MethodSignature)joinPoint.getSignature()).getMethod();//获取Public方法
		Object[] args = joinPoint.getArgs();
		
		if (null!=targetMethod && null!=args && args.length>0) {//公共方法才检查；有参数才检查
			Object arg = args[0];
			NeedValidation anno = null;
			try {
				anno = arg.getClass().getAnnotation(NeedValidation.class);
			} catch (Exception e) {}
			//仅有一个参数并且标注了@NeedValidation
			if (args.length==1 && null!=arg && !isJavaClass(arg.getClass()) && null!=anno) {
				entityValid(arg);
				checkAuth(arg);
			}
		}
	}

	/**
	 * 参数校验
	 * @param p
	 * @throws BizException
	 */
	protected <P> void entityValid(P p) throws BizException{
		ValidationResult result = ValidationUtil.validate(p);
		if(result.getHasErrors()) {
			String errorMessages = StringUtils.join(result.getErrorMessagesList());
			throw BizException.getParamException(errorMessages);
		}
	}
	
	/**
	 * 授权校验
	 * @param p
	 * @throws BizException
	 */
	protected <P> void checkAuth(P p) throws BizException{
		
	}
	
	/**
	 * 是否是Java自身的类
	 * 
	 * @param clazz
	 * @return
	 */
	private boolean isJavaClass(Class<?> clazz) {
		return clazz.getClassLoader() == null;
	}
}
