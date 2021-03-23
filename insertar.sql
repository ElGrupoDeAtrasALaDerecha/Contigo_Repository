Insert INTO TIPO_DOCUMENTO (tipo) values ("Cédula de ciudadanía");
INSERT INTO tipo_documento (tipo) values ("Tarjeta de indentidad");
INSERT INTO tipo_documento (tipo) values ("Registro civil");
INSERT INTO tipo_documento (tipo) values ("Cédula de extranjería");

call insertarPersonalCalificado(  "1000853623",1,"Santiago","","Pérez" ,"González","1","2000-07-26","1234","masculino","santipego0001@gmail.com");

call insertarPersonalCalificado("1000853622",1,"Valeria","","Bermúdez" ,"González","2","2000-07-26","1234","femenino","aja@gmail.com");
call insertarEstudiante("1000853621",1,"Valeria","","Bermúdez" ,"González","3","2000-07-26","1234","femenino","aaaaa");
call insertarEstudiante("1000853624",1,"Santiago","","Pérez" ,"González","4","2000-07-26","1234","masculino","aaaaa");
call insertarEstudiante("1007718536",1,"Miguel","Angel","Rippe" ,"Pereira","5","2000-11-26","1234","masculino","aaaaa");


call insertarPersonalCalificado("1000853622",1,"Valeria","","Bermúdez" ,"González","1234","2000-07-26","1234","femenino","aja@gmail.com");
call insertarEstudiante("1000853620",1,"Valeria","","Bermúdez" ,"González","1234","2000-07-26","1234","femenino","aaaaa");
call insertarEstudiante("123456789",1,"María","Camila","Fernández" ,"González","123","2000-07-26","12345","femenino","aaaaa");


insert into estudiante values (1007718536,"aaaaa");
insert into estudiante values (1000853624,"11B");


INSERT INTO metodo_pago (nombre) VALUES ("Pago Colegio Gimnasio Los Pinares");

INSERT INTO institucion (MUNICIPIO_id, METODO_PAGO_id, nombre, correo, direccion, tipoInstitucion, calendario, barrio, telefono, contraseña, web) 
VALUES (5,1,"Colegio Gimnasio Los Pinares", "colegiogimnasio@gmail.com","Cra. 35 #9 Sur 160",true,false,"Los Balsos II","42686034","1234","https://bit.ly/3thUZop");


insert into CLASIFICACION (grado) values("Transición");
insert into CLASIFICACION (grado) values("Primero");
insert into CLASIFICACION (grado) values("Segundo");
insert into CLASIFICACION (grado) values("Tercero");
insert into CLASIFICACION (grado) values("Cuarto");
insert into CLASIFICACION (grado) values("Quinto");
insert into CLASIFICACION (grado) values("Sexto");
insert into CLASIFICACION (grado) values("Septimo");
insert into CLASIFICACION (grado) values("Octavo");
insert into CLASIFICACION (grado) values("Noveno");
insert into CLASIFICACION (grado) values("Decimo");
insert into CLASIFICACION (grado) values("Once");
insert into CLASIFICACION (grado) values("Docentes");

insert into Grado (codigo,CLASIFICACION_id,INSTITUCION_id) values ("aaaaa",1,2);
insert into Grado (codigo,CLASIFICACION_id,INSTITUCION_id) values ("11B",1,2);

/* Conversatorios */
100085
select * from PERSONA;
select * from CLASIFICACION_has_CONVERSATORIO;
select * from PERSONAL;
select * from clasificacion;
select * FROM ESTUDIANTE;
select * from institucion;
select * from grado;
select * from conversatorio;
select * from institucion;
delete from conversatorio where id= 15;
delete from CLASIFICACION_has_CONVERSATORIO where conversatorio_id=15;
delete from clasificacion where id = 3;

insert into CONVERSATORIO (PERSONAL_PERSONA_documento,titulo,cronograma,imagen,descripcion,lugar,infografia)values ("1000853623","Amor Propio","Este conversatorio tiene una duracion de 2 bloques es decir 20 minutos","https://biutestbucket.s3.amazonaws.com/uploads/5077c422-eb07-489f-abbf-0055b16fed06-biutest.jpg","Este conversatorio va dirigido para los niños y niñas de primaria","Colegio Liceo Santa Bernardita","https://nosexistbullshithome.files.wordpress.com/2019/11/c2bfcocc81mo-puedes-amarte-macc81s_.png?w=768");
insert into CONVERSATORIO (PERSONAL_PERSONA_documento,titulo,cronograma,imagen,descripcion,lugar,infografia)values ("1000853623","Sexualidad","Este conversatorio tiene una duracion de 2 bloques es decir 20 minutos","https://www.vanguardia.com/binrepository/716x477/0c0/0d0/none/12204/WCEC/web_sexualidad___big_ce_VL414127_MG21906367.jpg","Este conversatorio para lo jovenes","Colegio Liceo Santa Cecilia","https://www.gob.mx/cms/uploads/attachment/file/246184/I_Sexualidad.pdf");

insert into CLASIFICACION_has_CONVERSATORIO (CLASIFICACION_id,CONVERSATORIO_id) values (1,3);
insert into CLASIFICACION_has_CONVERSATORIO (CLASIFICACION_id,CONVERSATORIO_id) values (12,3);