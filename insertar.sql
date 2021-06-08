#Docs

Insert INTO TIPO_DOCUMENTO (tipo) values ("Cédula de ciudadanía");
INSERT INTO TIPO_DOCUMENTO (tipo) values ("Tarjeta de indentidad");
INSERT INTO TIPO_DOCUMENTO (tipo) values ("Registro civil");
INSERT INTO TIPO_DOCUMENTO (tipo) values ("Cédula de extranjería");

#Método de pago
INSERT INTO METODO_PAGO (nombre) VALUES ("Pago Colegio Gimnasio Los Pinares");

#Personal calificado
call insertarPersonalCalificado(  "1000853623",1,"Santiago","","Pérez" ,"González","1","2000-07-26","12345","masculino","santipego0001@gmail.com","https://www.definicionabc.com/wp-content/uploads/2015/03/orador.jpg");
call insertarPersonalCalificado("1000853622",1,"Valeria","","Bermúdez" ,"González","1234","2000-07-26","12346","femenino","aja@gmail.com","https://www.definicionabc.com/wp-content/uploads/2015/03/orador.jpg");
call insertarPersonalCalificado("10008",1,"Valeria","","Bermúdez" ,"González","22222222","2000-07-26","12347999","femenino","aja2@gmail.com","/https://www.definicionabc.com/wp-content/uploads/2015/03/orador.jpg");


#Clasificación
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


INSERT INTO INSTITUCION (MUNICIPIO_id, METODO_PAGO_id, nombre, correo, direccion, tipoInstitucion, calendario, barrio, telefono, contraseña, web) 
VALUES (5,1,"Colegio Gimnasio Los Pinares", "colegiogimnasio@gmail.com","Cra. 35 #9 Sur 160",true,false,"Los Balsos II","42686034",sha("1234"),"https://bit.ly/3thUZop");

#Grados
insert into GRADO (codigo,CLASIFICACION_id,INSTITUCION_id) values ("aaaaa",1,(select id from INSTITUCION where correo="colegiogimnasio@gmail.com"));
insert into GRADO (codigo,CLASIFICACION_id,INSTITUCION_id) values ("11B",2,(select id from INSTITUCION where correo="colegiogimnasio@gmail.com"));

#Estudiante
call insertarEstudiante("1000853620",1,"Valeria","","Bermúdez" ,"González","3","2000-07-26","12348","femenino","aaaaa","");
call insertarEstudiante("1000853624",1,"Santiago","","Pérez" ,"González","4","2000-07-26","12349","masculino","aaaaa","santiago.perez01@correo.usa.edu.co");
call insertarEstudiante("1007718536",1,"Miguel","Angel","Rippe" ,"Pereira","5","2000-11-26","123410","masculino","aaaaa","santipego@yahoo.es");
call insertarEstudiante("123456789",1,"María","Camila","Fernández" ,"González","123","2000-07-26","222222","femenino","aaaaa","contigoedsxapp@gmail.com");







/* Conversatorios */

insert into CONVERSATORIO (PERSONAL_PERSONA_documento,titulo,cronograma,imagen,descripcion,lugar,infografia)values ("1000853623","Amor Propio","Este conversatorio tiene una duracion de 2 bloques es decir 20 minutos","https://biutestbucket.s3.amazonaws.com/uploads/5077c422-eb07-489f-abbf-0055b16fed06-biutest.jpg","Este conversatorio va dirigido para los niños y niñas de primaria","Colegio Liceo Santa Bernardita","https://nosexistbullshithome.files.wordpress.com/2019/11/c2bfcocc81mo-puedes-amarte-macc81s_.png?w=768");
insert into CONVERSATORIO (PERSONAL_PERSONA_documento,titulo,cronograma,imagen,descripcion,lugar,infografia)values ("1000853623","Sexualidad","Este conversatorio tiene una duracion de 2 bloques es decir 20 minutos","https://www.vanguardia.com/binrepository/716x477/0c0/0d0/none/12204/WCEC/web_sexualidad___big_ce_VL414127_MG21906367.jpg","Este conversatorio para lo jovenes","Colegio Liceo Santa Cecilia","https://www.gob.mx/cms/uploads/attachment/file/246184/I_Sexualidad.pdf");

insert into CLASIFICACION_has_CONVERSATORIO (CLASIFICACION_id,CONVERSATORIO_id) values (1,1);
insert into CLASIFICACION_has_CONVERSATORIO (CLASIFICACION_id,CONVERSATORIO_id) values (2,1);

