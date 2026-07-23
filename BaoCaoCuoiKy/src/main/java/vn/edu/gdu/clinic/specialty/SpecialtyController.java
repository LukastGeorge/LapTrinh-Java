package vn.edu.gdu.clinic.specialty;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.gdu.clinic.common.api.ApiResponse;

@RestController
@RequestMapping("/api/v1/specialties")
@RequiredArgsConstructor
@Tag(name = "Specialties", description = "API chuyên khoa")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @GetMapping
    @Operation(summary = "Lấy danh sách chuyên khoa có phân trang")
    public ApiResponse<Page<SpecialtyResponse>> findAll(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ApiResponse.success(
                HttpStatus.OK.value(),
                "Lấy danh sách chuyên khoa thành công",
                specialtyService.findAll(pageable)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết chuyên khoa")
    public ApiResponse<SpecialtyResponse> findById(@PathVariable Long id) {
        return ApiResponse.success(
                HttpStatus.OK.value(),
                "Lấy chuyên khoa thành công",
                specialtyService.findById(id)
        );
    }

    @PostMapping
    @Operation(summary = "Tạo chuyên khoa mới - tạm mở trong giai đoạn dựng khung")
    public ResponseEntity<ApiResponse<SpecialtyResponse>> create(
            @Valid @RequestBody SpecialtyRequest request) {
        SpecialtyResponse created = specialtyService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Tạo chuyên khoa thành công",
                        created
                ));
    }
}
