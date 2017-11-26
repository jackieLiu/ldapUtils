package com.ltpc.ldap.utils;

import com.ltpc.ldap.bean.Config;
import com.ltpc.ldap.utils.GenericX509TrustManager;
import com.novell.ldap.LDAPJSSESecureSocketFactory;
import com.novell.ldap.LDAPJSSEStartTLSFactory;
import com.novell.ldap.LDAPSocketFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by IntelliJ IDEA.
 * User: liu_tong
 * Date: 11-12-6
 * Time: 下午8:53
 * To change this template use File | Settings | File Templates.
 */
public class LdapSecurityUtil {
    private static Log log = LogFactory.getLog(LdapSecurityUtil.class);

    /**
     * Initializes an LDAP socket factory is a non-default socket factory is needed. This
     * scenario becomes relevant when needing to connect using SSL or TLS. If no special
     * socket factory is needed, null is returned which is safe to provide to the
     * constructor of {@link com.novell.ldap.LDAPConnection} or to
     * {@link com.novell.ldap.LDAPConnection#setSocketFactory(LDAPSocketFactory)}.
     *
     * @param config The configuration used for connecting.
     * @return The proper socket factory based on the provided configuration. null if
     *         special socket factory is required.
     */
    public static LDAPSocketFactory initLDAPSocketFactory(Config config) {
        LDAPSocketFactory socketFactory = null;

        if (config.isSecureConnection() || config.isTls()) {
            try {
                // initialize the keystore which will create an SSL context by which
                // socket factories can be created. this allows for multiple keystores
                // to be managed without the use of system properties.
                SSLContext ctx = initKeystore(config.getKeystoreLocation(),
                        config.getKeystorePassword());
                SSLSocketFactory sslSocketFactory = ctx.getSocketFactory();
                if (config.isTls()) {
                    socketFactory = new LDAPJSSEStartTLSFactory(sslSocketFactory);
                } else {
                    socketFactory = new LDAPJSSESecureSocketFactory(sslSocketFactory);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        return socketFactory;
    }

    /**
     * Loads a keystore and sets up an SSL context that can be used to create socket
     * factories that use the suggested keystore.
     *
     * @param keystoreLocation
     * @param keystorePassword
     * @throws java.security.cert.CertificateException
     * @throws java.security.KeyStoreException
     * @throws java.security.NoSuchProviderException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.IOException
     * @throws java.security.KeyManagementException
     * @throws NullPointerException     if a non-null keystore location cannot be resolved
     */
    public static SSLContext initKeystore(String keystoreLocation, String keystorePassword)
            throws GeneralSecurityException, IOException {
        TrustManager[] myTM = null;
        if(keystoreLocation != null && keystorePassword != null) {
            FileInputStream fis = new FileInputStream(keystoreLocation);
            char[] passChars = (keystorePassword != null) ? keystorePassword.toCharArray() : null;
            myTM = new TrustManager[]{new LdapX509TrustManager(fis, passChars)};
        } else {
            myTM = new TrustManager[]{new GenericX509TrustManager()};
        }
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, myTM, null);
        return ctx;
    }
}
