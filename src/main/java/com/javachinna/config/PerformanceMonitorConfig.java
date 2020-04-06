package com.javachinna.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class PerformanceMonitorConfig {

	@Bean
	public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
		return new PerformanceMonitorInterceptor(true);
	}

	/**
	 * Pointcut for execution of methods on classes annotated with {@link Service}
	 * annotation
	 */
	@Pointcut("execution(public * (@org.springframework.stereotype.Service com.javachinna..*).*(..))")
	public void serviceAnnotation() {
	}

	/**
	 * Pointcut for execution of methods on classes annotated with
	 * {@link Repository} annotation
	 */
	@Pointcut("execution(public * (@org.springframework.stereotype.Repository com.javachinna..*).*(..))")
	public void repositoryAnnotation() {
	}

	@Pointcut("serviceAnnotation() || repositoryAnnotation()")
	public void performanceMonitor() {
	}

	@Bean
	public Advisor performanceMonitorAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("com.javachinna.config.PerformanceMonitorConfig.performanceMonitor()");
		return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
	}
}