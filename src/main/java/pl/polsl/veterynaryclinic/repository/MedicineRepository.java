package pl.polsl.veterynaryclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.polsl.veterynaryclinic.model.Medicine;
import pl.polsl.veterynaryclinic.model.nondatabase.MedicineCounter;

import java.util.List;

public interface MedicineRepository extends CrudRepository<Medicine, Integer> {

    List<Medicine> findAllByMedicineNameAndRemainingItemsGreaterThanOrderByPurchaseDate(String medicineName, Integer remainingItems);

    @Query("SELECT new pl.polsl.veterynaryclinic.model.nondatabase.MedicineCounter(m.medicineName, SUM(m.remainingItems), m.soldItemsPrice) " +
            "FROM Medicine m " +
            "WHERE m.remainingItems > 0 " +
            "GROUP BY m.medicineName ")
    List<MedicineCounter> findMedicineCountList();

}
