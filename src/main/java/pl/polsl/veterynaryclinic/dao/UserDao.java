package pl.polsl.veterynaryclinic.dao;
import java.util.List;
import pl.polsl.veterynaryclinic.model.User;

public interface UserDao {

	public List<User> findAll();
	
	public List<User> findClients();
	
	public User findUser(Integer id);
	
	public boolean editClient(Integer userId, String firstName, String lastName, Character userType, String login, String password, String phoneNumber);
	
	public boolean addUser(Integer userId, String firstName, String lastName, Character userType, String login, String password, String phoneNumber);
	
	public boolean deleteUser(Integer userId);
	
	public List<User> findWorkers();
}
