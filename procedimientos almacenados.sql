delimiter $$

create procedure insertarPersonalCalificado (
  _documento VARCHAR(20),
  _TIPO_DOCUMENTO_ID int,
  _primerNombre VARCHAR(30),
  _segundoNombre VARCHAR(30) ,
  _primerApellido VARCHAR(30),
  _segundoApellido VARCHAR(30),
  _token VARCHAR(200),
  _fechaNacimiento DATE,
  _contraseña VARCHAR(200),
  _genero VARCHAR(15),
  _correo VARCHAR(80),
  _imagen VARCHAR (240)
)

begin
insert into PERSONA(documento,TIPO_DOCUMENTO_id,primerNombre,segundoNombre,primerApellido,segundoApellido ,token ,fechaNacimiento ,contraseña,genero,correo)
values(_documento,_TIPO_DOCUMENTO_ID,_primerNombre,_segundoNombre,_primerApellido,_segundoApellido ,_token ,_fechaNacimiento ,sha(_contraseña),_genero,_correo);
insert into PERSONAL(PERSONA_documento,imagen) values (_documento,_imagen);
end $$



delimiter $$

create procedure insertarEstudiante (
  _documento VARCHAR(20),
  _TIPO_DOCUMENTO_ID int,
  _primerNombre VARCHAR(30),
  _segundoNombre VARCHAR(30) ,
  _primerApellido VARCHAR(30),
  _segundoApellido VARCHAR(30),
  _token VARCHAR(200),
  _fechaNacimiento DATE,
  _contraseña VARCHAR(200),
  _genero VARCHAR(15),
  _GRADO_codigo VARCHAR(30),
  _correo VARCHAR(80)
)
begin
insert into PERSONA(documento,TIPO_DOCUMENTO_id,primerNombre,segundoNombre,primerApellido,segundoApellido ,token ,fechaNacimiento ,contraseña,genero,correo)
values(_documento,_TIPO_DOCUMENTO_ID,_primerNombre,_segundoNombre,_primerApellido,_segundoApellido ,_token ,_fechaNacimiento ,sha(_contraseña),_genero,_correo);
insert into ESTUDIANTE(PERSONA_documento,GRADO_codigo) values (_documento,_GRADO_codigo);
end $$


delimiter $$

create procedure insertarBiografia (
  _documento VARCHAR(20),
  _biografia VARCHAR(1000)
)
begin
insert into INFORMACION(PERSONAL_PERSONA_documento)
values(_documento);
insert into BIOGRAFIA(INFORMACION_id,biografia) 
values ((select id from INFORMACION order by id desc limit 1),_biografia);
end $$


delimiter $$

create procedure insertarExperiencia (
  _documento VARCHAR(20),
  _cargo VARCHAR(20),
  _detalles VARCHAR(300)
)
begin
insert into INFORMACION(PERSONAL_PERSONA_documento)
values(_documento);
insert into EXPERIENCIA(INFORMACION_id,cargo,detalles) 
values ((select id from INFORMACION order by id desc limit 1),_cargo,_detalles);
end $$


delimiter $$

create procedure insertarEspecialidad (
  _documento VARCHAR(20),
  _especialidad VARCHAR(150)
)
begin
insert into INFORMACION(PERSONAL_PERSONA_documento)
values(_documento);
insert into ESPECIALIDAD(INFORMACION_id,especialidad) 
values ((select id from INFORMACION order by id desc limit 1),_especialidad);
end $$


delimiter $$

create procedure insertarRedSocial (
  _documento VARCHAR(20),
  _nombre VARCHAR(30),
  _link VARCHAR(200),
  _alias VARCHAR(100)
)
begin
insert into INFORMACION(PERSONAL_PERSONA_documento)
values(_documento);
insert into RED_SOCIAL(INFORMACION_id,nombre,link,alias) 
values ((select id from INFORMACION order by id desc limit 1),_nombre,_link,_alias);
end $$


delimiter $$
create procedure insertarCertificado(
  _documento VARCHAR(20),
  _tipoCertificacion VARCHAR(90),
  _soporte VARCHAR(200)
)
begin
insert into INFORMACION(PERSONAL_PERSONA_documento)
values(_documento);
insert into CERTIFICADO(INFORMACION_id,tipoCertificacion,soporte) 
values ((select id from INFORMACION order by id desc limit 1),_tipoCertificacion,_soporte);
end $$
