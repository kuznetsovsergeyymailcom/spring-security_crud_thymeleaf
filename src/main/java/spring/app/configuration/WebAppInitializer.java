package spring.app.configuration;

import org.springframework.lang.Nullable;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Nullable
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{
				ApplicationContextConfig.class
		};
	}

	@Nullable
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{
				SpringWebConfig.class
		};
	}

	@Override
	@SuppressWarnings(value = "all")
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}


	@Override
	@SuppressWarnings(value = "all")
	protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		DispatcherServlet dispatcherServlet =  new DispatcherServlet(servletAppContext);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		return dispatcherServlet;
	}
}
