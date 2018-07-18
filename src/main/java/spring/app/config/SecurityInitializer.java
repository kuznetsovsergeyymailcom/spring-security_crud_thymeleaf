package spring.app.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	//Registers the DelegatingFilterProxy to use the springSecurityFilterChain before any other registered Filter
}
