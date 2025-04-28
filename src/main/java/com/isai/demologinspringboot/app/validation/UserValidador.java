package com.isai.demologinspringboot.app.validation;

import com.isai.demologinspringboot.app.dtos.UserRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidador implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequest userRequest = (UserRequest) target;
        ValidationUtils.rejectIfEmpty(errors, "userName", "NotEmpty.userRequest.userName");
        if (userRequest.getUserName().length() > 10){
            errors.rejectValue("userName", "Size.userRequest.userName");
        }
//        if (!user.getPhone().matches("\\d{3}[-.\\s]?\\d{3}[-.\\s]?\\d{4}")) {
//            errors.rejectValue("phone", "Invalid.user.phone");
//        }
    }
}
