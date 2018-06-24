package spring.app.service.abstraction;

import spring.app.models.Role;

import java.util.List;


public interface RoleService {
	void addRole(Role role);

	Role getRoleByName(String roleName);

	Role getRoleById(Long id);

	List<Role> getAllRoles();

	void updateRole(Role role);

	void deleteRoleById(Long id);
}
