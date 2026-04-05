
CREATE SCHEMA IF NOT EXISTS users_schema;

CREATE TABLE users_schema.users_table (
                       user_id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       pref_category VARCHAR(255)
);
CREATE SCHEMA IF NOT EXISTS event_schema;

CREATE TABLE event_schema.event_table (
                             event_id BIGSERIAL PRIMARY KEY,
                             event_name VARCHAR(255) NOT NULL,
                             category VARCHAR(255) NOT NULL,
                             location VARCHAR(255) NOT NULL,
                             event_date DATE NOT NULL,
                             price DOUBLE PRECISION NOT NULL,
                             tickets_count BIGINT NOT NULL
);
CREATE SCHEMA IF NOT EXISTS booking_schema;

CREATE TABLE booking_schema.booking_table (
                               booking_id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               event_id BIGINT NOT NULL,
                               booking_date DATE NOT NULL,
                               CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users_schema.users_table(user_id),
                               CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES event_schema.event_table(event_id)
);




CREATE SCHEMA IF NOT EXISTS outbox_schema;

CREATE TABLE outbox_schema.outbox_table (
    id BIGSERIAL PRIMARY KEY,
    book_id VARCHAR(255),
    payload TEXT,
    topic VARCHAR(255),
    processed BOOLEAN DEFAULT FALSE,
    date_now DATE DEFAULT CURRENT_DATE
);