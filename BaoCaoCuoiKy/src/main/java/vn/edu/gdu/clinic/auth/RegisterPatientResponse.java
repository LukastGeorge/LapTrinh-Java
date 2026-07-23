package vn.edu.gdu.clinic.auth;

import vn.edu.gdu.clinic.user.Gender;
import vn.edu.gdu.clinic.user.UserStatus;

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