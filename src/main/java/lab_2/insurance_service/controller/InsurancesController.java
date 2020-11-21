package lab_2.insurance_service.controller;

import lab_2.insurance_service.entities.Insurance;
import lab_2.insurance_service.repositories.InsuranceRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InsurancesController {
    final
    InsuranceRepository insuranceRepository;
    private static final Logger logger = LogManager.getLogger(InsurancesController.class);

    public InsurancesController(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }


    @GetMapping("/insurances")
    ResponseEntity<List<Insurance>> getInsurancesByUsersId(@RequestParam(required = false) Integer userId) {
        List<Insurance> insuranceList;
        if (userId != null) {
            insuranceList = insuranceRepository.findInsuranceByUserId(userId);
        } else {
            insuranceList = insuranceRepository.findAll();
        }
        if (insuranceList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(insuranceList, HttpStatus.OK);
    }

    @GetMapping("/insurances/{id}")
    ResponseEntity<Insurance> getInsuranceById(@PathVariable Integer id) {
        Insurance insurance = insuranceRepository.findInsuranceById(id);
        if (insurance == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(insurance, HttpStatus.OK);
    }

    @PostMapping("/insurances")
    ResponseEntity<Insurance> createInsurance(@RequestBody Insurance newInsurance) {
        Insurance insurance = insuranceRepository.save(newInsurance);
        return new ResponseEntity<>(insurance, HttpStatus.CREATED);
    }

    @PutMapping("/insurances/{id}")
    ResponseEntity<Insurance> updateInsurance(@RequestBody Insurance updatedInsurance, @PathVariable Integer id) {
        Insurance insurance = insuranceRepository.findInsuranceById(id);
        if (insurance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            insurance.setCost(updatedInsurance.getCost());
            insurance.setType(updatedInsurance.getType());
            insurance.setUserId(updatedInsurance.getUserId());
            insuranceRepository.save(insurance);
            return new ResponseEntity<>(insurance, HttpStatus.OK);
        }
    }

    @DeleteMapping("/insurances/{id}")
    ResponseEntity<?> deleteInsurance(@PathVariable Integer id) {
        insuranceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
