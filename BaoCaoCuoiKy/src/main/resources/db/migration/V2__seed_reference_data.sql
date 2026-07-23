INSERT INTO roles(code, name, description) VALUES
    ('ADMIN', 'Quản trị viên', 'Quản trị toàn bộ hệ thống'),
    ('DOCTOR', 'Bác sĩ', 'Quản lý lịch làm việc và khám bệnh'),
    ('PATIENT', 'Bệnh nhân', 'Tra cứu bác sĩ và đặt lịch khám')
ON CONFLICT (code) DO NOTHING;

INSERT INTO specialties(name, description, status) VALUES
    ('Nội tổng quát', 'Khám và điều trị nội khoa tổng quát', 'ACTIVE'),
    ('Tim mạch', 'Khám và điều trị bệnh tim mạch', 'ACTIVE'),
    ('Da liễu', 'Khám và điều trị bệnh da liễu', 'ACTIVE'),
    ('Tai Mũi Họng', 'Khám và điều trị tai, mũi, họng', 'ACTIVE'),
    ('Nhi khoa', 'Khám và điều trị cho trẻ em', 'ACTIVE')
ON CONFLICT (name) DO NOTHING;
