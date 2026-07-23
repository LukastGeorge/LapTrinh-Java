# Kế hoạch triển khai trong đêm

## Chặng 1 - Nền móng chạy được
- Docker PostgreSQL healthy
- Backend build và chạy
- Flyway tạo đủ 11 bảng
- Swagger truy cập được
- API chuyên khoa hoạt động

## Chặng 2 - Người dùng và bảo mật
- Role, User, Patient, Doctor
- Đăng ký bệnh nhân
- Đăng nhập và JWT
- Phân quyền PATIENT / DOCTOR / ADMIN

## Chặng 3 - Lịch làm việc
- DoctorSchedule
- ScheduleSlot
- Tạo slot theo thời lượng
- Xem slot AVAILABLE

## Chặng 4 - Đặt lịch
- Appointment
- Pessimistic lock SELECT FOR UPDATE
- Transaction
- Không đặt trùng
- Hủy lịch và mở lại slot

## Chặng 5 - Khám bệnh
- MedicalRecord
- Prescription
- PrescriptionItem

## Chặng 6 - Hoàn thiện
- Paging, Sorting
- Swagger mô tả endpoint
- Postman collection
- Docker Compose
- README và demo data
