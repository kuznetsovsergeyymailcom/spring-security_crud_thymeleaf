package spring.app.dao.abstraction;

import spring.app.models.User;

public interface UserDao extends GenericDao<Long, User> {
	User getUserByLogin(String login);
}
