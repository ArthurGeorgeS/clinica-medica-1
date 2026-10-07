-- Script SQL - Sistema de Clínica (compatível com H2, PostgreSQL e MySQL 8)
-- Por padrão a aplicação cria as tabelas sozinha (ddl-auto=update). Use este script para criar o banco manualmente.

CREATE TABLE usuario (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(100) NOT NULL UNIQUE,
  senha VARCHAR(100) NOT NULL,            -- hash BCrypt
  perfil VARCHAR(20) NOT NULL,            -- ADMIN | MEDICO | FUNCIONARIO | PACIENTE
  ativo BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE paciente (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(150) NOT NULL, cpf VARCHAR(20) UNIQUE, data_nascimento DATE,
  telefone VARCHAR(30), email VARCHAR(150), login VARCHAR(100)
);
CREATE TABLE medico (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(150) NOT NULL, crm VARCHAR(30) NOT NULL UNIQUE,
  especialidade VARCHAR(100), telefone VARCHAR(30), login VARCHAR(100)
);
CREATE TABLE funcionario (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(150) NOT NULL, cpf VARCHAR(20) UNIQUE,
  cargo VARCHAR(100), telefone VARCHAR(30), login VARCHAR(100)
);
CREATE TABLE consulta (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  paciente_id BIGINT NOT NULL, medico_id BIGINT NOT NULL,
  data_hora TIMESTAMP NOT NULL, motivo VARCHAR(255), status VARCHAR(20) DEFAULT 'AGENDADA',
  FOREIGN KEY (paciente_id) REFERENCES paciente(id),
  FOREIGN KEY (medico_id) REFERENCES medico(id)
);
CREATE TABLE prontuario (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  consulta_id BIGINT NOT NULL,
  descricao VARCHAR(4000), diagnostico VARCHAR(1000), prescricao VARCHAR(1000),
  FOREIGN KEY (consulta_id) REFERENCES consulta(id)
);

-- Dados iniciais: administrador (login: admin / senha: admin123)
INSERT INTO usuario (login, senha, perfil, ativo) VALUES ('admin', '$2a$10$0FtqyZtpJib51tPnCEjyjeFjLC9XjTbV.gnK8b8VxfB/IJidxI82G', 'ADMIN', TRUE);
