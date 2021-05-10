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

select f.* from fin as f
inner join Situacion as s on s.id=f.SITUACION_id
where s.HISTORIA_idHistoria=1;
select * from agenda;

select * from agenda where personal_persona_documento="1000853622" ; 
select * from cita;
select id from AGENDA order by id desc limit 1;
select DATE_ADD('2018-01-01', INTERVAL 1 DAY) as fecha;
select DATEPART(dw, '5/03/2021');

select concat(p.primerNombre," ",p.segundoNombre," ",p.primerApellido," ",p.segundoApellido) as personal, pc.imagen , c.* from cita as c 
inner join agenda as a on a.id=c.AGENDA_id
inner join personal as pc on pc.PERSONA_documento=a.PERSONAL_PERSONA_documento
inner join persona as p on p.documento=pc.PERSONA_documento
where ESTUDIANTE_PERSONA_documento="1000853624" and fecha<sysdate();
