package com.atcpl.crowd.exception;

/**
 * 保存或更新Admin时，'账号已被使用'异常
 */
public class LoginAcctAlreadyUsedException extends RuntimeException{

    public LoginAcctAlreadyUsedException() {
    }

    public LoginAcctAlreadyUsedException(String message) {
        super(message);
    }

    public LoginAcctAlreadyUsedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyUsedException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyUsedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
