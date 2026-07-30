package vn.edu.gdu.clinic.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.gdu.clinic.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhone(String phone);
}
