INSERT INTO roles(code, name, description)
VALUES (
           'RECEPTIONIST',
           'Lễ tân',
           'Xác nhận, check-in và quản lý lịch khám tại quầy'
       )
    ON CONFLICT (code) DO NOTHING;