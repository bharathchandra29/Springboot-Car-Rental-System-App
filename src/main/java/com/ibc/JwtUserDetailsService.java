package com.ibc;
import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("bharath".equals(username)) {
			return new User("bharath", "$2a$08$wui9v2yBMRJ9DNucdUOEw.zECP3Wv954wFvqEqA0zzLb8Xg7Kgahy",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException(username + " is not found in the records");
		}
	}
}