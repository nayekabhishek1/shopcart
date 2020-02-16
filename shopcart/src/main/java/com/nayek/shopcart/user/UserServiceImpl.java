package com.nayek.shopcart.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private final UserRepository userRepository;
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserDetailsService userDetailsService,
			BCryptPasswordEncoder bcryptPasswordEncoder, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.userDetailsService = userDetailsService;
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void save(User user) {
		user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Override
	public void login(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password,
				userDetails.getAuthorities());
		authenticationManager.authenticate(token);

		if (token.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(token);
			logger.debug("User %s logged in successfully!", username);
		} else {
			logger.debug("Error with %s authentication", username);
		}

	}

	@Override
	public User findById(long id) {
		User user = userRepository.findById(id);
		return user;
	}

	@Override
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;
		
	}

	@Override
	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

}
