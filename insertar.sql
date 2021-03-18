insert into TIPO_DOCUMENTO (tipo) values ("Cédula de ciudadanía");

call insertarPersonalCalificado(  "1000853623",1,"Santiago","","Pérez" ,"González","1","2000-07-26","1234","masculino","santipego0001@gmail.com");

call insertarPersonalCalificado("1000853622",1,"Valeria","","Bermúdez" ,"González","2","2000-07-26","1234","femenino","aja@gmail.com");
call insertarEstudiante("1000853621",1,"Valeria","","Bermúdez" ,"González","3","2000-07-26","1234","femenino","aaaaa");

delete from Persona;
call insertarPersonalCalificado("1000853622",1,"Valeria","","Bermúdez" ,"González","1234","2000-07-26","1234","femenino","aja@gmail.com");
call insertarEstudiante("1000853620",1,"Valeria","","Bermúdez" ,"González","1234","2000-07-26","1234","femenino","aaaaa");




INSERT INTO metodo_pago (nombre) VALUES ("Pago Colegio Gimnasio Los Pinares");

INSERT INTO institucion (MUNICIPIO_id, METODO_PAGO_id, nombre, correo, direccion, tipoInstitucion, calendario, barrio, telefono, contraseña, web) 
VALUES (5,1,"Colegio Gimnasio Los Pinares", "colegiogimnasio@gmail.com","Cra. 35 #9 Sur 160",true,false,"Los Balsos II","42686034","1234","https://bit.ly/3thUZop");


insert into CLASIFICACION (grado) values("transición");
insert into Grado (codigo,CLASIFICACION_id,INSTITUCION_id) values ("aaaaa",1,1);

/* Conversatorios */
select * from CONVERSATORIO;
select * from PERSONA;

select * from PERSONAL;
insert into CONVERSATORIO (PERSONAL_PERSONA_documento,titulo,cronograma,imagen,descripcion)values ("1000853623","Amor Propio","Este conversatorio tiene una duracion de 2 bloques es decir 20 minutos","https://biutestbucket.s3.amazonaws.com/uploads/5077c422-eb07-489f-abbf-0055b16fed06-biutest.jpg","Este conversatorio va dirigido para los niños y niñas de primaria");
insert into CONVERSATORIO (PERSONAL_PERSONA_documento,titulo,cronograma,imagen,descripcion)values ("1000853623","Sexualida","Este conversatorio tiene una duracion de 2 bloques es decir 20 minutos","https://www.vanguardia.com/binrepository/716x477/0c0/0d0/none/12204/WCEC/web_sexualidad___big_ce_VL414127_MG21906367.jpg","Este conversatorio para lo jovenes");

