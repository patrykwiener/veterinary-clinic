package pl.polsl.veterynaryclinic.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.veterynaryclinic.model.Patient;

@Repository
public class PatientDaoHibernateImpl implements PatientDao{
	// define field for entitymanager	
		private EntityManager entityManager;
			
		// set up constructor injection
		@Autowired
		public PatientDaoHibernateImpl(EntityManager theEntityManager) {
			entityManager = theEntityManager;
		}
		
		
		@Override
		@Transactional
		public List<Patient> findAll() {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			// create a query
			Query<Patient> theQuery =
					currentSession.createQuery("from Patient", Patient.class);
			
			// execute query and get result list
			List<Patient> patients = theQuery.getResultList();
			
			// return the results		
			return patients;
		}
}
