package pl.polsl.veterynaryclinic.dao;

import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.polsl.veterynaryclinic.model.Medicine;
import pl.polsl.veterynaryclinic.model.Service;

@Repository
public class ServiceDaoHibernateImpl implements ServiceDao{
	// define field for entitymanager	
		private EntityManager entityManager;
			
		// set up constructor injection
		@Autowired
		public ServiceDaoHibernateImpl(EntityManager theEntityManager) {
			entityManager = theEntityManager;
		}
		
		
		@Override
		@Transactional
		public List<Service> findAll() {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			// create a query
			Query<Service> theQuery =
					currentSession.createQuery("from Service", Service.class);
			
			// execute query and get result list
			List<Service> services = theQuery.getResultList();
			
			// return the results		
			return services;
		}
		
		@Override
		@Transactional
		public boolean editService(Integer serviceId, Float serviceCost, String serviceType) {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			String hql = "UPDATE cennik set cena_uslugi = "  + serviceCost.toString() + ", rodzaj_uslugi = '" + serviceType + "'" + 
					" WHERE id_uslugi = " + serviceId.toString();
			
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
		public boolean deleteService(Integer serviceId) {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			String hql = "DELETE FROM cennik "  + 
		             "WHERE id_uslugi = " + serviceId.toString();
			Query query = currentSession.createNativeQuery(hql);
			int result = query.executeUpdate();
			
			if (result == 1)
				return true;
			return false;
		}
		
		@Override
		@Transactional
		public Service findService(Integer id) {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			// create a query
			Query<Service> theQuery =
					currentSession.createQuery("from Service where serviceId = " + id.toString(), Service.class);
			
			// execute query and get result list
			List<Service> services = theQuery.getResultList();
			
			// return the results		
			return services.get(0);
		}
		
		@Override
		@Transactional
		public boolean addService(Integer serviceId, Float serviceCost, String serviceType) {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			String hql = "INSERT INTO cennik(cena_uslugi, rodzaj_uslugi) VALUES "
					+ "(" + serviceCost.toString() +", '" + serviceType + "')";
			Query query = currentSession.createNativeQuery(hql);
			int result = query.executeUpdate();
			
			if (result == 1)
				return true;
			return false;
		}
}
