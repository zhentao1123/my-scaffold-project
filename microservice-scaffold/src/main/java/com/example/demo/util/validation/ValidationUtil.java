package com.example.demo.util.validation;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import com.google.common.collect.Lists;

/**
 * 校验工具类 结合javax.validation.constraints和org.hibernate.validator.constraints的标签使用
 */
public class ValidationUtil {
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * 验证对象
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> ValidationResult validate(T object) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validate(object, Default.class);
		if (set != null && set.size() != 0) {
			result.setHasErrors(true);
			Set<ValidationResultItem> errorMessages = Collections.emptySet();
			for (ConstraintViolation<T> cv : set) {
				errorMessages.add(new ValidationResultItem(cv.getPropertyPath().toString(), cv.getMessage()));
			}
			result.setErrorMessages(errorMessages);
		}
		return result;
	}

	/**
	 * 验证对象属性
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 */
	public static <T> ValidationResult validateProperty(T object, String propertyName) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validateProperty(object, propertyName, Default.class);
		if (set != null && set.size() != 0) {
			result.setHasErrors(true);
			Set<ValidationResultItem> errorMessages = Collections.emptySet();
			for (ConstraintViolation<T> cv : set) {
				errorMessages.add(new ValidationResultItem(cv.getPropertyPath().toString(), cv.getMessage()));
			}
			result.setErrorMessages(errorMessages);
		}
		return result;
	}

	/**
	 * 验证所有被放置在名为{@code beanType}类的属性上的所有约束，属性值为{@code值}。使用场景？？
	 * 
	 * @param beanType
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static <T> ValidationResult validateValue(Class<T> beanType, String propertyName, Object value) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validateValue(beanType, propertyName, value, Default.class);
		if (set != null && set.size() != 0) {
			result.setHasErrors(true);
			Set<ValidationResultItem> errorMessages = Collections.emptySet();
			for (ConstraintViolation<T> cv : set) {
				errorMessages.add(new ValidationResultItem(cv.getPropertyPath().toString(), cv.getMessage()));
			}
			result.setErrorMessages(errorMessages);
		}
		return result;
	}

	public static class ValidationResult {
		private boolean hasErrors;
		private Set<ValidationResultItem> errorMessages;
		public List<String> getErrorMessagesList(){
			List<String> result = null;
			if(null!=errorMessages && errorMessages.size()>0) {
				result = Lists.newArrayList();
				for(ValidationResultItem vri : errorMessages) {
					result.add(new StringBuilder().append(vri.getPropertyName()).append(" ").append(vri.getErrorMessage()).toString());
				}
			}
			return result;
		}
		public boolean getHasErrors() {
			return hasErrors;
		}
		public void setHasErrors(boolean hasErrors) {
			this.hasErrors = hasErrors;
		}
		public Set<ValidationResultItem> getErrorMessages() {
			return errorMessages;
		}
		public void setErrorMessages(Set<ValidationResultItem> errorMessages) {
			this.errorMessages = errorMessages;
		}
	}
	
	public static class ValidationResultItem{
		private String propertyName;
		private String errorMessage;
		public ValidationResultItem(String propertyName, String errorMessage) {
			super();
			this.propertyName = propertyName;
			this.errorMessage = errorMessage;
		}
		public String getPropertyName() {
			return propertyName;
		}
		public void setPropertyName(String propertyName) {
			this.propertyName = propertyName;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
	}
	
}
