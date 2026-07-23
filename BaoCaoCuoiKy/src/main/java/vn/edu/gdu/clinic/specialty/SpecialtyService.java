package vn.edu.gdu.clinic.specialty;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecialtyService {
    Page<SpecialtyResponse> findAll(Pageable pageable);
    SpecialtyResponse findById(Long id);
    SpecialtyResponse create(SpecialtyRequest request);
}
