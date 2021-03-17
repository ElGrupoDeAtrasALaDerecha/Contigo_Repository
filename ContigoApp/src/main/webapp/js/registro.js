var departamentos;
var municipios;
function dep() {
    $.ajax({
        url: "Departamento",
        type: "GET",
        dataType: "json",
        success: function (result, textStatus, request) {
            console.log(result);
            if (result != "error") {
                console.log(result);
                departamentos = result.Departamentos;
            } else {
                console.log("error");
            }

        },
        complete: function (result) {


        },
        error: function (result) {

        }

    });
}

function resgistrar_institucion() {
    departamento = $("#departamento").val();
    municipio = $("#municipio").val();
    nom = $("#nombre").val();
    sector = $("#sector").val();
    direccion = $("#direccion").val();
    barrio = $("#barrio").val();
    tel = $("#telefono").val();
    correo = $("#correo").val();
    pagw = $("#web").val();
    contra = $("#contra").val();
    conficontra = $("#conficontra").val();

    informacion = {
        idMunicipio: municipio,
        nombre: nom,
        tipoInstitucion: sector,
        direccion: direccion,
        barrio: barrio,
        telefono: tel,
        correo: correo,
        pagina: pagw,
        contraseña: contra
    };
    console.log(informacion);

    $.ajax({
        url: "Institucion",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(informacion),
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {

        },
        success: function (result, textStatus, request) {
            console.log(result);
            if (result != "error") {
                console.log(result);

            } else {
                console.log("error");
            }

        },
        complete: function (result) {


        },
        error: function (result) {

        }

    });


}


function munici() {
    $.ajax({
        url: "Municipio",
        type: "GET",
        dataType: "json",
        success: function (result, textStatus, request) {
            console.log(result);
            if (result != "error") {
                console.log(result);
                municipios = result.Municipios;
            } else {
                console.log("error");
            }

        },
        complete: function (result) {


        },
        error: function (result) {

        }
    });
}

