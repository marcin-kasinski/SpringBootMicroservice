package mk.dao;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import mk.model.User;

@Transactional
public interface MysqlUserRepository extends CrudRepository<User, Long>{

//	public interface UserRepository {

	 /**
	   * This method will find an User instance in the database by its email.
	   * Note that this method is not implemented and its working code will be
	   * automagically generated from its signature by Spring Data JPA.
	   */
	  public User findByEmail(String email);
	  public List<User> findAllUsersByEmail(String email);

	
}
