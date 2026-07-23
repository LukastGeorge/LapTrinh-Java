package vn.edu.gdu.clinic.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.gdu.clinic.common.api.ApiResponse;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API đăng ký và đăng nhập")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Đăng ký tài khoản bệnh nhân")
    public ResponseEntity<ApiResponse<RegisterPatientResponse>> register(
            @Valid @RequestBody RegisterPatientRequest request
    ) {
        RegisterPatientResponse response =
                authService.registerPatient(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Đăng ký tài khoản bệnh nhân thành công",
                        response
                ));
    }
}