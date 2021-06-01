update situacion
set titulo="Amigo de Antonio"
where id=3;
set SQL_SAFE_UPDATES=0;

update cita
set ESTUDIANTE_PERSONA_documento = "1000853624",
estado=2
where id=1;

update personal as pc ,persona as p
set p.primerNombre="Santiago",
p.segundoNombre="",
p.primerApellido="Pérez",
p.segundoApellido="González",
p.fechaNacimiento="2000-07-26",
p.genero="masculino",
pc.imagen = "https://emocionas.com/wp-content/uploads/2019/03/hombre-zen-768x768.jpg",
pc.biografia ="Soy ingeniero de Sistemas y Telecomunicaciones y uno de los fundadores de Contigo"
where p.documento="1000853623" and pc.PERSONA_documento=p.documento;

update institucion as i 
set i.contraseña=sha("password")
where i.correo="colegiogimnasio@gmail.com";

update peticion_contrasena_institucion as pci
set valido=false
where institucion_id=1;

update peticion_contrasena_persona 
set valido=false
where persona_documento="1000853623" and valido=true;

update persona as p 
set p.contraseña=sha("password")
where p.documento="1000853623";