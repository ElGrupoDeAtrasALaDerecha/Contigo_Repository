var identificaciones;

window.onload = function tipos_id() {
    $.ajax({
        url: "TipoDocumento",
        type: "GET",
        dataType: "json",
        success: function (result, textStatus, request) {
            if (result != "error") {
                console.log(result);
                identificaciones = result.Identificaciones;
                for (var i = 0; i < identificaciones.length; i++) {
                    let departa = '<option value ="' + identificaciones[i].id + '">' + identificaciones[i].tipo +
                        '</option>';
                    $("#TipoDocumento").append(departa);
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
}

function registrar_estudiante() {
    tidocu = $("#TipoDocumento").val();
    numdocu = $("#TIoCC").val();
    pnom = $("#PrimerNombre").val();
    pape = $("#PrimerApellido").val();
    gen = $("#Genero").val();
    fena = $("#FechaNacimiento").val();
    codins = $("#CodigoInstitucional").val();
    snom = $("#SegundoNombre").val();
    sape = $("#SegundoApellido").val();
    con = $("#contra").val()

    informacion = {
        documento: numdocu,
        tipoDocumento: parseInt(tidocu, 10),
        primerNombre: pnom,
        segundoNombre: snom,
        primerApellido: pape,
        segundoApellido: sape,
        fechaDeNacimiento: fena,
        contraseña: con,
        genero: gen,
        grado: codins
    };

    $.ajax({
        url: "Estudiante",
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
                let msm = '<div class="alert alert-danger" role="alert">' + " Error código no valido" + '</div>';
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




function Ingresar() {
    TipoDocumento = document.getElementById("TipoDocumento").value;
    TIoCC = document.getElementById("TIoCC").value;
    PrimerNombre = document.getElementById("PrimerNombre").value;
    PrimerApellido = document.getElementById("PrimerApellido").value;
    Genero = document.getElementById("Genero").value;
    CodigoInstitucional = document.getElementById("CodigoInstitucional").value;
    SegundoApellido = document.getElementById("SegundoApellido").value;
    FechaNacimiento = document.getElementById("FechaNacimiento").value;
    contra = document.getElementById("contra").value;
    conficontra = document.getElementById("conficontra").value;
    var expReg = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;


    if (TipoDocumento == "Tipo de documento" || TIoCC == "" || PrimerNombre == "" || PrimerApellido == "" || Genero == "Genero" || CodigoInstitucional == "" || SegundoApellido == "" || FechaNacimiento == "" || contra == "" || conficontra == "") {
        let msm1 = '<div class="alert alert-danger" role="alert">' + " Todos los campos son obligatorios" + '</div>';
        $("#alert").append(msm1);
        
    } else if (contra != conficontra) {
        let msm2 = '<div class="alert alert-danger" role="alert">' + " Las contraseñas no coinciden" + '</div>';
        $("#alert").append(msm2);
        
    } else {
        registrar_estudiante();

    }
}

