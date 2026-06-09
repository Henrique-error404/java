-- ============================================================
-- script-bd.sql | DDL TerraOrbit | Azure SQL (SQL Server)
-- As tabelas tambem sao criadas automaticamente pelo Hibernate
-- (ddl-auto: update). Este script documenta o schema do banco.
-- ============================================================

IF OBJECT_ID('TO_AI_RECOMMENDATIONS','U') IS NOT NULL DROP TABLE TO_AI_RECOMMENDATIONS;
IF OBJECT_ID('TO_CLIMATE_ALERTS','U')     IS NOT NULL DROP TABLE TO_CLIMATE_ALERTS;
IF OBJECT_ID('TO_INCIDENTS','U')          IS NOT NULL DROP TABLE TO_INCIDENTS;
IF OBJECT_ID('TO_SENSORS','U')            IS NOT NULL DROP TABLE TO_SENSORS;
IF OBJECT_ID('TO_FARMS','U')              IS NOT NULL DROP TABLE TO_FARMS;
IF OBJECT_ID('TO_USERS','U')              IS NOT NULL DROP TABLE TO_USERS;

CREATE TABLE TO_USERS (
    id            BIGINT IDENTITY(1,1) PRIMARY KEY,
    name          NVARCHAR(255) NOT NULL,
    email         NVARCHAR(255) NOT NULL UNIQUE,
    password_hash NVARCHAR(255) NOT NULL,
    user_role     NVARCHAR(20)  NOT NULL,
    created_at    DATETIME2
);

CREATE TABLE TO_FARMS (
    id                 BIGINT IDENTITY(1,1) PRIMARY KEY,
    farm_name          NVARCHAR(255) NOT NULL,
    location           NVARCHAR(255),
    farm_size_hectares DECIMAL(18,2),
    owner_id           BIGINT,
    created_at         DATETIME2,
    CONSTRAINT fk_farms_owner FOREIGN KEY (owner_id) REFERENCES TO_USERS(id)
);

CREATE TABLE TO_SENSORS (
    id            BIGINT IDENTITY(1,1) PRIMARY KEY,
    sensor_name   NVARCHAR(255) NOT NULL,
    sensor_type   NVARCHAR(30)  NOT NULL,
    sensor_status NVARCHAR(30)  NOT NULL,
    last_reading  DECIMAL(18,2),
    installed_at  DATETIME2,
    farm_id       BIGINT,
    CONSTRAINT fk_sensors_farm FOREIGN KEY (farm_id) REFERENCES TO_FARMS(id)
);

CREATE TABLE TO_INCIDENTS (
    id                   BIGINT IDENTITY(1,1) PRIMARY KEY,
    incident_type        NVARCHAR(255) NOT NULL,
    incident_description NVARCHAR(1000),
    incident_date        DATETIME2,
    incident_status      NVARCHAR(30),
    farm_id              BIGINT,
    CONSTRAINT fk_incidents_farm FOREIGN KEY (farm_id) REFERENCES TO_FARMS(id)
);

CREATE TABLE TO_CLIMATE_ALERTS (
    id         BIGINT IDENTITY(1,1) PRIMARY KEY,
    alert_type NVARCHAR(50),
    severity   NVARCHAR(20),
    message    NVARCHAR(1000),
    alert_date DATETIME2,
    farm_id    BIGINT,
    CONSTRAINT fk_alerts_farm FOREIGN KEY (farm_id) REFERENCES TO_FARMS(id)
);

CREATE TABLE TO_AI_RECOMMENDATIONS (
    id             BIGINT IDENTITY(1,1) PRIMARY KEY,
    recommendation NVARCHAR(2000),
    risk_level     NVARCHAR(20),
    generated_at   DATETIME2,
    farm_id        BIGINT,
    CONSTRAINT fk_reco_farm FOREIGN KEY (farm_id) REFERENCES TO_FARMS(id)
);
