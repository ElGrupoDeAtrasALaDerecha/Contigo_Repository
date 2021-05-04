select * from Persona;


select p.*,pc.* from Persona as p
inner join Personal as pc on pc.PERSONA_documento=p.documento;

select p.*,e.* from Persona as p
inner join Estudiante as e on e.PERSONA_documento=p.documento;


select * from Estudiante;


select * from PERSONA;
select * from CLASIFICACION_has_CONVERSATORIO;
select * from PERSONAL;
select * from clasificacion;
select * FROM ESTUDIANTE;
select * from institucion;

select * from institucion where correo="contigoapp@gmail.com" and contraseña="contigo123";
select * from grado;
select * from conversatorio;
select * from institucion;

select * from historia;

select * from situacion;

select * from agenda;

select * from agenda where personal_persona_documento="1000853622" ; 
select * from cita;
select id from AGENDA order by id desc limit 1;
select DATE_ADD('2018-01-01', INTERVAL 1 DAY) as fecha;
select DATEPART(dw, '5/03/2021')


