package pl.polsl.veterynaryclinic.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.veterynaryclinic.model.Appointment;

@Repository
public class AppointmentDaoHibernateImpl implements AppointmentDao{

	// define field for entitymanager	
		private EntityManager entityManager;
			
		// set up constructor injection
		@Autowired
		public AppointmentDaoHibernateImpl(EntityManager theEntityManager) {
			entityManager = theEntityManager;
		}
		
		
		@Override
		@Transactional
		public List<Appointment> findAll() {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			// create a query
			Query<Appointment> theQuery =
					currentSession.createQuery("from Appointment", Appointment.class);
			
			// execute query and get result list
			List<Appointment> appointments = theQuery.getResultList();
			
			// return the results		
			return appointments;
		}
}
