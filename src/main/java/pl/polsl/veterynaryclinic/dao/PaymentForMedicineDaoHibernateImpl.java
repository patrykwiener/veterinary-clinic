package pl.polsl.veterynaryclinic.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.veterynaryclinic.model.PaymentForMedicine;

@Repository
public class PaymentForMedicineDaoHibernateImpl implements PaymentForMedicineDao{
	// define field for entitymanager	
		private EntityManager entityManager;
			
		// set up constructor injection
		@Autowired
		public PaymentForMedicineDaoHibernateImpl(EntityManager theEntityManager) {
			entityManager = theEntityManager;
		}
		
		
		@Override
		@Transactional
		public List<PaymentForMedicine> findAll() {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			// create a query
			Query<PaymentForMedicine> theQuery =
					currentSession.createQuery("from PaymentForMedicine", PaymentForMedicine.class);
			
			// execute query and get result list
			List<PaymentForMedicine> paymentsForMedicine = theQuery.getResultList();
			
			// return the results		
			return paymentsForMedicine;
		}
}
