package com.app.server.businessservice.appbasicsetup.aaa;
import org.springframework.stereotype.Component;

@Component
public class UrlPatternDefinitionImpl implements UrlPatternDefinitions {

	private final String[] aryIgnoreUrl = { "/secure/Authentication/*", "/secure/reportViewController/*", "/secure/queryExecutor/*", "/secure/Logout","/secure/CommonRestServiceImpl/*" };

	@Override
	public String[] getPublicUrlPatterns() {
		return null;
	}

	@Override
	public String[] getAuthorizedUrlPatterns() {
		return null;
	}

	@Override
	public String[] getRequestFilterIgnoreUrlPatterns() {
		return aryIgnoreUrl;
	}

	@Override
	public String getMainPageControllerUrlPattern() {
		return null;
	}
}
