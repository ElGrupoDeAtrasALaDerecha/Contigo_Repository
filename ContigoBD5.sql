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



