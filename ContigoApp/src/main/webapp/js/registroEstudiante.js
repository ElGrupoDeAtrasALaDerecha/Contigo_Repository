function registrar_estudiante() {
    tidocu = $("#TipoDocumento").val();
    numdocu = $("#TIoCC").val();
    pnom = $("#PrimerNombre").val();
    pape = $("#PrimerApellido").val();
    gen = $("#Genero").val();
    corre = $("#CorreoElectronico").val();
    fena = $("#FechaNacimiento").val();
    codins = $("#CodigoInstitucional").val();
    snom = $("#Segundo nombre").val();
    sape = $("#SegundoApellido").val();
    con = $("#contra").val()

    informacion = {
        documento: numdocu,
        tipoDocumento: parseInt(tidocu,10),
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
            } else {
                console.log("error");
            }

        },
        complete: function (result) {


        },
        error: function (result) {
            console.log(result);
        }

    });


}
