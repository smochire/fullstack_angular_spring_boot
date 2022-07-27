package com.spring.angular.exception;

import lombok.Data;

import java.awt.TrayIcon.MessageType;


@Data
public class FieldValidationError {
    private String filed;
    private String message;
    private MessageType type;
}
