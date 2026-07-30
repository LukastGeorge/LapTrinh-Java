package vn.edu.gdu.clinic.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.gdu.clinic.patient.entity.Patient;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUserId(Long userId);

    boolean existsByPatientCode(String patientCode);

    boolean existsByHealthInsuranceNumber(String healthInsuranceNumber);
}
