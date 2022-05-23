package com.yunhalee.withEmployee.aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAop {

    private final LogTrace logTrace;

    public LogAop(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Pointcut("execution(* com.yunhalee.withEmployee..*Controller*.*(..))")
    public void controller(){};

    @Pointcut("execution(* com.yunhalee.withEmployee..*Service*.*(..))")
    public void service(){};

    @Pointcut("execution(* com.yunhalee.withEmployee..*Repository*.*(..))")
    public void repository(){};


    @Around("controller() || service() || repository()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            status = logTrace.begin(joinPoint.getSignature().toShortString());
            Object result = joinPoint.proceed();
            logTrace.end(status);
            return result;
        }catch (Throwable e) {
            e.printStackTrace();
            logTrace.exception(status, e);
            throw e;
        }
    }


}
