package com.app.config;
import com.app.server.businessservice.appbasicsetup.aaa.ADAuthBusinessService;

import com.app.server.businessservice.appbasicsetup.aaa.DBAuthBusinessService;

import com.app.config.appSetup.model.AppConfiguration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.spartan.pluggable.auth.PluggableAuthConnector;


@Configuration

public class SecurityConfig {

	/**
	 * authentication type decides the type of authentication. 1 = Internal
	 * (JDBC) 2 = LDAP
	 * */

	@Autowired
	private AppConfiguration appConfig;

	@Bean(name = "userAuthenticator")
	public PluggableAuthConnector userAuthenticator() throws Exception {
		PluggableAuthConnector authentication = null;
		if (Integer.valueOf(appConfig.getAuthenticationConfig().getAuthPlugin().getType()) == 0) {
			if (Integer.valueOf(appConfig.getAuthenticationConfig().getAuthPlugin().getAuthType()) == 1) {
				authentication = new DBAuthBusinessService();
			} else if (Integer.valueOf(appConfig.getAuthenticationConfig().getAuthPlugin().getAuthType()) == 2) {
				/* External (LDAP) */
				authentication = new ADAuthBusinessService(appConfig.getAuthenticationConfig().getAuthPlugin().getAuthProperties());
			} else {
				throw new Exception("Plugin type 0: Authentication type not set properly");
			}
		} else if (Integer.valueOf(appConfig.getAuthenticationConfig().getAuthPlugin().getType()) == 1) {
			try {
				authentication=(PluggableAuthConnector) Class.forName(appConfig.getAuthenticationConfig().getAuthPlugin().getAuthClassName()).newInstance();
			} catch (ClassNotFoundException e) {
				throw new ClassNotFoundException("Plugin type 1: Auth class colud not be loaded.");
			}

		} else {
			throw new Exception("Plugin type 1: Auth class " + appConfig.getAuthenticationConfig().getAuthPlugin().getAuthClassName() + " not found.");
		}
		return authentication;
	}

}
