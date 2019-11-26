package com.quiet.live.hall.config;

/**
 * 中继上下文
 * 
 * @author luoml
 *
 */
public class RelayContext {

	private String transId;
	private String userId;
	private String partId;
	private String userName;
	private String userType;
	private String clientIp;
	private String systemId;
	private String clientUa;
	private String openId;
	private String token;
	private String account;
	private String storeId;
	private String customOpenId;

	public String getCustomOpenId() {
		return customOpenId;
	}

	public RelayContext setCustomOpenId(String customOpenId) {
		this.customOpenId = customOpenId;
		return this;
	}

	public String getTransId() {
		return transId;
	}

	public RelayContext setTransId(String transId) {
		this.transId = transId;
		return this;
	}
	
	public RelayContext setOpenId(String openId) {
		this.openId = openId;
		return this;
	}
	public RelayContext setStoreId(String storeId) {
		this.storeId = storeId;
		return this;
	}
	
	public String getStoreId() {
		return storeId;
	}
	public String getUserId() {
		return userId;
	}

	public RelayContext setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public String getUserType() {
		return userType;
	}

	public RelayContext setUserType(String userType) {
		this.userType = userType;
		return this;
	}

	public String getClientIp() {
		return clientIp;
	}

	public RelayContext setClientIp(String clientIp) {
		this.clientIp = clientIp;
		return this;
	}

	public String getSystemId() {
		return systemId;
	}

	public RelayContext setSystemId(String systemId) {
		this.systemId = systemId;
		return this;
	}

	public String getClientUa() {
		return clientUa;
	}

	public RelayContext setClientUa(String clientUa) {
		this.clientUa = clientUa;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getOpenId() {
		return openId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}



	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	

}
