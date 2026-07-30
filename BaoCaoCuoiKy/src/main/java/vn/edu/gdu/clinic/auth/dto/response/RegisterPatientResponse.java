package vn.edu.gdu.clinic.auth.dto.response;

import vn.edu.gdu.clinic.user.enums.Gender;
import vn.edu.gdu.clinic.user.enums.UserStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegisterPatientResponse(
        Long userId,
        Long patientId,
        String patientCode,
        String fullName,
        String email,
        String phone,
        Gender gender,
        LocalDate dateOfBirth,
        UserStatus status,
        LocalDateTime createdAt
) {
}