insert into HISTORIA (PERSONAL_PERSONA_documento,titulo,descripcion,urlImagen) 
values ("1000853623","Juanita y sus amigos", "Juanita que es una niña valiente y muy inteligente llega a un nuevo colegio y se da
                            cuenta que es
                            diferente a sus compañeras, por esto se vuelve insegura y tímida lo que hace que le cueste hacer
                            amigos, ¿Te acercarías a hablar con juanita?","img/school.jpg");
insert into SITUACION (SITUACION_id,HISTORIA_idHistoria,titulo,texto)
values(null,1,"Amigo de Juanita","Muy bien. No te dejas llevar por las apariencias");

insert into SITUACION (SITUACION_id,HISTORIA_idHistoria,titulo,texto)
values(2,1,"Amigo de Miguel","Muy bien. No te dejas llevar por las apariencias");

insert into SITUACION (SITUACION_id,HISTORIA_idHistoria,titulo,texto)
values(1,1,"Amigo de Miguel","Muy bien. No te dejas llevar por las apariencias");

insert into SITUACION (SITUACION_id,HISTORIA_idHistoria,titulo,texto)
values(2,1,"Amigo de Miguel","Muy bien. No te dejas llevar por las apariencias");

insert into SITUACION (SITUACION_id,HISTORIA_idHistoria,titulo,texto)
values(2,1,"Amigo de S4","Muy bien. No te dejas llevar por las apariencias");

insert into SITUACION (SITUACION_id,HISTORIA_idHistoria,titulo,texto)
values(3,1,"Amigo de S4","Muy bien. No te dejas llevar por las apariencias");

insert into SITUACION (SITUACION_id,HISTORIA_idHistoria,titulo,texto)
values(6,1,"Amigo de cx","Muy bien. No te dejas llevar por las apariencias");

insert into SITUACION (SITUACION_id,HISTORIA_idHistoria,titulo,texto)
values(6,1,"Amigo de cx","Muy bien. No te dejas llevar por las apariencias");

insert into FIN (SITUACION_id,titulo,texto)
values(8,"Acompañas a cx","Muy bien. Lo has logrado excelentemente :D");
insert into ESTUDIANTE_has_CONVERSATORIO (CONVERSATORIO_id, ESTUDIANTE_PERSONA_documento) values (1,"1007718536");
insert into ESTUDIANTE_has_CONVERSATORIO (CONVERSATORIO_id, ESTUDIANTE_PERSONA_documento) values (1,"123456789");
insert into ESTUDIANTE_has_CONVERSATORIO (CONVERSATORIO_id, ESTUDIANTE_PERSONA_documento) values (2,"123456789");

insert into ESTADISTICAS_BTNPANICO(ESTUDIANTE_PERSONA_documento,FECHA) values("123456789",sysdate());
#insert into ESTADISTICAS_BTNPANICO(ESTUDIANTE_PERSONA_documento,FECHA) values("100718536",sysdate());

insert into AGENDA (PERSONAL_PERSONA_documento, fechaInicio, fechaFin,horaInicio ,horaFin ) values ("1000853622","2021-3-28","2021-3-30","9","11");
insert into AGENDA (PERSONAL_PERSONA_documento, fechaInicio, fechaFin,horaInicio ,horaFin ) values ("1000853622","2021-3-28","2021-4-5","9","11");
insert into AGENDA (PERSONAL_PERSONA_documento, fechaInicio, fechaFin,horaInicio ,horaFin ) values ("1000853622","2021-05-05","2021-05-20","9","11");



insert into CITA (AGENDA_id ,horaInicio,fecha,estado,lugar ) values (1,9,"2021-03-28",1,"https://meet.google.com/snf-yxio-tdp");
insert into CITA (AGENDA_id ,horaInicio,fecha,estado,lugar ) values (1,10,"2021-03-28",1,"https://meet.google.com/snf-yxio-tdp");
insert into CITA (AGENDA_id ,horaInicio,fecha,estado,lugar ) values (1,11,"2021-03-28",1,"https://meet.google.com/snf-yxio-tdp");
insert into CITA (AGENDA_id ,horaInicio,fecha,estado,lugar ) values (1,9,"2021-04-04",1,"https://meet.google.com/snf-yxio-tdp");

insert into CITA (AGENDA_id ,horaInicio,fecha,estado,lugar ) values (3,9,"2021-05-07",1,"https://meet.google.com/snf-yxio-tdp");
insert into CITA (AGENDA_id ,horaInicio,fecha,estado,lugar ) values (3,10,"2021-05-07",1,"https://meet.google.com/snf-yxio-tdp");

insert into CITA (AGENDA_id ,horaInicio,fecha,estado,lugar ) values (3,10,"2021-05-11",1,"https://meet.google.com/snf-yxio-tdp");

call insertarBiografia("1000853623","Hola, soy estudiante de ingeniería de sistemas y Telecomunicaciones. Soy uno de los fundadores de Contigo");
call insertarExperiencia("1000853623","Tutor","Tutor autónomo de cálculo y programación");
#call insertarCertificado("1000853623","Comunicación asertiva","Miami me lo confirmó");

insert into NOTIFICACION (titulo,texto,vista,persona_documento,fecha,tipo) 
values("Su institución ha hecho visible un conversatorio", "Se ha hecho visible el conversatorio de amor propio",false,"123456789",sysdate());

insert into NOTIFICACION (titulo,texto,vista,persona_documento,fecha,tipo) 
values("Su institución ha hecho visible un conversatorio", "Se ha hecho visible el conversatorio de amor propio",false,"123456789",sysdate(),"conversatorio");
insert into NOTIFICACION (titulo,texto,vista,persona_documento,fecha,tipo) 
values("Tiene una cita", "Alguien ha solicitado cita con usted",true,"1000853623",sysdate(),"cita");
insert into NOTIFICACION (titulo,texto,vista,persona_documento,fecha,tipo) 
values("Tiene una cita", "Alguien ha solicitado cita con usted",false,"1000853623",sysdate(),"cita");


insert into NOTIFICACION (titulo,texto,vista,persona_documento,fecha,tipo)  
values("Tiene una cita", "Alguien ha solicitado cita con usted",false,"1000853623",sysdate(),"cita");