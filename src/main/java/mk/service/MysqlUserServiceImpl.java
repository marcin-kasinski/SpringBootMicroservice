package mk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.dao.MysqlUserRepository;
import mk.model.User;

@Service
public class MysqlUserServiceImpl implements MysqlUserService{

	@Autowired
	private MysqlUserRepository mysqlUserRepository;

	@Override
	public User addUser(User user) {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Service add user "+user);

		mysqlUserRepository.save(user);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Service add user END "+user);

		return user;
	}

	@Override
	public User findByEmail(String email) {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Service findByEmail "+email+" "+mysqlUserRepository.findByEmail(email));

		return mysqlUserRepository.findByEmail(email);

		
	}

	@Override
	public List<User> findAllUsersByEmail(String email) {

		return mysqlUserRepository.findAllUsersByEmail(email);
	}

	@Override
	public List<User> findAll() {

		return mysqlUserRepository.findAll();
	}

}
