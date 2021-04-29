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
