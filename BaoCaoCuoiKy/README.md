# Clinic Management System - Nhóm 15

Hệ thống quản lý và đặt lịch khám bệnh bằng Spring Boot, PostgreSQL và Docker Compose.

## Phạm vi triển khai đầu tiên

Bản starter này tập trung vào nền móng chạy được:

- Spring Boot 3.5.4, Java target 21
- Layered Architecture
- PostgreSQL 16
- Flyway migration
- Dockerfile và Docker Compose
- Swagger/OpenAPI
- Actuator health check
- Global Exception Handling
- API chuyên khoa mẫu có Paging và Validation

Redis, email queue và thanh toán trong sơ đồ được giữ cho giai đoạn nâng cao sau khi nghiệp vụ lõi chạy ổn định.

## Cách chạy nhanh bằng Docker

```powershell
Copy-Item .env.example .env
docker compose up --build -d
docker compose ps
docker compose logs -f backend
```

Mở:

- Health: http://localhost:8080/actuator/health
- Ping: http://localhost:8080/api/v1/public/ping
- Swagger: http://localhost:8080/swagger-ui.html
- Specialties: http://localhost:8080/api/v1/specialties

## Kiểm tra PostgreSQL trong container

```powershell
docker exec -it clinic-postgres psql -U clinic_app -d clinic_management
```

Trong psql:

```sql
\dt
SELECT * FROM specialties;
\q
```

## Chạy backend trên Windows, PostgreSQL bằng Docker

Chỉ khởi động database:

```powershell
docker compose up -d postgres
mvn clean spring-boot:run
```

## API mẫu

Lấy danh sách:

```http
GET /api/v1/specialties?page=0&size=10&sort=name,asc
```

Tạo mới:

```http
POST /api/v1/specialties
Content-Type: application/json

{
  "name": "Răng Hàm Mặt",
  "description": "Khám và điều trị răng hàm mặt"
}
```

## Lộ trình code tiếp theo

1. Auth + JWT + phân quyền PATIENT/DOCTOR/ADMIN
2. User, Patient, Doctor
3. DoctorSchedule và ScheduleSlot
4. Appointment với Pessimistic Lock và Transaction
5. MedicalRecord và Prescription
6. Redis cache, email async, thanh toán nếu còn thời gian
