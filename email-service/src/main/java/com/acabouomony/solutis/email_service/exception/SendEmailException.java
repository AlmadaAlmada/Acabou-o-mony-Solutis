package com.acabouomony.solutis.email_service.exception;

public class SendEmailException extends RuntimeException{
    public SendEmailException(String message){
        super(message);
    }
}
