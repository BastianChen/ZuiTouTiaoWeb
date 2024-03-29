package com.cb.web.zuitoutiao;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Description: 日志切面
 * @author: YFZX-CB-1784 ChenBen
 * @create: 2018-10-17 14:17
 **/
@Aspect
@Component
public class LoggerAdvice {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Before("within(com.cb.web.zuitoutiao..*) && @annotation(loggerManage)")
    @Before(value = "execution(* com.cb.web.zuitoutiao.controller.*.*(..)) && @annotation(loggerManage)")
    public void addBeforeLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
        LocalDateTime now = LocalDateTime.now();
        logger.info(now.toString() + "执行[" + loggerManage.logDescription() + "]开始");
        logger.info(joinPoint.getSignature().toString());
        logger.info(parseParames(joinPoint.getArgs()));
    }

    @AfterReturning("within(com.cb.web.zuitoutiao..*) && @annotation(loggerManage)")
    public void addAfterReturningLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
        LocalDateTime now = LocalDateTime.now();
        logger.info(now.toString() + "执行 [" + loggerManage.logDescription() + "] 结束");
    }

    @AfterThrowing(pointcut = "within(com.cb.web.zuitoutiao..*) && @annotation(loggerManage)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, LoggerManage loggerManage, Exception ex) {
        LocalDateTime now = LocalDateTime.now();
        logger.error(now.toString() + "执行 [" + loggerManage.logDescription() + "] 异常", ex);
    }

    private String parseParames(Object[] parames) {
        if (null == parames || parames.length <= 0) {
            return "";
        }
        StringBuffer param = new StringBuffer("传入参数 # 个:[ ");
        int i = 0, j = 0;
        for (Object obj : parames) {
            j++;
            if (obj != null) {
                i++;
                if (j != parames.length) {
                    param.append(obj.toString()).append(" ,");
                } else {
                    param.append(obj.toString());
                }
            }
        }
        return param.append(" ]").toString().replace("#", String.valueOf(i));
    }
}
