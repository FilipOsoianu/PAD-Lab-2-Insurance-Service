package lab_2.insurance_service.repositories;

import lab_2.insurance_service.entities.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
    Insurance findInsuranceById(int id);
    List<Insurance> findInsuranceByUserId(int userId);
    void deleteById(int id);
}
