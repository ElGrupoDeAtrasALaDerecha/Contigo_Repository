function info_institucion(){
    departamento =$("#departamento").val();
    municipio =$("#municipio").val();
    nombre  =$("#nombre").val();
    sector =$("#sector").val();
    direccion =$("#direccion").val();
    barrio =$("#barrio").val();
    tel =$("#telefono").val();
    correo =$("#correo").val();
    pagw =$("#web").val();
    contra =$("#contra").val();
    conficontra =$("#conficontra").val();

    informacion={
            municipio:municipio,
            nombre:nombre,
            tipoInstitucion:sector,
            direccion:direccion,
            barrio:barrio,
            telefono:tel,
            correo:correo,
            pag:pagw,
            contraseña:contra
    };
    
    
    $.ajax({
        url: "Estudiante",
        type: "GET",

        data:informacion,
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function() {
            
        },
        success: function(result, textStatus, request) {
            console.log(result);
            if (result != "error") {
                $("#resultado").empty();
                $("#resultado").append(result.resultado);
                
            } else {
                console.log("error");
            }

        },
        complete: function(result) {
        

        },
        error: function(result) {

        }

    });
    
    
}