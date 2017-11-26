package com.ltpc.ldap;

import com.ltpc.ldap.bean.Config;
import com.ltpc.ldap.pool.LdapPoolFactory;
import com.novell.ldap.LDAPConnection;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * Created by IntelliJ IDEA.
 * User: liu_tong
 * Date: 11-12-6
 * Time: 下午5:44
 * To change this template use File | Settings | File Templates.
 */
public class LDAPConnectionPoolManager {

    private GenericObjectPool ldapPool;
    private LdapPoolFactory ldapPoolFactory;

    public LDAPConnectionPoolManager(Config config) {
        ldapPoolFactory = new LdapPoolFactory(config);
        ldapPool = new GenericObjectPool(ldapPoolFactory,
                config.getMaxActive(),
                config.getWhenExhausted(),
                config.getMaxWait(),
                config.getMaxIdle(),
                config.isTestOnBorrow(),
                config.isTestOnReturn());
    }

    /**
     * 池中获取LDAP连接
     * @return
     * @throws Exception
     */
    public LDAPConnection getLdapConnection() throws Exception {
        try {
            LDAPConnection conn = (LDAPConnection) ldapPool.borrowObject();
            return conn;
        } catch (Exception e) {
            throw new RuntimeException("failed to get pooled connection", e);
        }
    }

    /**
     * 将连接放回池中
     * @param conn
     * @throws Exception
     */
    public void returnConnection(LDAPConnection conn) throws Exception{
        if (conn == null) {
            return;
        }

        try {
            ldapPool.returnObject(conn);
        } catch (Exception e) {
            throw new RuntimeException("failed to return pooled connection", e);
        }
    }
}
