package com.jcca.common.util;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * 校验工具
 *
 * @author Manager
 */
public class AppValidatorUtils {

    private static <T> Set<ConstraintViolation<T>> validate(T bean) {
        Set<ConstraintViolation<T>> ret = new HashSet<ConstraintViolation<T>>();
        if (bean == null) {
            return ret;
        }

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        ret = validator.validate(bean);
        return ret;
    }

    public static <T> String validateReq(T req) {
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<T> violation : validate(req)) {
            builder.append(violation.getMessage());
            builder.append("; ");
        }
        return builder.toString();
    }

    public static <T> String validateReq2(T req) {
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<T> violation : validate(req)) {
            builder.append(violation.getPropertyPath());
            builder.append(":");
            builder.append(violation.getMessage());
            builder.append("; ");
        }
        return builder.toString();
    }

}
