package spring.app.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handle() {
		return "404";
	}
}
