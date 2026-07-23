package vn.edu.gdu.clinic.specialty;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.gdu.clinic.exception.ConflictException;
import vn.edu.gdu.clinic.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<SpecialtyResponse> findAll(Pageable pageable) {
        return specialtyRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialtyResponse findById(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy chuyên khoa với id = " + id));
        return toResponse(specialty);
    }

    @Override
    @Transactional
    public SpecialtyResponse create(SpecialtyRequest request) {
        String normalizedName = request.name().trim();
        if (specialtyRepository.existsByNameIgnoreCase(normalizedName)) {
            throw new ConflictException("Tên chuyên khoa đã tồn tại");
        }

        Specialty specialty = Specialty.builder()
                .name(normalizedName)
                .description(request.description())
                .status(SpecialtyStatus.ACTIVE)
                .build();

        return toResponse(specialtyRepository.save(specialty));
    }

    private SpecialtyResponse toResponse(Specialty specialty) {
        return new SpecialtyResponse(
                specialty.getId(),
                specialty.getName(),
                specialty.getDescription(),
                specialty.getStatus(),
                specialty.getCreatedAt(),
                specialty.getUpdatedAt()
        );
    }
}
