package mk.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.mongodb.repository.MongoRepository;

import mk.model.MongoUser;

public interface MongoUserRepository extends MongoRepository<MongoUser, Long>
{

//	public interface UserRepository {

	 /**
	   * This method will find an User instance in the database by its email.
	   * Note that this method is not implemented and its working code will be
	   * automagically generated from its signature by Spring Data JPA.
	   */
	  public MongoUser findByEmail(String email);
	  public List<MongoUser> findAllUsersByEmail(String email);

	
}
