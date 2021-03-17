insert into TIPO_DOCUMENTO (tipo) values ("Cédula de ciudadanía");

call insertarPersonalCalificado(  "1000853623",1,"Santiago","","Pérez" ,"González","1","2000-07-26","1234","masculino","santipego0001@gmail.com");

call insertarPersonalCalificado("1000853622",1,"Valeria","","Bermúdez" ,"González","2","2000-07-26","1234","femenino","aja@gmail.com");
call insertarEstudiante("1000853621",1,"Valeria","","Bermúdez" ,"González","3","2000-07-26","1234","femenino","aaaaa");

delete from Persona;

INSERT INTO metodo_pago (nombre) VALUES ("Pago Colegio Gimnasio Los Pinares");

INSERT INTO institucion (MUNICIPIO_id, METODO_PAGO_id, nombre, correo, direccion, tipoInstitucion, calendario, barrio, telefono, contraseña, web) 
VALUES (5,1,"Colegio Gimnasio Los Pinares", "colegiogimnasio@gmail.com","Cra. 35 #9 Sur 160",true,false,"Los Balsos II","42686034","1234","https://bit.ly/3thUZop");


insert into CLASIFICACION (grado) values("transición");
insert into Grado (codigo,CLASIFICACION_id,INSTITUCION_id) values ("aaaaa",1,1);

