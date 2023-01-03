CREATE SCHEMA IF NOT EXISTS portail_formation;

CREATE TABLE modules (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE courses (
    id        serial PRIMARY KEY,
    name      text NOT NULL,
    file_name      VARCHAR(100) NOT NULL,

    module_id integer REFERENCES modules (id) ON DELETE CASCADE
);

ALTER TABLE courses
    ADD CONSTRAINT fk_courses_modules
        FOREIGN KEY (module_id) REFERENCES modules (id);