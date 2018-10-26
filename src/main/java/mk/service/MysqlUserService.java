package mk.service;

import java.util.List;

import mk.model.User;

public interface MysqlUserService {
	public User addUser(User user);
	public User findByEmail(String email);
	public List<User> findAllUsersByEmail(String email);
}
