package pl.polsl.veterynaryclinic.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import pl.polsl.veterynaryclinic.model.CompanyData;

public interface CompanyDataRepository extends CrudRepository<CompanyData, Integer>, JpaSpecificationExecutor<CompanyData> {
    CompanyData findByCompanyDataId(Integer companyDataId);
}
