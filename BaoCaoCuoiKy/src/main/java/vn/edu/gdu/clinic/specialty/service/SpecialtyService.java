package vn.edu.gdu.clinic.specialty.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.gdu.clinic.specialty.dto.request.SpecialtyRequest;
import vn.edu.gdu.clinic.specialty.dto.response.SpecialtyResponse;

public interface SpecialtyService {
    Page<SpecialtyResponse> findAll(Pageable pageable);
    SpecialtyResponse findById(Long id);
    SpecialtyResponse create(SpecialtyRequest request);
}
