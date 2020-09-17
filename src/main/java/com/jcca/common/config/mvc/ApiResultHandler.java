package com.jcca.common.config.mvc;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.jcca.common.bean.ResultVo;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

@Slf4j
@RestController
@ControllerAdvice(annotations = {RestController.class,Controller.class})
public class ApiResultHandler implements ResponseBodyAdvice {
    private static final Class[] anons = {
            RequestMapping.class,
            GetMapping.class,
            PostMapping.class,
            DeleteMapping.class,
            PutMapping.class
    };

    /**
     * 对所有RestController的接口方法进行拦截
     */
    @Override
    public boolean supports(MethodParameter returnType, @Nullable Class converterType) {
        AnnotatedElement element = returnType.getAnnotatedElement();
        return Arrays.stream(anons).anyMatch(a -> a.isAnnotation() && element.isAnnotationPresent(a));
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body,
                                  @Nullable MethodParameter returnType,
                                  @Nullable MediaType selectedContentType,
                                  @Nullable Class selectedConverterType,
                                  @Nullable ServerHttpRequest request,
                                  ServerHttpResponse response) {


        return body;

    }

    /**
     * 统一异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultErrorHandler(Exception e) {
        log.error("全局异常:",e);
        return new ResultVo(500, "错误提示:" + e.getMessage(),null);
    }



}
