package vn.edu.gdu.clinic.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI clinicOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clinic Management API")
                        .version("v1")
                        .description("RESTful API cho hệ thống quản lý và đặt lịch khám bệnh - Nhóm 15"));
    }
}
