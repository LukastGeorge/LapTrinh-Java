CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL REFERENCES roles(id),
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20) UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    gender VARCHAR(20),
    date_of_birth DATE,
    address VARCHAR(255),
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT ck_users_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'LOCKED'))
);

CREATE TABLE patients (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id),
    patient_code VARCHAR(30) NOT NULL UNIQUE,
    health_insurance_number VARCHAR(50) UNIQUE,
    blood_type VARCHAR(10),
    emergency_contact_name VARCHAR(150),
    emergency_contact_phone VARCHAR(20),
    medical_history TEXT
);

CREATE TABLE specialties (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT ck_specialties_status CHECK (status IN ('ACTIVE', 'INACTIVE'))
);

CREATE TABLE doctors (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id),
    specialty_id BIGINT NOT NULL REFERENCES specialties(id),
    doctor_code VARCHAR(30) NOT NULL UNIQUE,
    degree VARCHAR(100),
    experience_years INTEGER NOT NULL DEFAULT 0,
    consultation_fee NUMERIC(12,2) NOT NULL DEFAULT 0,
    biography TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT ck_doctors_experience CHECK (experience_years >= 0),
    CONSTRAINT ck_doctors_fee CHECK (consultation_fee >= 0),
    CONSTRAINT ck_doctors_status CHECK (status IN ('ACTIVE', 'ON_LEAVE', 'INACTIVE'))
);

CREATE TABLE doctor_schedules (
    id BIGSERIAL PRIMARY KEY,
    doctor_id BIGINT NOT NULL REFERENCES doctors(id),
    work_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    slot_duration_minutes INTEGER NOT NULL,
    room_number VARCHAR(30),
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_schedule_time CHECK (start_time < end_time),
    CONSTRAINT ck_schedule_duration CHECK (slot_duration_minutes > 0),
    CONSTRAINT ck_schedule_status CHECK (status IN ('ACTIVE', 'CANCELLED', 'COMPLETED'))
);

CREATE TABLE schedule_slots (
    id BIGSERIAL PRIMARY KEY,
    schedule_id BIGINT NOT NULL REFERENCES doctor_schedules(id) ON DELETE CASCADE,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'AVAILABLE',
    CONSTRAINT uq_schedule_slot UNIQUE (schedule_id, start_time),
    CONSTRAINT ck_slot_time CHECK (start_time < end_time),
    CONSTRAINT ck_slot_status CHECK (status IN ('AVAILABLE', 'BOOKED', 'BLOCKED'))
);

CREATE TABLE appointments (
    id BIGSERIAL PRIMARY KEY,
    appointment_code VARCHAR(30) NOT NULL UNIQUE,
    patient_id BIGINT NOT NULL REFERENCES patients(id),
    slot_id BIGINT NOT NULL REFERENCES schedule_slots(id),
    booked_by_user_id BIGINT REFERENCES users(id),
    reason VARCHAR(500) NOT NULL,
    symptoms TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    patient_note TEXT,
    staff_note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    confirmed_at TIMESTAMP,
    cancelled_at TIMESTAMP,
    cancellation_reason VARCHAR(500),
    CONSTRAINT ck_appointment_status CHECK (
        status IN ('PENDING', 'CONFIRMED', 'CHECKED_IN', 'COMPLETED', 'CANCELLED', 'NO_SHOW')
    )
);

CREATE UNIQUE INDEX uq_active_appointment_slot
    ON appointments(slot_id)
    WHERE status IN ('PENDING', 'CONFIRMED', 'CHECKED_IN');

CREATE INDEX idx_appointments_patient_id ON appointments(patient_id);
CREATE INDEX idx_doctor_schedules_doctor_date ON doctor_schedules(doctor_id, work_date);
CREATE INDEX idx_schedule_slots_schedule_status ON schedule_slots(schedule_id, status);

CREATE TABLE medical_records (
    id BIGSERIAL PRIMARY KEY,
    appointment_id BIGINT NOT NULL UNIQUE REFERENCES appointments(id),
    diagnosis TEXT NOT NULL,
    conclusion TEXT,
    treatment TEXT,
    advice TEXT,
    blood_pressure VARCHAR(20),
    temperature NUMERIC(4,1),
    weight NUMERIC(5,2),
    height NUMERIC(5,2),
    follow_up_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE prescriptions (
    id BIGSERIAL PRIMARY KEY,
    medical_record_id BIGINT NOT NULL UNIQUE REFERENCES medical_records(id),
    note TEXT,
    prescribed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE prescription_items (
    id BIGSERIAL PRIMARY KEY,
    prescription_id BIGINT NOT NULL REFERENCES prescriptions(id) ON DELETE CASCADE,
    medicine_name VARCHAR(200) NOT NULL,
    dosage VARCHAR(100) NOT NULL,
    frequency VARCHAR(100) NOT NULL,
    duration_days INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    usage_instruction VARCHAR(500),
    CONSTRAINT ck_prescription_duration CHECK (duration_days > 0),
    CONSTRAINT ck_prescription_quantity CHECK (quantity > 0)
);
