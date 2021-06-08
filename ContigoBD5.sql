drop database if exists contigoBD;
create database contigoBD;
use contigoBD;
CREATE TABLE DEPARTAMENTO (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(200) NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE METODO_PAGO (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(70) NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE TIPO_DOCUMENTO (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  tipo VARCHAR(25) NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE CLASIFICACION (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  grado VARCHAR(50) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE MUNICIPIO (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  DEPARTAMENTO_id INTEGER UNSIGNED NOT NULL,
  nombre VARCHAR(200) NULL,
  PRIMARY KEY(id),
  INDEX MUNICIPIO_FKIndex1(DEPARTAMENTO_id),
  FOREIGN KEY(DEPARTAMENTO_id)
    REFERENCES DEPARTAMENTO(id)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE PERSONA (
  documento VARCHAR(20) NOT NULL,
  TIPO_DOCUMENTO_id INTEGER UNSIGNED NOT NULL,
  primerNombre VARCHAR(30) NOT NULL,
  segundoNombre VARCHAR(30) NULL,
  primerApellido VARCHAR(30) NOT NULL,
  segundoApellido VARCHAR(30) NULL,
  token VARCHAR(200) NOT NULL unique,
  fechaNacimiento DATE NOT NULL,
  contraseña VARCHAR(200) NOT NULL,
  genero VARCHAR(15) NULL,
  correo VARCHAR(80) NOT NULL unique,
  PRIMARY KEY(documento),
  INDEX PERSONA_FKIndex1(TIPO_DOCUMENTO_id),
  FOREIGN KEY(TIPO_DOCUMENTO_id)
    REFERENCES TIPO_DOCUMENTO(id)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE PERSONAL (
  PERSONA_documento VARCHAR(20) NOT NULL,
  imagen VARCHAR(240) NOT NULL,
  PRIMARY KEY(PERSONA_documento),
  INDEX PERSONAL_FKIndex1(PERSONA_documento),
  FOREIGN KEY(PERSONA_documento)
    REFERENCES PERSONA(documento)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE INSTITUCION (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  MUNICIPIO_id INTEGER UNSIGNED NOT NULL,
  METODO_PAGO_id INTEGER UNSIGNED NOT NULL,
  nombre VARCHAR(200) NOT NULL,
  correo VARCHAR(80) NOT NULL,
  direccion VARCHAR(60) NOT NULL,
  tipoInstitucion BOOL NOT NULL,
  calendario BOOL NULL,
  barrio VARCHAR(90) NULL,
  telefono VARCHAR(13) NULL,
  contraseña VARCHAR(200) NOT NULL,
  web VARCHAR(50) NULL,
  PRIMARY KEY(id),
  INDEX INSTITUCION_FKIndex1(MUNICIPIO_id),
  INDEX INSTITUCION_FKIndex2(METODO_PAGO_id),
  FOREIGN KEY(MUNICIPIO_id)
    REFERENCES MUNICIPIO(id)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(METODO_PAGO_id)
    REFERENCES METODO_PAGO(id)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE GRADO (
  codigo VARCHAR(30) NOT NULL,
  CLASIFICACION_id INTEGER UNSIGNED NOT NULL,
  INSTITUCION_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(codigo),
  INDEX GRADO_FKIndex1(INSTITUCION_id),
  INDEX GRADO_FKIndex2(CLASIFICACION_id),
  FOREIGN KEY(INSTITUCION_id)
    REFERENCES INSTITUCION(id)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(CLASIFICACION_id)
    REFERENCES CLASIFICACION(id)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE HISTORIA (
  idHistoria INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  PERSONAL_PERSONA_documento VARCHAR(20) NOT NULL,
  titulo VARCHAR(50) NOT NULL,
  descripcion VARCHAR(600) NOT NULL,
  urlImagen VARCHAR(200) NOT NULL,
  PRIMARY KEY(idHistoria),
  INDEX HISTORIA_FKIndex1(PERSONAL_PERSONA_documento),
  FOREIGN KEY(PERSONAL_PERSONA_documento)
    REFERENCES PERSONAL(PERSONA_documento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE SITUACION (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  SITUACION_id INTEGER UNSIGNED NULL,
  HISTORIA_idHistoria INTEGER UNSIGNED NOT NULL,
  titulo VARCHAR(50) null,
  texto VARCHAR(600),
  PRIMARY KEY(id),
  INDEX SITUACION_FKIndex1(HISTORIA_idHistoria),
  INDEX SITUACION_FKIndex2(SITUACION_id),
  FOREIGN KEY(HISTORIA_idHistoria)
    REFERENCES HISTORIA(idHistoria)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(SITUACION_id)
    REFERENCES SITUACION(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);
CREATE TABLE CONVERSATORIO (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  PERSONAL_PERSONA_documento VARCHAR(20) NOT NULL,
  titulo VARCHAR(250) NOT NULL,
  cronograma VARCHAR(250) NOT NULL,
  imagen VARCHAR(250) NULL,
  descripcion VARCHAR(350) NOT NULL,
  lugar VARCHAR(250) NOT NULL,
  infografia VARCHAR(300) NOT NULL,
  PRIMARY KEY(id),
  INDEX CONVERSATORIO_FKIndex1(PERSONAL_PERSONA_documento),
  FOREIGN KEY(PERSONAL_PERSONA_documento)
    REFERENCES PERSONAL(PERSONA_documento)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE ESTUDIANTE (
  PERSONA_documento VARCHAR(20) NOT NULL,
  GRADO_codigo VARCHAR(30) NOT NULL,
  PRIMARY KEY(PERSONA_documento),
  INDEX ESTUDIANTE_FKIndex1(GRADO_codigo),
  INDEX ESTUDIANTE_FKIndex2(PERSONA_documento),
  FOREIGN KEY(GRADO_codigo)
    REFERENCES GRADO(codigo)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(PERSONA_documento)
    REFERENCES PERSONA(documento)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE CLASIFICACION_has_CONVERSATORIO (
  CLASIFICACION_id INTEGER UNSIGNED NOT NULL,
  CONVERSATORIO_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(CLASIFICACION_id, CONVERSATORIO_id),
  INDEX CLASIFICACION_has_CONVERSATORIO_FKIndex1(CLASIFICACION_id),
  INDEX CLASIFICACION_has_CONVERSATORIO_FKIndex2(CONVERSATORIO_id),
  FOREIGN KEY(CLASIFICACION_id)
    REFERENCES CLASIFICACION(id)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(CONVERSATORIO_id)
    REFERENCES CONVERSATORIO(id)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE CLASIFICACION_has_HISTORIA (
  CLASIFICACION_id INTEGER UNSIGNED NOT NULL,
  HISTORIA_idHistoria INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(CLASIFICACION_id, HISTORIA_idHistoria),
  INDEX CLASIFICACION_has_CONVERSATORIO_FKIndex1(CLASIFICACION_id),
  INDEX CLASIFICACION_has_CONVERSATORIO_FKIndex2(HISTORIA_idHistoria),
  FOREIGN KEY(CLASIFICACION_id)
    REFERENCES CLASIFICACION(id)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(HISTORIA_idHistoria)
    REFERENCES HISTORIA(idHistoria)
      ON DELETE cascade
      ON UPDATE cascade
);


CREATE TABLE ESTUDIANTE_has_CONVERSATORIO (
  CONVERSATORIO_id INTEGER UNSIGNED NOT NULL,
  ESTUDIANTE_PERSONA_documento VARCHAR(20) NOT NULL,
  PRIMARY KEY(CONVERSATORIO_id, ESTUDIANTE_PERSONA_documento),
  INDEX ESTUDIANTE_has_CONVERSATORIO_FKIndex1(ESTUDIANTE_PERSONA_documento),
  INDEX ESTUDIANTE_has_CONVERSATORIO_FKIndex2(CONVERSATORIO_id),
  FOREIGN KEY(ESTUDIANTE_PERSONA_documento)
    REFERENCES ESTUDIANTE(PERSONA_documento)
      ON DELETE cascade
      ON UPDATE cascade,
  FOREIGN KEY(CONVERSATORIO_id)
    REFERENCES CONVERSATORIO(id)
      ON DELETE cascade
      ON UPDATE cascade
);

CREATE TABLE ESTUDIANTE_has_HISTORIA (
  ESTUDIANTE_PERSONA_documento VARCHAR(20) NOT NULL,
  HISTORIA_idHistoria INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(ESTUDIANTE_PERSONA_documento, HISTORIA_idHistoria),
  INDEX ESTUDIANTE_has_HISTORIA_FKIndex1(ESTUDIANTE_PERSONA_documento),
  INDEX ESTUDIANTE_has_HISTORIA_FKIndex2(HISTORIA_idHistoria),
  FOREIGN KEY(ESTUDIANTE_PERSONA_documento)
    REFERENCES ESTUDIANTE(PERSONA_documento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(HISTORIA_idHistoria)
    REFERENCES HISTORIA(idHistoria)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE ESTADISTICAS_BTNPANICO (
    ESTUDIANTE_PERSONA_documento VARCHAR (20),
    FECHA timestamp,
    FOREIGN KEY (ESTUDIANTE_PERSONA_documento)
    REFERENCES ESTUDIANTE(PERSONA_documento)
        ON DELETE cascade
        ON UPDATE cascade
);

CREATE TABLE AGENDA (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  PERSONAL_PERSONA_documento VARCHAR(20) NOT NULL,
  fechaInicio DATE NOT NULL,
  fechaFin DATE NOT NULL,
  horaInicio INTEGER UNSIGNED NOT NULL,
  horaFin INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id),
  INDEX AGENDA_FKIndex1(PERSONAL_PERSONA_documento),
  FOREIGN KEY(PERSONAL_PERSONA_documento)
    REFERENCES PERSONAL(PERSONA_documento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);
CREATE TABLE CITA (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  AGENDA_id INTEGER UNSIGNED NOT NULL,
  ESTUDIANTE_PERSONA_documento VARCHAR(20) null,
  horaInicio INTEGER UNSIGNED NOT NULL,
  fecha DATE NOT NULL,
  estado INTEGER UNSIGNED NULL,
  lugar VARCHAR(300) NOT NULL,
  motivo VARCHAR(20) null,
  recomendaciones VARCHAR(1000) null,
  PRIMARY KEY(id),
  INDEX CITA_FKIndex1(ESTUDIANTE_PERSONA_documento),
  INDEX CITA_FKIndex2(AGENDA_id),
  FOREIGN KEY(ESTUDIANTE_PERSONA_documento)
    REFERENCES ESTUDIANTE(PERSONA_documento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(AGENDA_id)
    REFERENCES AGENDA(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);


CREATE TABLE FIN (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  SITUACION_id INTEGER UNSIGNED NOT NULL,
  texto VARCHAR(600) NOT NULL,
  titulo VARCHAR(50) NOT NULL,
  PRIMARY KEY(id),
  INDEX FIN_FKIndex1(SITUACION_id),
  FOREIGN KEY(SITUACION_id)
    REFERENCES SITUACION(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);


CREATE TABLE INFORMACION (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  PERSONAL_PERSONA_documento VARCHAR(20) NOT NULL,
  publico BOOL NOT NULL DEFAULT false,
  PRIMARY KEY(id),
  INDEX INFORMACION_FKIndex1(PERSONAL_PERSONA_documento),
  FOREIGN KEY(PERSONAL_PERSONA_documento)
    REFERENCES PERSONAL(PERSONA_documento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE REDSOCIAL (
  INFORMACION_id INTEGER UNSIGNED NOT NULL,
  nombre VARCHAR(30) NULL,
  link VARCHAR(200) NULL,
  alias VARCHAR(100) NULL,
  PRIMARY KEY(INFORMACION_id),
  INDEX RED_SOCIAL_FKIndex1(INFORMACION_id),
  FOREIGN KEY(INFORMACION_id)
    REFERENCES INFORMACION(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);


CREATE TABLE EXPERIENCIA (
  INFORMACION_id INTEGER UNSIGNED NOT NULL,
  cargo VARCHAR(90) NULL,
  detalles VARCHAR(300) NULL,
  PRIMARY KEY(INFORMACION_id),
  INDEX EXPERIENCIA_FKIndex1(INFORMACION_id),
  FOREIGN KEY(INFORMACION_id)
    REFERENCES INFORMACION(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE ESPECIALIDAD (
  INFORMACION_id INTEGER UNSIGNED NOT NULL,
  especialidad VARCHAR(150) NULL,
  PRIMARY KEY(INFORMACION_id),
  INDEX ESPECIALIDAD_FKIndex1(INFORMACION_id),
  FOREIGN KEY(INFORMACION_id)
    REFERENCES INFORMACION(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE CERTIFICADO (
  INFORMACION_id INTEGER UNSIGNED NOT NULL,
  tipoCertificacion VARCHAR(90) NULL,
  soporte VARCHAR(200) NULL,
  PRIMARY KEY(INFORMACION_id),
  INDEX CERTIFICADO_FKIndex1(INFORMACION_id),
  FOREIGN KEY(INFORMACION_id)
    REFERENCES INFORMACION(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE BIOGRAFIA (
  INFORMACION_id INTEGER UNSIGNED NOT NULL,
  biografia VARCHAR(1000) NULL,
  PRIMARY KEY(INFORMACION_id),
  INDEX Table_21_FKIndex1(INFORMACION_id),
  FOREIGN KEY(INFORMACION_id)
    REFERENCES INFORMACION(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);


CREATE TABLE `contigoBD`.`peticion_contrasena_persona` (
  `codigo` VARCHAR(20) NOT NULL,
  `persona_documento` VARCHAR(20) NOT NULL,
  `valido` TINYINT NOT NULL DEFAULT 0,
  `fecha` DATE NULL,
  INDEX `fk_peticion_contraseña_persona_persona1_idx` (`persona_documento` ASC) VISIBLE,
  PRIMARY KEY (`codigo`, `persona_documento`),
  CONSTRAINT `fk_peticion_contraseña_persona_persona1`
    FOREIGN KEY (`persona_documento`)
    REFERENCES `contigoBD`.`PERSONA` (`documento`)
    ON DELETE NO ACTION
    ON UPDATE cascade);
#ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `contigobd`.`peticion_contrasena_institucion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contigoBD`.`peticion_contrasena_institucion` (
  `codigo` VARCHAR(20) NOT NULL,
  `institucion_id` INT UNSIGNED NOT NULL,
  `valido` TINYINT NULL DEFAULT 0,
  `fecha` DATE NULL,
  PRIMARY KEY (`codigo`),
  INDEX `fk_peticion_contrasena_institucion_institucion1_idx` (`institucion_id` ASC) VISIBLE,
  CONSTRAINT `fk_peticion_contrasena_institucion_institucion1`
    FOREIGN KEY (`institucion_id`)
    REFERENCES `contigoBD`.`INSTITUCION` (`id`)
    ON DELETE NO ACTION
    ON UPDATE cascade);
    
CREATE TABLE IF NOT EXISTS `contigoBD`.`grado_has_historia` (
  `grado_codigo` VARCHAR(30) NOT NULL,
  `historia_idHistoria` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`grado_codigo`, `historia_idHistoria`),
  INDEX `fk_grado_has_historia_historia1_idx` (`historia_idHistoria` ASC) VISIBLE,
  INDEX `fk_grado_has_historia_grado1_idx` (`grado_codigo` ASC) VISIBLE,
  CONSTRAINT `fk_grado_has_historia_grado1`
    FOREIGN KEY (`grado_codigo`)
    REFERENCES `contigoBD`.`GRADO` (`codigo`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_grado_has_historia_historia1`
    FOREIGN KEY (`historia_idHistoria`)
    REFERENCES `contigoBD`.`HISTORIA` (`idHistoria`)
    ON DELETE cascade
    ON UPDATE cascade);
    
CREATE TABLE IF NOT EXISTS `contigoBD`.`NOTIFICACION` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(80) NULL,
  `texto` VARCHAR(200) NULL,
  `vista` TINYINT NULL,
  `persona_documento` VARCHAR(20) NOT NULL,
  `fecha` TIMESTAMP NULL,
  `tipo` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_NOTIFICACION_persona1_idx` (`persona_documento` ASC) VISIBLE,
  CONSTRAINT `fk_NOTIFICACION_persona1`
    FOREIGN KEY (`persona_documento`)
    REFERENCES `contigoBD`.`PERSONA` (`documento`)
    ON DELETE cascade
    ON UPDATE NO ACTION);
CREATE TABLE MOTIVO(
	ID VARCHAR(200) NOT NULL,
    primary key(id)
);
