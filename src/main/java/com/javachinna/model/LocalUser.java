package com.javachinna.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 
 * @author Chinna
 *
 */
public class LocalUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2845160792248762779L;
	private UserEntity user;

	public LocalUser(final String userID, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
			final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities, final UserEntity user) {
		super(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.user = user;
	}

	public static LocalUser create(UserEntity user) {
		LocalUser localUser = new LocalUser(user.getUsername(), user.getPassword(), true, true, true, true, buildSimpleGrantedAuthorities(user.getRoles()), user);
		return localUser;
	}

	public UserEntity getUser() {
		return user;
	}

	public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}
}
