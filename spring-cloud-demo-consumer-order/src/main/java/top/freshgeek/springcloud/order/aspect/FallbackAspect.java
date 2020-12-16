package top.freshgeek.springcloud.order.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import top.freshgeek.springcloud.common.dto.CommonResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chen.chao
 */
@Aspect
@Configuration
public class FallbackAspect {


	/**
	 * 这里我们使用注解的形式
	 * 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
	 * 切点表达式:   execution(...)
	 */
	@Pointcut("execution( * top.freshgeek.springcloud.order.feign.fallback..*(..))")
	public void logPointCut() {
	}

	/**
	 * 环绕通知 @Around  ， 当然也可以使用 @Before (前置通知)  @After (后置通知)
	 *
	 * @param point
	 * @return
	 * @throws Throwable
	 */
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object proceed = point.proceed();
		return proceed == null ? CommonResult.of(9007, "全局兜底降级,请稍后再试") : proceed;
	}


}
