package com.a7space.commons.exception;

import com.a7space.commons.enums.ExceptionEnum;

public class LoginException extends RuntimeException {

    private static final long serialVersionUID = -4027792512571150024L;

    private String code;

    private String message;
    
    public LoginException(ExceptionEnum exceptionEnum) {
        // TODO Auto-generated constructor stub
        super(exceptionEnum.getDescribe());
        this.code=exceptionEnum.getName();
        this.message=exceptionEnum.getDescribe();
    }
    public LoginException(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public LoginException(ExceptionEnum exceptionEnum, Throwable cause) {
        super(exceptionEnum.getDescribe(), cause);
        this.code = exceptionEnum.getName();
        this.message = exceptionEnum.getDescribe();
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
