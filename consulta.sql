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
select * from GRADO where codigo="aaaaa"; 
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



select i.*,b.* from informacion as i
inner join biografia as b on b.INFORMACION_id=i.id
where i.PERSONAL_PERSONA_documento="1000853623";

select i.*,expe.* from informacion as i
inner join experiencia as expe on expe.INFORMACION_id=i.id
where i.PERSONAL_PERSONA_documento="1000853623";

select i.*,c.* from informacion as i
inner join certificado as c on c.INFORMACION_id=i.id
where i.PERSONAL_PERSONA_documento="1000853623";

select i.*,esp.* from informacion as i
inner join especialidad as esp on esp.INFORMACION_id=i.id
where i.PERSONAL_PERSONA_documento="1000853623";

select i.*,rs.* from informacion as i
inner join red_social as rs on rs.INFORMACION_id=i.id
where i.PERSONAL_PERSONA_documento="1000853623";


select pci.* from peticion_contrasena_institucion as pci 
inner join institucion as i on i.id=pci.institucion_id
where i.correo="colegiogimnasio@gmail.com" and pci.valido=true;

select institucion_id from peticion_contrasena_institucion 
where codigo="codigo";


select persona_documento from peticion_contrasena_persona 
where codigo="codigo";

select pcp.* from peticion_contrasena_persona as pcp
inner join persona as p on p.documento=pcp.persona_documento
where p.documento="1000853623" and pcp.valido=true;

select c.*, g.codigo from clasificacion c
inner join grado as g on g.CLASIFICACION_id=c.id
inner join institucion as i on i.id=INSTITUCION_id
where i.id=1;


select p.*,c.grado 
from persona as p 
inner join Estudiante as e on e.PERSONA_documento= p.documento
inner join Grado as g  on e.GRADO_codigo=g.codigo
inner join clasificacion as c on c.id=g.CLASIFICACION_id
where g.codigo = "aaaaa";

#Consulta de las historias que ha completado un estudiante
select h.* from estudiante_has_historia as eh
inner join estudiante as e on e.PERSONA_documento=eh.ESTUDIANTE_PERSONA_documento
inner join historia as h on h.idHistoria=eh.HISTORIA_idHistoria
where e.PERSONA_documento="1000853624";


select month(FECHA) as mes, count(eb.ESTUDIANTE_PERSONA_documento) as clicks from estadisticas_btnpanico as eb
inner join estudiante as e on eb.ESTUDIANTE_PERSONA_documento=e.PERSONA_documento
where e.PERSONA_documento="1000853623"
and 
FECHA between LAST_DAY(DATE_SUB(NOW(), INTERVAL 6 MONTH)) and NOW()
group by month(FECHA)
order by month(FECHA) asc;

#Consulta de los clicks de un estudiante
select t2.mes, coalesce(t1.clicks,0) as clicks from 
(
select month(FECHA) as mes, count(eb.ESTUDIANTE_PERSONA_documento) as clicks from estadisticas_btnpanico as eb
inner join estudiante as e on eb.ESTUDIANTE_PERSONA_documento=e.PERSONA_documento
where e.PERSONA_documento="123456789"
and 
FECHA between FIRST_DAY(DATE_SUB(NOW(), INTERVAL 5 MONTH)) and NOW()
group by month(FECHA)
order by month(FECHA) asc
)as t1
right outer join
 (select month(q.dia) as mes from (
SELECT
    DATE_ADD(LAST_DAY(DATE_SUB(NOW(), INTERVAL 5 MONTH)), INTERVAL t.n DAY) as dia
FROM (
    SELECT 
        a.N + b.N * 10 + c.N * 100 AS n
    FROM
        (SELECT 0 AS N UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) a
       ,(SELECT 0 AS N UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) b
       ,(SELECT 0 AS N UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7) c
    ORDER BY n
) t   
WHERE
    t.n <= TIMESTAMPDIFF(DAY, LAST_DAY(DATE_SUB(NOW(), INTERVAL 5 MONTH)) , NOW() )) as q
group by month(q.dia)
order by q.dia) as t2
on t1.mes=t2.mes;