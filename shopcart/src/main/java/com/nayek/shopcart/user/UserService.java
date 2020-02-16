package com.nayek.shopcart.user;

public interface UserService {
	
	void save(User user);
	void login(String username,String password);
	User findById(long id);
	User findByUsername(String username);
	User findByEmail(String email);

}
