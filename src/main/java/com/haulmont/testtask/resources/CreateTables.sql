DROP TABLE patients IF EXISTS;
DROP TABLE doctors IF EXISTS;
DROP TABLE prescriptions IF EXISTS;

CREATE TABLE patients (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    surname varchar(50) NOT NULL,
    name varchar(50) NOT NULL,
    patronymic varchar(50) NOT NULL,
    phone varchar(11) NOT NULL
);

CREATE TABLE doctors (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    surname varchar(50) NOT NULL,
    name varchar(50) NOT NULL,
    patronymic varchar(50) NOT NULL,
    specialization varchar(50) NOT NULL
);

CREATE TABLE prescriptions (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    description varchar(500) NOT NULL,
    patient_id bigint REFERENCES patients (id),
    doctor_id bigint REFERENCES doctors (id),
    created date NOT NULL,
    validity date NOT NULL,
    priority varchar(50) NOT NULL
);