DROP TABLE patients IF EXISTS;
DROP TABLE doctors IF EXISTS;
DROP TABLE prescriptions IF EXISTS;

CREATE TABLE patients (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    last_name varchar(50) NOT NULL,
    first_name varchar(50) NOT NULL,
    middle_name varchar(50),
    phone varchar(12) NOT NULL
);

CREATE TABLE doctors (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    last_name varchar(50) NOT NULL,
    first_name varchar(50) NOT NULL,
    middle_name varchar(50),
    specialization varchar(50) NOT NULL
);

CREATE TABLE prescriptions (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    description varchar(500) NOT NULL,
    patient_id bigint NOT NULL REFERENCES patients (id),
    doctor_id bigint NOT NULL REFERENCES doctors (id),
    created date NOT NULL,
    validity date NOT NULL,
    priority_id bigint NOT NULL REFERENCES priorities (id)
);

CREATE TABLE priorities (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    priority varchar(50) NOT NULL
);