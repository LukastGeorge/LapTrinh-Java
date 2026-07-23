package vn.edu.gdu.clinic.specialty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SpecialtyRequest(
        @NotBlank(message = "Tên chuyên khoa không được để trống")
        @Size(max = 150, message = "Tên chuyên khoa tối đa 150 ký tự")
        String name,

        @Size(max = 2000, message = "Mô tả tối đa 2000 ký tự")
        String description
) {
}
