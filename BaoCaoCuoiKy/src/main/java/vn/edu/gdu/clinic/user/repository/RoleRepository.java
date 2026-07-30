package vn.edu.gdu.clinic.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.gdu.clinic.user.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByCode(String code);
}
