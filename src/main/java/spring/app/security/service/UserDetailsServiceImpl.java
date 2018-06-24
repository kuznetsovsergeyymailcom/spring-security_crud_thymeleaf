package spring.app.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.app.models.User;
import spring.app.service.abstraction.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	//сервис, отвечающий за получение аутентификации пользователя

	private final UserService userService;

	@Autowired
	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

// подгружаем пользователя в контекст
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = userService.getUserByLogin(login);

		if (user == null) {
			throw new UsernameNotFoundException("Username " + login + " not found");
		}

		return user;
	}
}
