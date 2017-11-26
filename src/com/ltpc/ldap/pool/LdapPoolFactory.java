package com.ltpc.ldap.pool;

import com.ltpc.ldap.bean.Config;
import com.ltpc.ldap.utils.LdapSecurityUtil;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPSocketFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.BasePoolableObjectFactory;

import java.io.UnsupportedEncodingException;


/**
 * Created by IntelliJ IDEA.
 * User: liu_tong
 * Date: 11-12-6
 * Time: 下午5:55
 * To change this template use File | Settings | File Templates.
 */
public class LdapPoolFactory extends BasePoolableObjectFactory {
    private static Log log = LogFactory.getLog(LdapPoolFactory.class);
	private LDAPSocketFactory factory;
	private Config config;

    public LdapPoolFactory(Config config){
        this.config = config;
        factory = LdapSecurityUtil.initLDAPSocketFactory(config);
    }

    /**
     * 产生新的连接
     * @return
     * @throws Exception
     */
    @Override
    public Object makeObject() throws Exception {
        LDAPConnection conn = new LDAPConnection(factory);

        log.debug("connect ldapHost=" + config.getLdapHost() + ", ldapPort=" + config.getLdapPort());
        conn.connect(config.getLdapHost(), config.getLdapPort());

        if (config.isTls()) {
            log.debug("attempting to initiate TLS");
            conn.startTLS();
        }
        if (config.isAutoBind()) {
            String binddn = config.getLoginUser();
            log.debug("makeObject(): binding connection to default bind DN [" + binddn + "]");
            byte[] bindpw;
            try {
                bindpw = config.getLoginPassword().getBytes("UTF8");
            } catch (Exception e) {
                throw new RuntimeException("unable to encode bind password", e);
            }
            conn.bind(LDAPConnection.LDAP_V3, binddn, bindpw);
        }
        return conn;
    }

    /**
     * 销毁连接
     * @param LdapConn
     * @throws Exception
     */
    @Override
    public void destroyObject(Object LdapConn) throws Exception  {
        LDAPConnection conn = (LDAPConnection)LdapConn;
        log.debug("destroyObject");
    }

    /**
     *验证连接
     * @param LdapConn
     * @return
     */
    @Override
    public boolean validateObject(Object LdapConn) {
        if (LdapConn == null) {
            return false;
        }

        if (LdapConn instanceof LDAPConnection) {
            LDAPConnection conn = (LDAPConnection) LdapConn;

            if (!(config.isAutoBind())) {
                return false;
            }

            try {
                String binddn = config.getLoginUser();
                byte[] bindpw = null;
                try {
                    bindpw = config.getLoginPassword().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // ignore
                }
                conn.bind(LDAPConnection.LDAP_V3, binddn, bindpw);
            } catch (Exception e) {
                log.error("validateObject(): unable to rebind pooled connection", e);
                return false;
            }

        } else {
            // we know the ref is not null
            log.debug("validateObject(): connection not of expected type [" + LdapConn.getClass().getName() + "] nothing to do");
            return false;
        }
        return true;
    }


}
