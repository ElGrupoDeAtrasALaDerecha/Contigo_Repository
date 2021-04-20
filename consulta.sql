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

/*cantidad de estudiantes que presionaron el btn*/
/*total de estudiantes de un grado*/

select q1.totalE as "total", q2.totalEP as "si", q1.totalE-q2.totalEP as "no" from 
(select count(*) as totalE from estudiante
where GRADO_codigo="aaaaa") as q1, 
(select count(p.documento) as totalEP from Persona as p
inner join Estudiante as e on e.PERSONA_documento=p.documento
inner join GRADO as g on g.codigo=e.GRADO_codigo
where g.codigo="aaaaa" and p.documento in (select distinct ESTUDIANTE_PERSONA_documento from estadisticas_btnpanico)
) as q2;


select count(*) as totalE from estudiante
where GRADO_codigo="aaaaa";
 
select count(p.documento) as totalEP from Persona as p
inner join Estudiante as e on e.PERSONA_documento=p.documento
inner join GRADO as g on g.codigo=e.GRADO_codigo
where g.codigo="aaaaa" and p.documento in (select distinct ESTUDIANTE_PERSONA_documento from estadisticas_btnpanico);

select *from estadisticas_btnpanico;
