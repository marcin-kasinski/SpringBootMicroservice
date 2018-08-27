package mk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mk.dao.UserNumRepository;
import mk.model.UserNum;

@Service
public class UserNumServiceImpl implements UserNumService  {

	  @Autowired
	  private UserNumRepository userNumRepository;

	  @Override
	  public long getNext() {
	    UserNum last = userNumRepository.findTopByOrderByIdDesc();
	    if (last ==null ) last =new UserNum( 0);
	    long lastNum = last.seq;
	    UserNum next = new UserNum(lastNum + 1);
	    userNumRepository.save(next);
	    return next.seq;
	  }
}