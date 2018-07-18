package spring.app.dao.abstraction;

import spring.app.models.Role;

public interface RoleDao extends GenericDao<Long, Role> {
	Role getRoleByName(String roleName);
}
