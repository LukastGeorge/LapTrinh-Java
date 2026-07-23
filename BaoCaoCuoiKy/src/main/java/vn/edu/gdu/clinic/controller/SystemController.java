package vn.edu.gdu.clinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.gdu.clinic.common.api.ApiResponse;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/public")
public class SystemController {

    @GetMapping("/ping")
    public ApiResponse<Map<String, String>> ping() {
        return ApiResponse.success(
                HttpStatus.OK.value(),
                "Backend đang hoạt động",
                Map.of("service", "clinic-management", "status", "UP")
        );
    }
}
