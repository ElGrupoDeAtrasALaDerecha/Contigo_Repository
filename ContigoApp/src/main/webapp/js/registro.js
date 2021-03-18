var departamentos;
var municipios;
var ca = null;

window.onload = function depas() {
    $.ajax({
        url: "Departamento",
        type: "GET",
        dataType: "json",
        success: function (result, textStatus, request) {
            if (result != "error") {
                console.log(result);
                departamentos = result.Departamentos;
                for (var i = 0; i < departamentos.length; i++) {
                    let departa = '<option value ="' + departamentos[i].id + '">' + departamentos[i].nombre +
                        '</option>';
                    $("#departamento").append(departa);
                }
            } else {
                console.log("error");
            }

        },
        complete: function (result) {


        },
        error: function (result) {

        }

    });
    munici();
}



function registrar_institucion() {
    municipio = $("#municipio").val();
    nom = $("#nombre").val();
    sector = $("#sector").val();
    direccion = $("#direccion").val();
    barrio = $("#barrio").val();
    tel = $("#telefono").val();
    correo = $("#correo").val();
    pagw = $("#web").val();
    contra = $("#contra").val();
    calen = $("#Calendario").val();

    if (calen == 0) {
        ca = false;
    } else {
        ca = true;
    }

   

    informacion = {
        idMunicipio: municipio,
        nombre: nom,
        tipoInstitucion: sector,
        direccion: direccion,
        barrio: barrio,
        telefono: tel,
        correo: correo,
        pagina: pagw,
        contraseña: contra,
        calendario: ca
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

var departamento;

 $("#departamento").click(function(){
    departamento = $("#departamento").val();
    consultarMunicipiosPorDepartamento(departamento);
 });

function consultarMunicipiosPorDepartamento(departamento_ID){
    let municipiosAPintar=new Array();
    for (let i = 0; i < municipios.length; i++) {
        if(municipios[i].departamentoId===parseInt(departamento_ID,10)){
            municipiosAPintar.push(municipios[i]);
        }
    }
    llenarMunicipios(municipiosAPintar);
}

function llenarMunicipios(municipiosAPintar){
    $('#municipio').empty();
    let txt = '';
    for(let i=0; i<municipiosAPintar.length; i++){
        txt='<option value ="' + municipiosAPintar[i].id + '">' + municipiosAPintar[i].nombre +
        '</option>';
        $('#municipio').append(txt);
    };
    
}
