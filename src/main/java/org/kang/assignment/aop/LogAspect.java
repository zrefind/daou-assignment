package org.kang.assignment.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Aspect
@Component
public class LogAspect {

    @Around("within(org.kang.assignment.web.controller..*)")
    public Object logAction(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        LocalDateTime requestAt = LocalDateTime.now();
        Object result = joinPoint.proceed();
        LocalDateTime responseAt = LocalDateTime.now();

        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        logger.info("[{}]-[request: {} / response: {} / running: {} ns]", methodName, requestAt, responseAt, Duration.between(requestAt, responseAt).toNanos());

        return result;
    }

}
