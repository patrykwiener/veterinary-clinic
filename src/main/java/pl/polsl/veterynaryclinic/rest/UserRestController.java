package pl.polsl.veterynaryclinic.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.veterynaryclinic.model.User;
import pl.polsl.veterynaryclinic.dao.UserDao;

@RestController
@RequestMapping("/api")
public class UserRestController {
	
private UserDao userDao;
	
	@Autowired
	public UserRestController(UserDao theUserDao) {
		userDao = theUserDao;
	}
	
	@GetMapping("/users")
	public List<User> findAll() {
		return userDao.findAll();
	}
	
}
