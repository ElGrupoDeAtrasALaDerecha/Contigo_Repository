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
