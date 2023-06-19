package com.atcpl.crowd.exception;

/**
 * 保存或更新Admin时，'账户已被使用'异常
 */
public class LoginAcctAlreadyUsedForUpdateException extends RuntimeException{

    public LoginAcctAlreadyUsedForUpdateException() {
    }

    public LoginAcctAlreadyUsedForUpdateException(String message) {
        super(message);
    }

    public LoginAcctAlreadyUsedForUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyUsedForUpdateException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyUsedForUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
