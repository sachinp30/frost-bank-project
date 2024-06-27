package com.frostbank.forstbank.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... args) {
        System.out.println("There is an exception " + ex.getMessage()
        + " at this method" + method.getName() + "---" + Arrays.toString(args));
    }
}
