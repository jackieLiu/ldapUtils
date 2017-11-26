package com.ltpc.ldap.bean;

import com.novell.ldap.LDAPConnection;
import org.apache.commons.pool.impl.GenericObjectPool;

public class Config {
	/**
	 * Whether to create secure connections
	 * */
	private boolean secureConnection;

	/**
	 * Where to connect using TLS.
	 */
	private boolean tls;

	/**
	 * Where the keystore is located.
	 */
	private String keystoreLocation;

	/**
	 * The password to the keystore.
	 */
	private String keystorePassword;

	/**
	 * Timeout (in milliseconds) for an operation.
	 */
	private int operationTimeout = 5000;

	/**
	 * The host to which to connect.
	 */
	private String ldapHost;

	/**
	 * The port on which to connect.
	 */
	private int ldapPort = LDAPConnection.DEFAULT_PORT;

	/**
	 * The user/account to use for connections.
	 */
	private String loginUser;

	/**
	 * The password to use for connection logins.
	 */
	private String loginPassword ;

	/**
	 * Whether to follow referrals.
	 */
	private boolean followReferrals;

	/**
	 * Whether to connection allocation should include a bind attempt.
	 */
	private boolean autoBind = true;

	private int maxActive = 6;

	private Byte whenExhausted = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;

	private int maxWait = 6;

	private int maxIdle = 6;

	private boolean testOnBorrow = true;

	private boolean testOnReturn = true;

    public Config(){

    }

	public boolean isSecureConnection() {
		return secureConnection;
	}

	public void setSecureConnection(boolean secureConnection) {
		this.secureConnection = secureConnection;
	}

	public boolean isTls() {
		return tls;
	}

	public void setTls(boolean tls) {
		this.tls = tls;
	}

	public String getKeystoreLocation() {
		return keystoreLocation;
	}

	public void setKeystoreLocation(String keystoreLocation) {
		this.keystoreLocation = keystoreLocation;
	}

	public String getKeystorePassword() {
		return keystorePassword;
	}

	public void setKeystorePassword(String keystorePassword) {
		this.keystorePassword = keystorePassword;
	}

	public int getOperationTimeout() {
		return operationTimeout;
	}

	public void setOperationTimeout(int operationTimeout) {
		this.operationTimeout = operationTimeout;
	}

	public String getLdapHost() {
		return ldapHost;
	}

	public void setLdapHost(String ldapHost) {
		this.ldapHost = ldapHost;
	}

	public int getLdapPort() {
		return ldapPort;
	}

	public void setLdapPort(int ldapPort) {
		this.ldapPort = ldapPort;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public boolean isFollowReferrals() {
		return followReferrals;
	}

	public void setFollowReferrals(boolean followReferrals) {
		this.followReferrals = followReferrals;
	}

	public boolean isAutoBind() {
		return autoBind;
	}

	public void setAutoBind(boolean autoBind) {
		this.autoBind = autoBind;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public Byte getWhenExhausted() {
		return whenExhausted;
	}

	public void setWhenExhausted(Byte whenExhausted) {
		this.whenExhausted = whenExhausted;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

}
