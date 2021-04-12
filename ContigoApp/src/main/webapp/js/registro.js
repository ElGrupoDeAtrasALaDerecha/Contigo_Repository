var departamentos;
var municipios;
var instituciones;
var ca = null;
var sec = null;
var idpago = 1;

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

    if (sector == 0) {
        sec = false;
    } else {
        sec = true;
    }


    informacion = {
        idMunicipio: parseInt(municipio,10),
        nombre: nom,
        tipoInstitucion: sec,
        direccion: direccion,
        barrio: barrio,
        telefono: tel,
        correo: correo,
        pagina: pagw,
        contraseña: contra,
        calendario: ca,
        METODO_PAGO_id: idpago
    };

    $.ajax({
        url: "Institucion",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(informacion),
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {  
        },
        success: function (result, textStatus, request) {
            if (result.tipo != "error") {
                console.log(result);
                $(location).attr('href', 'ingresar.html');
            } else {
                console.log(result);
                let msm = '<div class="alert alert-danger" role="alert">' + " Error esta institución ya esta registrada" + '</div>';
                $("#alert").append(msm);
            }

        },
        complete: function (result) {


        },
        error: function (result) {
            console.log(result);
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

$("#departamento").click(function () {
    departamento = $("#departamento").val();
    consultarMunicipiosPorDepartamento(departamento);
});

function consultarMunicipiosPorDepartamento(departamento_ID) {
    let municipiosAPintar = new Array();
    for (let i = 0; i < municipios.length; i++) {
        if (municipios[i].departamentoId === parseInt(departamento_ID, 10)) {
            municipiosAPintar.push(municipios[i]);
        }
    }
    llenarMunicipios(municipiosAPintar);
}

function llenarMunicipios(municipiosAPintar) {
    $('#municipio').empty();
    let txt = '';
    for (let i = 0; i < municipiosAPintar.length; i++) {
        txt = '<option value ="' + municipiosAPintar[i].id + '">' + municipiosAPintar[i].nombre +
            '</option>';
        $('#municipio').append(txt);
    };

}

function Ingresar() {
    departamento = document.getElementById("departamento").value;
    municipio = document.getElementById("municipio").value;
    nombre = document.getElementById("nombre").value;
    sector = document.getElementById("sector").value;
    direccion = document.getElementById("direccion").value;
    barrio = document.getElementById("barrio").value;
    telefono = document.getElementById("telefono").value;
    correo = document.getElementById("correo").value;
    web = document.getElementById("web").value;
    Calendario = document.getElementById("Calendario").value;
    contra = document.getElementById("contra").value;
    conficontra = document.getElementById("conficontra").value;
    var expReg = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
    var esValido = expReg.test(correo);

    if (departamento == "Departamentos" || municipio == "Municipio" || nombre == "" || sector == "Sector" || direccion == "" || barrio == "" || telefono == "" || correo == "" || Calendario == "" || contra == "" || conficontra == "") {
        let msm1 = '<div class="alert alert-danger" role="alert">' + " Todos los campos son obligatorios" + '</div>';
        $("#alert").append(msm1);
    } else if (contra != conficontra) {
        let msm2 = '<div class="alert alert-danger" role="alert">' + " Las contraseñas no coinciden" + '</div>';
        $("#alert").append(msm2);
    } else if (esValido != true) {
        let msm2 = '<div class="alert alert-danger" role="alert">' + " El correo ingresado no es valido" + '</div>';
        $("#alert").append(msm2);
    } else {
        registrar_institucion();
    }
}
