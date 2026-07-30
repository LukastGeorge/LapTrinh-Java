package vn.edu.gdu.clinic.specialty.dto.response;

import vn.edu.gdu.clinic.specialty.enums.SpecialtyStatus;

import java.time.LocalDateTime;

public record SpecialtyResponse(
        Long id,
        String name,
        String description,
        SpecialtyStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
