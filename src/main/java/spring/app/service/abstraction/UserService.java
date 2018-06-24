package spring.app.service.abstraction;

import spring.app.models.Role;
import spring.app.models.User;

import java.util.List;
import java.util.Set;


public interface UserService {
	User getUserByLogin(String login);

	User getUserById(Long id);

	void addUser(User user);

	List<User> getAllUsers();

	void deleteUserById(Long id);

	void updateUser(User user);
}
