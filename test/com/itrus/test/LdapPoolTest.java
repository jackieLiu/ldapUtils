package com.itrus.test;

import java.util.Properties;

import org.junit.Test;
import com.ltpc.ldap.LDAPConnectionPoolManager;
import com.ltpc.ldap.bean.Config;
import com.novell.ldap.LDAPConnection;

public class LdapPoolTest {

	@Test
	public void test() {
		Properties pro=new Properties();
		try {
			pro.load(LdapPoolTest.class.getClassLoader().getResourceAsStream("ldapconfig.properties"));
		}catch(Exception e){
			System.out.println("加载配置文件失败！");
		}
        boolean isSecure = true;  //flase;
        Config config = new Config();
        config.setAutoBind(true);
        config.setLdapHost("192.168.30.25");
        config.setLdapPort(389);
        config.setLoginUser("itrusutsadmin");
        config.setLoginPassword("password");
        config.setTls(isSecure);
        config.setSecureConnection(isSecure);
        LDAPConnectionPoolManager ldapCPM = new LDAPConnectionPoolManager(config);
        try {
        	//获得LDAP连接
			LDAPConnection conn = ldapCPM.getLdapConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
