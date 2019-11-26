package com.quiet.live.hall.config;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import com.alibaba.fastjson.JSONObject;

/**
 * 运行上下文信息
 * <p>
 * 此上下文会传播至整个dubbo调用链，相关信息说明如下
 * <ol>
 * <li>transId： 事务id, 会打印在日志中，用于快速定位日志；也可用于幂等设计中的uuid</li>
 * <li>userId: 当前操作的用户id， 用户操作数据时，需要记录此userId</li>
 * </ol>
 * 
 * @date 2016年12月20日
 */
public class ContextUtil {
	public static final String TRANSID = "transId";
	public static final String USERID = "userId";
	public static final String USERNAME = "userName";
	public static final String OPENID="openId";
	public static final String SYSTEMID = "systemId";
	public static final String USERTYPE = "userType";
	public static final String TOKEN = "token";
	public static final String CLIENT_IP = "smartRouteclientIp";
	public static final String CLIENT_UA = "tclo2o-ua";
	// 上下文json串
	public static final String CONTEXT = "relayContext";
	
	public static final String ACCOUNT = "account";
	
	public static final String STOREID = "storeId";
	
	public static final String CUSTOMOPENID ="customOpenId";
	
	/**
	 * 记录transId
	 */
	public static void logTransId() {
		MDC.put(TRANSID, UUID.randomUUID().toString().replaceAll("-", ""));
	}

	public static void logTransId(String transId) {
		MDC.put(TRANSID, transId);
	}

	public static void setStoreId(String storeId) {
		MDC.put(STOREID, storeId);
	}
	
	public static void setUserId(String userId) {
		MDC.put(USERID, userId);
	}
	
	public static void setUserId(Long userId) {
		MDC.put(USERID, userId.toString());
	}

	public static void setSystemId(String systemId) {
		MDC.put(SYSTEMID, systemId);
	}
	
	public static void setSystemId(Long systemId) {
		MDC.put(USERID, systemId.toString());
	}

	public static void setUserType(String userType) {
		MDC.put(USERTYPE, userType);
	}
	
	
	
	public static void setCompanyCode(String companyCode){
		MDC.put(OPENID, companyCode);
	}
	
	public static void setAccount(String account){
		MDC.put(ACCOUNT, account);
	}
	
	

	public static void initContext(String context) {
		try {
			context =URLDecoder.decode(context, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		
		RelayContext vo = JSONObject.parseObject(context, RelayContext.class);
		initContext(vo);
	}

	public static void initContext(RelayContext vo) {
		MDC.put(SYSTEMID, vo.getSystemId());
		MDC.put(USERID, vo.getUserId());
		MDC.put(USERNAME, vo.getUserName());
		MDC.put(USERTYPE, vo.getUserType());
		MDC.put(CLIENT_IP, vo.getClientIp());
		MDC.put(TRANSID, vo.getTransId());
		MDC.put(CLIENT_UA, vo.getClientUa());
		MDC.put(OPENID, vo.getOpenId());
		MDC.put(TOKEN, vo.getToken());
		MDC.put(CONTEXT, JSONObject.toJSONString(vo));
		MDC.put(ACCOUNT, vo.getAccount());
		MDC.put(STOREID, vo.getStoreId());
		MDC.put(CUSTOMOPENID, vo.getCustomOpenId());
		
	}

	/**
	 * 获取当前操作用户userId
	 * 
	 * @return
	 */
	public static Long getLongUserId() {
		String t = MDC.get(USERID);
		return t != null ? Long.valueOf(t) : null;
	}

	/**
	 * 获取当前操作用户systemId
	 * 
	 * @return
	 */
	public static Integer getIntegerSystemId() {
		String t = MDC.get(SYSTEMID);
		return t != null ? Integer.valueOf(t) : null;
	}

	/**
	 * 获取当前操作用户userType
	 * 
	 * @return
	 */
	public static String getStoreId() {
		return MDC.get(STOREID);
	}
	
	public static String getUserType() {
		return MDC.get(USERTYPE);
	}

	/**
	 * 获取当前操作用户userId
	 * 
	 * @return
	 */
	public static String getUserId() {
		return MDC.get(USERID);
	}

	public static String getUserName() {
		String str = MDC.get(USERNAME);
		if(StringUtils.isNotEmpty(str)){
		     if(com.quiet.live.hall.utils.string.StringUtil.isBase64(str)){
		    	 try {
					str = new String(org.apache.commons.codec.binary.Base64.decodeBase64(str),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return  str;
		     }
		}
		return MDC.get(USERNAME);
	}

	/**
	 * 获取当前操作用户userId
	 * 
	 * @return
	 */
	public static String getSystemId() {
		return MDC.get(SYSTEMID);
	}
	
	
	public static String getOpenId() {
		return MDC.get(OPENID);
	}
	
	public static String getCustomOpenId() {
		return MDC.get(CUSTOMOPENID);
	}

	/**
	 *
	 * 获取当前调用链事务id
	 * 
	 * @return
	 */
	public static String getTransId() {
		return MDC.get(TRANSID);
	}

	public static String getClientIp() {
		return MDC.get(CLIENT_IP);
	}

	public static String getContext() {
		return MDC.get(CONTEXT);
	}
	
	public static String getAccount() {
		return MDC.get(ACCOUNT);
	}
	
	public static String getEncoderContext() {
		String context = MDC.get(CONTEXT);
		try {
			if(StringUtils.isNotEmpty(context)){
				return URLEncoder.encode(context, "UTF-8");
			}
			return context;
		} catch (UnsupportedEncodingException e) {
			return context;
		}
	}

	public static String getClientUa() {
		return MDC.get(CLIENT_UA);
	}

	public static void clear() {
		MDC.clear();
	}
	
	
	/**
	 *
	 * token
	 * 
	 * @return
	 */
	public static String getToken() {
		return MDC.get(TOKEN);
	}
	
	
	public static void setToken(String token) {
		MDC.put(TOKEN, token);
	}

}
