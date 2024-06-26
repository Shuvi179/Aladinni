package com.monesoft.refelin.util;

import com.monesoft.refelin.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static User getCurrentUser() {
       return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
