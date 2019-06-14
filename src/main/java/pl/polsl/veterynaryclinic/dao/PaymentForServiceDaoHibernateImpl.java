package pl.polsl.veterynaryclinic.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.veterynaryclinic.model.PaymentForService;

@Repository
public class PaymentForServiceDaoHibernateImpl implements PaymentForServiceDao{
	// define field for entitymanager	
		private EntityManager entityManager;
			
		// set up constructor injection
		@Autowired
		public PaymentForServiceDaoHibernateImpl(EntityManager theEntityManager) {
			entityManager = theEntityManager;
		}
		
		
		@Override
		@Transactional
		public List<PaymentForService> findAll() {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			// create a query
			Query<PaymentForService> theQuery =
					currentSession.createQuery("from PaymentForService", PaymentForService.class);
			
			// execute query and get result list
			List<PaymentForService> paymentsForService = theQuery.getResultList();
			
			// return the results		
			return paymentsForService;
		}
}
