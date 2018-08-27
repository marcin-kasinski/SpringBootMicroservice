package mk.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mk.model.UserNum;

@Repository
public interface UserNumRepository extends MongoRepository<UserNum, String> {
  UserNum findTopByOrderByIdDesc();
}