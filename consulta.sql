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









