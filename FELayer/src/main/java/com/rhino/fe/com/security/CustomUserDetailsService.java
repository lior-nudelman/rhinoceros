package com.rhino.fe.com.security;

import java.util.ArrayList;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rhino.userAttributesServic.UserManagerInterface;
import com.rhino.userAttributesServic.data.UserAttributeType;
import com.rhino.userAttributesServic.data.UserDataInterface;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserManagerInterface userManagerInterface;
	
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		UserDataInterface userDB = userManagerInterface.getUser(userName);
		if(userDB == null){
			throw new UsernameNotFoundException("User "+userName+" not found");
		}
		Map<UserAttributeType, String> userAttr = userDB.getUserAttributes();
		String username = userName;
		String password = userAttr.get(UserAttributeType.USER_PASSWORD);
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority auth1= new SimpleGrantedAuthority("ROLE_USER");
		authorities.add(auth1);
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		User user = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		return user;
	}


}
