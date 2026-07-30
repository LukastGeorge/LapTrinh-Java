package vn.edu.gdu.clinic.specialty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.gdu.clinic.specialty.entity.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    boolean existsByNameIgnoreCase(String name);
}
