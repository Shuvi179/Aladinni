package com.monesoft.refelin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "No access to this data")
public class PermissionException extends RuntimeException{
    public PermissionException() { super(); }
}
