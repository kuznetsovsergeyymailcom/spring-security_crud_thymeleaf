package spring.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.app.dao.abstraction.UserDao;
import spring.app.models.User;

import javax.persistence.TypedQuery;


@Transactional
@Repository
public class UserDaoImpl extends AbstractDao<Long, User> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

	@Override
	public User getUserByLogin(String login) {
		TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class);
		query.setParameter("login", login);
		return query.getSingleResult();
	}
}
