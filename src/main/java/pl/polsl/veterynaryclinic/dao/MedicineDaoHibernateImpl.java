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
import pl.polsl.veterynaryclinic.model.User;

@Repository
public class MedicineDaoHibernateImpl implements MedicineDao{
	// define field for entitymanager	
		private EntityManager entityManager;
			
		// set up constructor injection
		@Autowired
		public MedicineDaoHibernateImpl(EntityManager theEntityManager) {
			entityManager = theEntityManager;
		}
		
		
		@Override
		@Transactional
		public List<Medicine> findAll() {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			// create a query
			Query<Medicine> theQuery =
					currentSession.createQuery("from Medicine", Medicine.class);
			
			// execute query and get result list
			List<Medicine> medicines = theQuery.getResultList();
			
			// return the results		
			return medicines;
		}
		
		@Override
		@Transactional
		public boolean deleteMedicine(Integer medicineId) {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			String hql = "DELETE FROM leki "  + 
		             "WHERE id_leku = " + medicineId.toString();
			Query query = currentSession.createNativeQuery(hql);
			int result = query.executeUpdate();
			
			if (result == 1)
				return true;
			return false;
		}
		
		@Override
		@Transactional
		public Medicine findMedicine(Integer id) {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			// create a query
			Query<Medicine> theQuery =
					currentSession.createQuery("from Medicine where medicineId = " + id.toString(), Medicine.class);
			
			// execute query and get result list
			List<Medicine> medicines = theQuery.getResultList();
			
			// return the results		
			return medicines.get(0);
		}
		
		@Override
		@Transactional
		public boolean editMedicine(Integer medicineId, String medicineName, Integer purchasedItems, Integer remainingItems, String unit, Float purchasePrice, Date purchaseDate, Float soldItemsPrice) {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			String hql = "UPDATE leki set nazwa_leku = '"  + medicineName + "', liczba_zakupionych_sztuk = " + purchasedItems.toString() + ", liczba_pozostalych_sztuk = " + remainingItems.toString() + 
					", jednostka = '" + unit + "', cena_kupna = " + purchasePrice.toString() + ", data_kupna = '" + purchaseDate.toString() + "', cena_sp_szt = " + soldItemsPrice.toString() +
		             " WHERE id_leku = " + medicineId.toString();
			
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
		public boolean addMedicine(Integer medicineId, String medicineName, Integer purchasedItems, Integer remainingItems, String unit, Float purchasePrice, Date purchaseDate, Float soldItemsPrice) {

			// get the current hibernate session
			Session currentSession = entityManager.unwrap(Session.class);
			
			String hql = "INSERT INTO leki(nazwa_leku,liczba_zakupionych_sztuk,liczba_pozostalych_sztuk,jednostka,cena_kupna,data_kupna,cena_sp_szt) VALUES "
					+ "('" + medicineName + "',"+ purchasedItems.toString() +","+ remainingItems.toString() +",'"+ unit +"',"+ purchasePrice.toString() +",'"+ purchaseDate.toString() +"',"+ soldItemsPrice.toString() +")";
			Query query = currentSession.createNativeQuery(hql);
			int result = query.executeUpdate();
			
			if (result == 1)
				return true;
			return false;
		}
}
