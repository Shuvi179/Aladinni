package com.monesoft.refelin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email is already in use")
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super();
    }
}
