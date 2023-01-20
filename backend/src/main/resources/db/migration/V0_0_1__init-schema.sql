CREATE SCHEMA IF NOT EXISTS portail_formation;

CREATE TABLE modules (
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(200) NOT NULL
);

CREATE TABLE courses (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    file_name   VARCHAR(100) NOT NULL,

    module_id   BIGINT NOT NULL,
    CONSTRAINT fk_courses_modules FOREIGN KEY (module_id) REFERENCES modules (id) ON DELETE CASCADE;
);
