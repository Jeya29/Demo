package com.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


@Configuration
@Aspect
public class AspectConfig {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Before(value = "execution(* com.example.controller.*.*(..))")
	public void beforeAdvice(JoinPoint joinPoint){
		
		log.info("-----Inside Before Advice-----");
	}
	
	/* After Advice with one argument as request*/
	
	/*@After(value = "execution(* com.example.controller.*.*(..)) and args(object)")
	public void afterAdvice(JoinPoint joinPoint,Object object){
		
		log.info("-----Inside After Advice-----" + object);
	}*/
	
	/* After Advice with one argument & response in return*/
//	@AfterReturning(value = "execution(* com.example.controller.*.*(..)) and args(object)",returning="returningObject")
//	public void AfterReturningAdvice(JoinPoint joinPoint,Object object,Object returningObject){
//		
//		log.info("-----After returning Advice Response-----" + returningObject);
//	}
	
	/* Around Advice with one argument as request & response in return)*/
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
	
	

}
