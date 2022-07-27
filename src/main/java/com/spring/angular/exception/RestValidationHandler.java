package com.spring.angular.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestValidationHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<FieldValidationErrorDetails> handleValidationError(MethodArgumentNotValidException mNotValidException, HttpServletRequest request) {
        FieldValidationErrorDetails fieldValidationErrorDetails = new FieldValidationErrorDetails();
        fieldValidationErrorDetails.setError_timeStamp(new Date().getTime());
        fieldValidationErrorDetails.setError_status(HttpStatus.BAD_REQUEST.value());
        fieldValidationErrorDetails.setError_title("Field Validation Error");
        fieldValidationErrorDetails.setError_detail("Input Field Validation Failed");
        fieldValidationErrorDetails.setError_developer_Message(mNotValidException.getMessage());
        fieldValidationErrorDetails.setError_path(request.getRequestURI());
        BindingResult result = mNotValidException.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError error : fieldErrors) {
            FieldValidationError fError = processFieldError(error);
            List<FieldValidationError> fValidationErrorsList =
                    fieldValidationErrorDetails.getErrors().get(error.getField());
            if (fValidationErrorsList == null) {
                fValidationErrorsList =
                        new ArrayList<FieldValidationError>();
            }
            fValidationErrorsList.add(fError);
            fieldValidationErrorDetails.getErrors().put(
                    error.getField(), fValidationErrorsList);
        }
        return new ResponseEntity<FieldValidationErrorDetails>(fieldValidationErrorDetails, HttpStatus.BAD_REQUEST);
    }


    private FieldValidationError processFieldError(final FieldError error) {
        FieldValidationError fieldValidationError =
                new FieldValidationError();
        if (error != null) {
            fieldValidationError.setFiled(error.getField());
            fieldValidationError.setType(MessageType.ERROR);
            fieldValidationError.setMessage(error.getDefaultMessage());
        }
        return fieldValidationError;
    }
}
