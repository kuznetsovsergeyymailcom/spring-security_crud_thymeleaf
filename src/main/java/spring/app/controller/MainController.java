package spring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import spring.app.models.Role;
import spring.app.models.User;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;
import spring.app.utils.CodeMessenger;
import spring.app.utils.ErrorCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

	private final RoleService roleService;

	private final UserService userService;

	@Autowired
	public MainController(RoleService roleService, UserService userService) {
		this.roleService = roleService;
		this.userService = userService;
	}

	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String redirectToLoginPage() {
		return "redirect:/login";
	}

	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public ModelAndView showLoginPage() throws NoHandlerFoundException {
		ModelAndView model = new ModelAndView("login");
		ErrorCode code = CodeMessenger.getCode();
		if (code != null && code.equals(ErrorCode.LOGIN)) {
			model.addObject("isNotValid", true);
		}
		return model;
	}

	@RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
	public ModelAndView adminPage() {
		List<User> listUsers = userService.getAllUsers();
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView model = new ModelAndView("admin");
		model.addObject("currentUser", user);
		model.addObject("users", listUsers);
		return model;
	}

	@RequestMapping(value = {"/admin/addUser"}, method = RequestMethod.GET)
	public ModelAndView addUser() {
		ModelAndView model = new ModelAndView("addUser");
		ErrorCode code = CodeMessenger.getCode();
		if (code != null && code.equals(ErrorCode.ADD)) {
			model.addObject("isNotValid", true);
		}
		return model;
	}

	@RequestMapping(value = {"/admin/addUser"}, method = RequestMethod.POST)
	public String addUser(@RequestParam("login") String login, @RequestParam("email") String email,
                          @RequestParam("password") String password, @RequestParam("role") String role) {
		if (login.isEmpty() || password.isEmpty()) {
			CodeMessenger.setCode(ErrorCode.ADD);
			return "redirect:/admin/addUser";
		}

		User user = new User(login, email, password, true);
		user.setRoles(getRoles(role));

		userService.addUser(user);

		return "redirect:/admin";
	}

	@RequestMapping(value = {"/admin/edit/{id}"}, method = RequestMethod.GET)
	public ModelAndView editUser(@PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView("edit");
		ErrorCode code = CodeMessenger.getCode();
		if (code != null && code.equals(ErrorCode.EDIT)) {
			model.addObject("isNotValid", true);
		}
		model.addObject("user", userService.getUserById(id));
		return model;
	}

	@RequestMapping(value = {"/admin/edit"}, method = RequestMethod.POST)
	public String editUser(@RequestParam("id") Long id, @RequestParam("login") String login,
                           @RequestParam("email") String email, @RequestParam("password") String password,
                           @RequestParam("role") String role) {
		if (login.isEmpty() || password.isEmpty()) {
			CodeMessenger.setCode(ErrorCode.EDIT);
			return "redirect:/admin/edit/" + id;
		}

		User user = new User(id, login, email, password, true);
		user.setRoles(getRoles(role));

		userService.updateUser(user);

		return "redirect:/admin";

	}

	@RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") Long id) throws Exception {
		userService.deleteUserById(id);

		return "redirect:/admin";
	}

	@RequestMapping(value = {"/user"}, method = RequestMethod.GET)
	public ModelAndView userPage() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView model = new ModelAndView("user");
		model.addObject("user", user);
		return model;
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String accessDenied() {
		return "error";
	}

	private Set<Role> getRoles(String role) {
		Set<Role> roles = new HashSet<>();

		switch (role) {
			case "admin":
				roles.add(roleService.getRoleById(1L));
				break;
			case "user":
				roles.add(roleService.getRoleById(2L));
				break;
			case "admin, user":
				roles.add(roleService.getRoleById(1L));
				roles.add(roleService.getRoleById(2L));
				break;
			default:
				roles.add(roleService.getRoleById(2L));
				break;
		}

		return roles;
	}
}
