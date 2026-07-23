package vn.edu.gdu.clinic.auth;

import jakarta.validation.constraints.*;
import vn.edu.gdu.clinic.user.Gender;

import java.time.LocalDate;

public record RegisterPatientRequest(

        @NotBlank(message = "Họ tên không được để trống")
        @Size(max = 150, message = "Họ tên tối đa 150 ký tự")
        String fullName,

        @NotBlank(message = "Email không được để trống")
        @Email(message = "Email không đúng định dạng")
        @Size(max = 150, message = "Email tối đa 150 ký tự")
        String email,

        @NotBlank(message = "Số điện thoại không được để trống")
        @Pattern(
                regexp = "^(0\\d{9}|\\+84\\d{9})$",
                message = "Số điện thoại phải có dạng 0xxxxxxxxx hoặc +84xxxxxxxxx"
        )
        String phone,

        @NotBlank(message = "Mật khẩu không được để trống")
        @Size(
                min = 8,
                max = 72,
                message = "Mật khẩu phải từ 8 đến 72 ký tự"
        )
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
                message = "Mật khẩu phải có chữ hoa, chữ thường và chữ số"
        )
        String password,

        @NotNull(message = "Giới tính không được để trống")
        Gender gender,

        @NotNull(message = "Ngày sinh không được để trống")
        @Past(message = "Ngày sinh phải là ngày trong quá khứ")
        LocalDate dateOfBirth,

        @Size(max = 255, message = "Địa chỉ tối đa 255 ký tự")
        String address,

        @Size(max = 50, message = "Mã bảo hiểm tối đa 50 ký tự")
        String healthInsuranceNumber,

        @Pattern(
                regexp = "^(A|B|AB|O)[+-]$",
                message = "Nhóm máu phải có dạng A+, A-, B+, B-, AB+, AB-, O+ hoặc O-"
        )
        String bloodType,

        @Size(max = 150, message = "Tên người liên hệ tối đa 150 ký tự")
        String emergencyContactName,

        @Pattern(
                regexp = "^(0\\d{9}|\\+84\\d{9})$",
                message = "Số điện thoại người liên hệ không đúng định dạng"
        )
        String emergencyContactPhone,

        @Size(max = 2000, message = "Tiền sử bệnh tối đa 2000 ký tự")
        String medicalHistory
) {
}