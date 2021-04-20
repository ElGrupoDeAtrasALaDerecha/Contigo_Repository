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



#Consultas Natalia Estadisticas conversatorios
#Se consulta el id y el titulo de los conversatorios
select C.id , C.titulo from  ESTUDIANTE_has_CONVERSATORIO as EC
inner join conversatorio as C on EC.CONVERSATORIO_id = C.id;

#Se consula cuantas personas se han registrado en los conversatorios, se agrupan los datos 
#Inscritos por institución
select C.titulo, count(EC.ESTUDIANTE_PERSONA_documento) as Inscritos from  ESTUDIANTE_has_CONVERSATORIO as EC
right join conversatorio as C on EC.CONVERSATORIO_id = C.id 
inner join estudiante as E on EC.ESTUDIANTE_PERSONA_documento = E.PERSONA_documento
inner join grado as G on E.GRADO_codigo = G.codigo
inner join institucion as I on G.INSTITUCION_id = I.id
where I.id = 1
group by C.id
order by Inscritos desc
limit 5
;

#Se consula cuantas personas se han registrado en los conversatorios, se agrupan los datos 
#Inscritos por grado
select C.titulo, count(EC.ESTUDIANTE_PERSONA_documento) as Inscritos from  ESTUDIANTE_has_CONVERSATORIO as EC
inner join estudiante as E on EC.ESTUDIANTE_PERSONA_documento = E.PERSONA_documento
inner join grado as G on E.GRADO_codigo = G.codigo
right join conversatorio as C on EC.CONVERSATORIO_id = C.id 
where G.codigo = "aaaaa" 
group by C.id
order by Inscritos desc
limit 5
; 









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
