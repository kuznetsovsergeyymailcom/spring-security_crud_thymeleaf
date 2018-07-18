package spring.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "login",  nullable = false, unique = true)
	private String login;

	@Column(name = "email",  nullable = false, unique = true)
	private String email;

	@Column(name = "password", length = 30, nullable = false)
	private String password;


	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
	@JoinTable(name = "permissions",
			joinColumns = {@JoinColumn(name = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")})
//	@JsonIgnore
	private Set<Role> roles;

	@Column(name = "enabled", nullable = false)
	private Boolean enabled = true;


	public User() {
	}

	public User(Long id, String login, String email, String password, Boolean enabled) {
		this.id = id;
		this.login = login;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.enabled = enabled;
	}

	public User(String login, String email, String password, Boolean enabled) {
		this.login = login;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return login;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String toString() {
		return login;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != null ? !id.equals(user.id) : user.id != null) return false;
		if (login != null ? !login.equals(user.login) : user.login != null) return false;
		if (password != null ? !password.equals(user.password) : user.password != null) return false;
		if (roles != null ? !roles.equals(user.roles) : user.roles != null) return false;
		return enabled != null ? enabled.equals(user.enabled) : user.enabled == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (login != null ? login.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (roles != null ? roles.hashCode() : 0);
		result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
		return result;
	}
}
