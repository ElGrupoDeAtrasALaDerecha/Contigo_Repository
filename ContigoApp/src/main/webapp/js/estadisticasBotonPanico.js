var estudiante
let estadisticas

var boton = document.getElementById("generarEstadisticas");

$(document).ready(function () {
    traerGrados();
    estudiante = getCookie("token");
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
                grados = response.Grados;
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
        document.getElementById("grados").innerHTML += "<option value='" + grados[grado] + "'>" + grados[grado] + "</option>";
    }

}

$("#btnGerar").on("click", function () {
    //window.location.assign("gestionCurso.html")
    consultarInformacion();
});

function consultarInformacion(obj) {
    $.ajax({
        method: 'GET',
        url: 'EstadisticasBtnPanico',
        data: JSON.stringify(obj),
        dataType: "json",
        success: function (response) {
            if (response.tipo === "ok") {
                console.log(response);
                estadisticas = response.Estadisticas;
                // console.log(estadisticas)
                grafica1(estadisticas)
            } else {
                console.log(response.mensaje);
            }
        },
        error: function (response) {
            console.log(JSON.stringify(response))
        }
    });
}

// Grafica mensual de la cantidad de veces que los estudiantes han dado click al botón de pánico
function grafica1 (estadisticas) {
    var meses = ['EN.', 'FEB.', 'MAR', 'ABR', 'MAY', 'JUN', 'JUL','AGT', 'SEP', 'OCT', 'NOV', 'DIC']
    var frecuencia = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0] // frecuenciauencia por mes
    for (let index = 0; index < estadisticas.length; index++) {
        for (let i = 0; i < estadisticas[index].fechas.length; i++) {
            var fecha = estadisticas[index].fechas[i]
            for (let j = 0; j < meses.length; j++) {
                var n = frecuencia[j]
                if (fecha.charAt(6) == (j+1).toString()){
                    n++
                    frecuencia[j] = n
                } else if((fecha.charAt(5)+fecha.charAt(6)) == (j+1).toString()){
                    n++
                    frecuencia[j] = n
                } 
            }               
        }
    }
    parametrosGrafica(meses,frecuencia)
    // console.log(frecuencia)
}

// Grafica semanal de la cantidad de veces que los estudiantes han dado click al botón de pánico

function grafica2(estadisticas) {
    var semanas = ['En', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio','Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre']
    var frecuencia = [0, 0, 0, 0] // frecuenciauencia por semana
}

function parametrosGrafica (x, y) {  
    var chart = new Chart(document.getElementById('cantidadEstudiantes').getContext('2d'), {
        type: 'line',
        data: {
            labels: x,
            datasets: [{
                label: 'Interacciones mensuales con el botón de Pánico',
                borderColor: '#2d4059',
                backgroundColor: 'rgba(176, 196, 222, 0.35)',
                data: y
            }]
        },
        options: {
            title: {
                display: true,
                text: 'Cantidad de estudiantes usando  Contigo en la Institución'
            },
            legend:{
                display:false
            }
        } 
    });
}