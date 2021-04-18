var estudiante
let estadisticas

var boton = document.getElementById("generarEstadisticas");

$(document).ready(function () {
    traerGrados();
    estudiante = getCookie("token");
    console.log(estudiante)
    var obj = {
        estudiante: estudiante
    }
    console.log(obj)
    enviarInformacion(obj)

});

function traerGrados() {
    $.ajax({
        method: 'GET',
        url: 'Grado',
        data: "json",
        contentType: "JSON application/json charset=utf-8",
        success: function (response) {
            if (response.tipo === "ok") {
                console.log(response);
                grados = result.Grados;
                cargarSelectGrados(grados);
            } else {
                console.log(response.mensaje);
            }
        },
        error: function (response) {
            console.log(JSON.stringify(response))
        }
    });
}

function cargarSelectGrados(grados) {
    for (var grado in grados) {
        document.getElementById("grados").innerHTML += "<option value='" + grados[i] + "'>" + grados[i] + "</option>";
    }

}

function enviarInformacion(obj) {
    $.ajax({
        method: 'POST',
        url: 'Estadisticas_btn_Panico',
        data: JSON.stringify(obj),
        dataType: "json",
        success: function (response) {
            if (response.tipo === "ok") {
                console.log(response);
            } else {
                console.log(response.mensaje);
            }
        },
        error: function (response) {
            console.log(JSON.stringify(response))
        }
    });
}

$("#btnGerar").click(function () {
    var obj = {
        estudiante: estudiante
    }
    consultarInformacion(obj);
});

function consultarInformacion(obj) {
    $.ajax({
        method: 'GET',
        url: 'Estadisticas_btn_Panico',
        data: JSON.stringify(obj),
        dataType: "json",
        success: function (response) {
            if (response.tipo === "ok") {
                console.log(response);
                estadisticas = result.Estadísticas;
            } else {
                console.log(response.mensaje);
            }
        },
        error: function (response) {
            console.log(JSON.stringify(response))
        }
    });
}