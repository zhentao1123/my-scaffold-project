package com.example.demo.web.controlleradvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.exception.BizException;
import com.example.demo.web.response.CommResponse;


/**
 * 统一异常处理
 */
@ControllerAdvice
public class AppExceptionHandler {
	
	private static final Logger logger =  LoggerFactory.getLogger(AppExceptionHandler.class);

	/**
	 * 统一异常处理
	 * 处理自定义异常类型，ResponseBody返回
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({ BizException.class })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CommResponse<?> processException(BizException exception) {
		logger.info("自定义异常处理-BizException");
		return CommResponse.getInstances4Fail(exception);
	}
	
	/**
	 * 统一异常处理
	 * 处理RuntimeException异常类型，ResponseBody返回
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({ RuntimeException.class })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CommResponse<?> processException(RuntimeException exception) {
		logger.info("自定义异常处理-RuntimeException");
		return CommResponse.getInstances4Fail();
	}

	/**
	 * 统一异常处理
	 * 处理Exception异常类型，ResponseBody返回
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CommResponse<?> processException(Exception exception) {
		logger.info("自定义异常处理-Exception");
		return CommResponse.getInstances4Fail();
	}
	
	/**
	 * 统一异常处理
	 * 模版页返回
	 * @param exception
	 * @return
	 */
//	@ExceptionHandler({ RuntimeException.class })
//	@ResponseStatus(HttpStatus.OK)
//	public ModelAndView processException(RuntimeException exception) {
//		logger.info("自定义异常处理-RuntimeException");
//		ModelAndView m = new ModelAndView();
//		m.addObject("message", exception.getMessage());
//		m.setViewName("error/500");
//		return m;
//	}

	/**
	 * 统一异常处理
	 * 模版页返回
	 * @param exception
	 * @return
	 */
//	@ExceptionHandler({ Exception.class })
//	@ResponseStatus(HttpStatus.OK)
//	public ModelAndView processException(Exception exception) {
//		logger.info("自定义异常处理-Exception");
//		ModelAndView m = new ModelAndView();
//		m.addObject("message", exception.getMessage());
//		m.setViewName("error/500");
//		return m;
//	}
}
