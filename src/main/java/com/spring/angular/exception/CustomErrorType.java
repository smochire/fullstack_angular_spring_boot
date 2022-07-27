package com.spring.angular.exception;

import com.spring.angular.model.Users;

public class CustomErrorType extends Users {
    private String errorMessage;

    public CustomErrorType(final String errorMessage)
    {
        this.errorMessage = errorMessage;
    }


    public String getErrorMessage()
    {
        return errorMessage;
    }
}
