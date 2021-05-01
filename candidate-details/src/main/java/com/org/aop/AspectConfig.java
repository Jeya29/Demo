package com.org.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;


@Configuration
@Aspect
@Slf4j
public class AspectConfig {
	
	
	@Before(value = "execution(* com.example.controller.*.*(..))")
	public void beforeAdvice(JoinPoint joinPoint){
		
		log.info("-----Inside Before Advice-----");
	}
	
	
	/* Around Advice with one argument as request & response in return */
	@Around(value = "execution(* com.example.controller.*.*(..)) and args(object)")
	public Object AroundAdvice(ProceedingJoinPoint proceedingJoinPoint,Object object){
		
		log.info("-----Inside Around Advice Request-----" + object);
		
		Object returningObject = null;
		
		try {
			returningObject = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("----After Around Advice Response----" + returningObject );
		
		return returningObject;
		
		
	}
	
	@Around("@annotation(com.org.aop.TrackExecutionTime)")
	public Object executionTime(ProceedingJoinPoint point) throws Throwable {
		
		long startTime = System.currentTimeMillis();
		Object obj = point.proceed();
		long endTime = System.currentTimeMillis();
		
		log.info("Class Name: " + point.getSignature().getDeclaringTypeName()+
				". Method Name: " + point.getSignature().getName()+". Time taken for execution is : " +
				(endTime-startTime) + "ms");
		
		return obj;
	}
	
	

}
