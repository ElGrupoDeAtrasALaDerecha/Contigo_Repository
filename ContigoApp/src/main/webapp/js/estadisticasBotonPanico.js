var clicks = 0;
var estudiante
var fecha
const tiempoTranscurrido = Date.now();
const hoy = new Date(tiempoTranscurrido);
let estadisticas

fecha = hoy.toLocaleDateString();

var boton = document.getElementById("generarEstadisticas");
var pCont = document.getElementById("areaContador");


$(document).ready(function () {
    traerGrados();
    estudiante = parseInt(getCookie("tipoUsuario"));
    console.log(estudiante)
    console.log(hoy)
});

function traerGrados() {
    $.ajax({
        method: 'GET',
        url: 'Grado',
        data: JSON.stringify(obj),
        dataType: "json",
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
    for(var grado in grados){ 
        document.getElementById("grados").innerHTML += "<option value='"+grados[i]+"'>"+grados[i]+"</option>";
     }

}

boton.onclick = function () {
    var obj = {
        fecha: fecha,
        estudiante: estudiante
    }
    console.log(obj)
    enviarInformacion(obj)

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