package com.ltpc.ldap.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class GenericX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] ax509certificate, String s)
			throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] ax509certificate, String s)
			throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}