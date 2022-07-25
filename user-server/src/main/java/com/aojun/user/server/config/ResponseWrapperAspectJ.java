package com.aojun.user.server.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy
@Order(1)
public class ResponseWrapperAspectJ {

    @Pointcut(value = "within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerPointcut() {
    }

    @Pointcut(value = "execution(* com.aojun..*Controller.*(..))")
    public void anyControllerMethodPointcut() {
    }

    @Pointcut(value = "within(@org.springframework.cloud.openfeign.FeignClient *)")
    public void feignClientPointcut(){

    }

    /**
     * 不拦截的方法
     */
    @Pointcut(value = "!execution(* com.aojun.user.server.controller.SysLoginController.captcha(..))")
    public void NoPointCut(){

    }

    @Around(value = "(restControllerPointcut() || anyControllerMethodPointcut() || feignClientPointcut()) && NoPointCut()")
    public Object processAround(ProceedingJoinPoint jp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Object[] args = jp.getArgs();
        Object responseObj = null;
        long start = System.currentTimeMillis();
        try {
            responseObj = jp.proceed();
            return responseObj;
        } finally {
            Map<String, Object> headerMap = new HashMap<>();
            Enumeration<String> enumeration = request.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                String headerName = enumeration.nextElement();
                Object headerValue = request.getHeader(headerName);
                headerMap.put(headerName, headerValue);
            }
            log.info(">>>>>>>真实地址：" + StringUtils.trimToEmpty(getRemoteIp(request)));
            log.info(">>>>>>>请求地址：" + StringUtils.wrap(StringUtils.trimToEmpty(request.getServletPath()), "'"));
            log.info(">>>>>>>请求头部：" + JSON.toJSONString(headerMap));
            log.info(">>>>>>>请求参数：" + JSON.toJSONString(args));
            log.info(">>>>>>>处理方法：" + StringUtils.trimToEmpty(jp.getSignature().toString()));
            log.info(">>>>>>>请求时间：" + DateFormatUtils.format(start, "yyyy-MM-dd HH:mm:ss.SSS"));
            log.info(">>>>>>>响应时间：" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss.SSS"));
            log.info(">>>>>>>处理耗时：" + (System.currentTimeMillis() - start) + "ms");
            log.info(">>>>>>>请求地址：{},返回结果：{}",StringUtils.wrap(StringUtils.trimToEmpty(request.getServletPath()), "'"),JSON.toJSONString(responseObj));

        }
    }
    protected String getRemoteIp(HttpServletRequest req) {
        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }
}