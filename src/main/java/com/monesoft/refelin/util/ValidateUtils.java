package com.monesoft.refelin.util;

import java.util.Objects;

public class ValidateUtils {
    public static void validateId(Long id) {
        if (Objects.isNull(id) || id <= 0) {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
    }
}
