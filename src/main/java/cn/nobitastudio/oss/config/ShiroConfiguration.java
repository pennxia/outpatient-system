package cn.nobitastudio.oss.config;

import cn.nobitastudio.oss.shiro.RestFormAuthenticationFilter;
import cn.nobitastudio.oss.shiro.UserRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

	@Value("#{ @environment['shiro.loginUrl'] ?: '/login.html' }")
	protected String loginUrl;

	@Value("#{ @environment['shiro.successUrl'] ?: '/' }")
	protected String successUrl;

	@Value("#{ @environment['shiro.unauthorizedUrl'] ?: null }")
	protected String unauthorizedUrl;

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager) {

		Map<String, String> filterChainMap = new HashMap<>();
		filterChainMap.put("/syndloan/**", "authc");

		ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

		filterFactoryBean.setLoginUrl(loginUrl);
		filterFactoryBean.setSuccessUrl(successUrl);
		filterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);

		filterFactoryBean.setSecurityManager(securityManager);
		filterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

		Map<String, Filter> map = new HashMap<>();
		map.put("authc", new RestFormAuthenticationFilter());
		filterFactoryBean.setFilters(map);
		return filterFactoryBean;
	}

	@Bean
	public Realm realm() {
		return new UserRealm();
	}

}
