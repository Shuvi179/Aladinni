package com.monesoft.refelin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource(ignoreResourceNotFound=true, value="classpath:email.properties")
public class EmailService {

    @Value("${refriend.hunter.redirect.url}")
    private String hunterRedirectUrl;

    @Value("${refriend.employee.redirect.url}")
    private String employeeRedirectUrl;

    public boolean isEmployeeRedirectUrl(String redirectUrl) {
        return employeeRedirectUrl.equals(redirectUrl);
    }

    public boolean isValidRedirectUrl(String redirectUrl) {
        return StringUtils.equalsAny(redirectUrl, employeeRedirectUrl, hunterRedirectUrl);
    }
}
