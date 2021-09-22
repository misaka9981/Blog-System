package com.cxy.blog.common.aspect;

import com.cxy.blog.model.Log;
import com.cxy.blog.service.LogService;
import com.cxy.blog.common.constant.CodeEnum;
import com.cxy.blog.common.util.JsonUtils;
import com.cxy.blog.common.util.IPUtils;
import com.cxy.blog.common.util.Result;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


 
@Slf4j
@Aspect
@Component
public class BlogLogAspect {
	@Autowired
	private LogService logService;

    @Around("execution(public * com.cxy.blog.controller.front..*.*(..)) && !@annotation(org.springframework.web.bind.annotation.ModelAttribute)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Log blogLog = new Log();
        try{
                         long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
                         int duration = Math.toIntExact(endTime - startTime);
            blogLog.setDuration(duration);
                         blogLog.setResult(JsonUtils.toJson(result));
                         String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
            blogLog.setMethod(method);
                         HttpServletRequest request=((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
                         String params = JsonUtils.toJson(request.getParameterMap());
            blogLog.setParams(params);
                         blogLog.setReferer(request.getHeader("referer"));
                         String userAgentStr = request.getHeader("user-agent");
            blogLog.setUserAgent(userAgentStr);
            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
            String operatingSystem= userAgent.getOperatingSystem().getName();
            blogLog.setOperatingSystem(operatingSystem);
            String browser = userAgent.getBrowser().getName();
            blogLog.setBrowser(browser);
                         blogLog.setType(request.getMethod());
                         String ip= IPUtils.getIpAddr(request);
            blogLog.setIp(ip);
                         blogLog.setCity(IPUtils.getCity(ip));
            if(StringUtils.isEmpty(blogLog.getCity())||"0".equals(blogLog.getCity())){
                blogLog.setCity("未知");
            }
                         blogLog.setUrl(request.getRequestURL().toString());
                         boolean isNormal = true;
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            if(Objects.nonNull(response) && response.getStatus() != HttpServletResponse.SC_OK){
                isNormal = false;
            }else if(result instanceof Result && ((Result) result).getCode() != CodeEnum.SUCCESS.getValue()){
                isNormal = false;
            }
            blogLog.setIsNormal(isNormal);
            log.info("访问日志：{}", JsonUtils.toJson(blogLog));
            logService.addLog(blogLog);
            return result;
        }catch (Throwable t){
            log.error("aop方法执行失败",t);
            blogLog.setIsNormal(false);
            logService.addLog(blogLog);
            throw t;
        }
	}
}
