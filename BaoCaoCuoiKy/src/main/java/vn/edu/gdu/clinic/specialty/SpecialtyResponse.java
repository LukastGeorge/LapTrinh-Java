package vn.edu.gdu.clinic.specialty;

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
