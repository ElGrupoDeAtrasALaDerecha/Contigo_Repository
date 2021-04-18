








function crearHistoria() {
    var documento;
    console.log(getCookie("token"));
    for (var i = 0; i < personal.length; i++) {
        if (parseInt(getCookie("token")) == parseInt(personal[i].token)) {
            documento = personal[i].documento
        }
    }

    titulo = $("#Texto").val();
    descripcion = $("#Descripcion").val();
    cronograma = $("#cronograma").val();
    lugar = $("#Lugar").val();
    imagen = $("#linkImagen").val();
    infografia = $("#linkInfografia").val();
    clasifica = $("#grados").val();

    informacion = {


    };


    console.log(informacion);
    $.ajax({
        url: "Conversatorio",
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
