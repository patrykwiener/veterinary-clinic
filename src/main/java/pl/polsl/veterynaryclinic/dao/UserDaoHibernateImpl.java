package pl.polsl.veterynaryclinic.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.veterynaryclinic.model.User;

@Repository
public class UserDaoHibernateImpl implements UserDao{
	// define field for entitymanager	
		private EntityManager entityManager;
			
		// set up constructor injection
		@Autowired
		public UserDaoHibernateImpl(EntityManager theEntityManager) {
			entityManager = theEntityManager;
		}
		
		
		@Override
		@Transactional
		public List<User> findAll() {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			// create a query
			Query<User> theQuery =
					currentSession.createQuery("from User", User.class);
			
			// execute query and get result list
			List<User> users = theQuery.getResultList();
			
			// return the results		
			return users;
		}
		
		@Override
		@Transactional
		public List<User> findClients() {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			// create a query
			Query<User> theQuery =
					currentSession.createQuery("from User where userType = 'K'", User.class);
			
			// execute query and get result list
			List<User> users = theQuery.getResultList();
			
			// return the results		
			return users;
		}
		
		@Override
		@Transactional
		public User findUser(Integer id) {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			// create a query
			Query<User> theQuery =
					currentSession.createQuery("from User where userId = " + id.toString(), User.class);
			
			// execute query and get result list
			List<User> users = theQuery.getResultList();
			
			// return the results		
			return users.get(0);
		}
		
		@Override
		@Transactional
		public boolean editClient(Integer userId, String firstName, String lastName, Character userType, String login, String password, String phoneNumber) {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			String hql = "UPDATE uzytkownicy set imie = '"  + firstName + "', nazwisko = '" + lastName + "', rodzaj_uzytkownika = '" + userType.toString() + "', login = '" + login + "', haslo = '" + password + "', numer_telefonu = '" + phoneNumber + "'" +
		             " WHERE id_uzytkownika = " + userId.toString();
			
			//String hql = "INSERT INTO uzytkownicy(imie,nazwisko,rodzaj_uzytkownika,login,haslo,numer_telefonu) VALUES "
			//		+ "('" + firstName + "','"+ lastName +"','"+ userType.toString() +"','"+ login +"','"+ password +"','"+ phoneNumber +"')";
			Query query = currentSession.createNativeQuery(hql);
			int result = query.executeUpdate();
			
			if (result == 1)
				return true;
			return false;
		}
		
		@Override
		@Transactional
		public boolean addUser(Integer userId, String firstName, String lastName, Character userType, String login, String password, String phoneNumber) {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			String hql = "INSERT INTO uzytkownicy(imie,nazwisko,rodzaj_uzytkownika,login,haslo,numer_telefonu) VALUES "
					+ "('" + firstName + "','"+ lastName +"','"+ userType.toString() +"','"+ login +"','"+ password +"','"+ phoneNumber +"')";
			Query query = currentSession.createNativeQuery(hql);
			int result = query.executeUpdate();
			
			if (result == 1)
				return true;
			return false;
		}
		
		@Override
		@Transactional
		public boolean deleteUser(Integer userId) {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			String hql = "DELETE FROM uzytkownicy "  + 
		             "WHERE id_uzytkownika = " + userId.toString();
			Query query = currentSession.createNativeQuery(hql);
			int result = query.executeUpdate();
			
			if (result == 1)
				return true;
			return false;
		}
		
		@Override
		@Transactional
		public List<User> findWorkers() {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			// create a query
			Query<User> theQuery =
					currentSession.createQuery("from User where userType = 'L'", User.class);
			
			// execute query and get result list
			List<User> users = theQuery.getResultList();
			
			// return the results		
			return users;
		}
}
