package com.quiet.live.hall.config;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.quiet.live.hall.constants.Constant;
import com.quiet.live.hall.entity.JUser;
import com.quiet.live.hall.utils.IPUtil;
import com.quiet.live.hall.utils.PropUtil;
import com.quiet.live.hall.utils.web.WebVo;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class MySecurity {

	 	@Autowired
	 	RedisComponent redisComponent;
	 	
	 	@Value("${chek.api.not.filter}")
	 	private String apiNotString;
	 	
	    @Pointcut("execution(public * com.quiet.live.hall.*.rest.*.*(..))")
	    public void log() {
	    }

	    @Before("log()")
	    public void deoBefore(JoinPoint joinPoint) throws IOException {
	        log.info("方法执行前...");

	        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = sra.getRequest();
	        HttpServletResponse response = sra.getResponse();
	        log.info("url:" + request.getRequestURI());
	        log.info("ip:" + IPUtil.getIP(request));
	        log.info("method:" + request.getMethod());
	        log.info("class_method:" + joinPoint.getSignature().getDeclaringTypeName() + "."
	                + joinPoint.getSignature().getName());
	        if(!request.getRequestURI().contains("fileUpload") && 
	        		!request.getRequestURI().contains("/wx/return") 
	        		&& !request.getRequestURI().contains("/order/createOrderParam")){
	        	log.info("args:" + JSON.toJSONString(joinPoint.getArgs()));
	        }
	        
	        response.addHeader("Access-Control-Max-Age", "3600");//30 min
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,Content-Type,token,multipart/form-data");
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH,CORS");
	       
	        
	       //支持所有跨域请求
//			if("OPTIONS".equals(request.getMethod())){
//				sendOptionResult(request, response);
//				return;
//			}
	    }

	    @After("log()")
	    public void doAfter(JoinPoint joinPoint) {
	        log.info("方法执行后...");
	    }

	    @AfterReturning(returning = "result", pointcut = "log()")
	    public void doAfterReturning(Object result) {
	        log.info("执行返回值：" + JSON.toJSONString(result));
	    }

	    @Around("log()")
	    public Object trackInfo(ProceedingJoinPoint pjp) throws Throwable {
	    	
	        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = sra.getRequest();
	        String path = request.getServletPath();
	        log.info("请求连接："+path);
	        if(apiNotString.contains(path) || path.contains("/wx/portal")){
	        	log.info("放行的API：" +path);
				return pjp.proceed();
			}
	        JUser loginUser =  checkUserLogin(request);
	        if (null == loginUser) {
	            log.info("-------------没有登录-------------");
	            return WebVo.result(Constant.CommonCode.NOT_LOGIN, "未登录");
	        } else {
	        	log.info("登录用户信息:" + JSON.toJSONString(loginUser));
	            log.info("-------------登录通过-------------");
	        }
	        return pjp.proceed();
	    }
	    
	    public JUser checkUserLogin(HttpServletRequest request){
	    	String accessToken = request.getHeader("accessToken");
			if (StringUtils.isBlank(accessToken)) {
				Cookie tokenCookie = WebUtils.getCookie(request, "accessToken");
				if(tokenCookie != null){
					accessToken = tokenCookie.getValue();
				}
			}
			
			if(StringUtils.isNotBlank(accessToken)){
				String tokenKey =  accessToken;
				JUser loginUser = redisComponent.get(tokenKey, JUser.class);
				log.info("redis 数据" + JSON.toJSONString(loginUser));
				if(loginUser == null){
					return null;
				}
				redisComponent.setEx(tokenKey, loginUser, PropUtil.getInt("token.timeout", 17280000));
				
				RelayContext context = new  RelayContext();
				context.setUserId(loginUser.getId());
				context.setUserName(loginUser.getUsername());
				context.setUserType(loginUser.getUserType());
				context.setCustomOpenId(loginUser.getCustomOpenId());
				context.setOpenId(loginUser.getOpenId());
				context.setToken(accessToken);
				context.setAccount(loginUser.getAccount());
				ContextUtil.initContext(context);
				return loginUser;
			}
//			else{
//				JUser loginUser = jUserService.selectById("5da6fe713b284a9283e344abfddc6eeb");
//				RelayContext context = new  RelayContext();
//				context.setUserId(loginUser.getId());
//				context.setUserName(loginUser.getUsername());
//				context.setUserType(loginUser.getUserType());
//				context.setOpenId(loginUser.getOpenId());
//				context.setToken(accessToken);
//				context.setAccount(loginUser.getAccount());
//				ContextUtil.initContext(context);
//				return loginUser;
//			}
			return null;
	    }
//	    
//		public void init(FilterConfig filterConfig) throws ServletException {
//			String pubListKey = "api.public.list";
//			String pubList = PropUtil.get(pubListKey);
//			if(pubList != null){
//				String[] list = pubList.split(",");
//				log.info("无需token借口地址： " + Arrays.toString(list));
//				notAuthUrl.addAll(Arrays.asList(list));
//			}
//			PropUtil.addListener(pubListKey, new PropChangeListener() {
//				@Override
//				public void onChange(String key, String oldValue, String newValue) {
//					if(StringUtils.isNotEmpty(newValue)){
//						String[] list = newValue.split(",");
//						notAuthUrl = new HashSet<>();
//						log.info("无需授权列表重新加载： " + Arrays.toString(list));
//						notAuthUrl.addAll(Arrays.asList(list));
//					}
//				}
//			});
//		}
}
