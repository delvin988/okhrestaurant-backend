CREATE TABLE bookings (
    id BIGSERIAL PRIMARY KEY,

    customer_name VARCHAR(255),

    phone VARCHAR(255),

    email VARCHAR(255),

    booking_time TIMESTAMP,

    number_of_people INTEGER,

    special_request TEXT,

    want_menu BOOLEAN,

    selected_menus TEXT,

    status VARCHAR(100),

    created_at TIMESTAMP,

    updated_at TIMESTAMP
);

CREATE TABLE otp (
    id BIGSERIAL PRIMARY KEY,

    phone VARCHAR(255),

    code VARCHAR(20),

    expired_at TIMESTAMP,

    verified BOOLEAN
);