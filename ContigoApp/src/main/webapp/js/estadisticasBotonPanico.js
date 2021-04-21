var estudiante
var estadisticas
var grados
var frecuenciasClicksGrado = []
var boton = document.getElementById("generarEstadisticas");

$(document).ready(function () {
    traerClasificacionGrados();
    estudiante = getCookie("token");
});

function traerClasificacionGrados() {
    $.ajax({
        method: 'GET',
        url: 'ClasificacionServlet',
        data: "json",
        contentType: "JSON application/json charset=utf-8",
        success: function (response) {
            // console.log(response);
            grados = response.clasificaciones;
            cargarSelectGrados(grados);
        },
        error: function (response) {
            // console.log(JSON.stringify(response))
        }
    });
}

function cargarSelectGrados(grados) {
    for (var grado in grados) {
        document.getElementById("grados").innerHTML += "<option value='" + grados[grado].id + "'>" + grados[grado].grado + "</option>";
    }
}

$("#btnGerar").on("click", function () {
    //window.location.assign("gestionCurso.html")
    consultarInformacion();

});

// copy start
function consultarInformacion(obj) {
    $.ajax({
        method: 'GET',
        url: 'EstadisticasBtnPanico',
        data: JSON.stringify(obj),
        dataType: "json",
        success: function (response) {
            if (response.tipo === "ok") {
                // console.log(response);
                estadisticas = response.Estadisticas;
                // console.log(estadisticas)
                grafica1(estadisticas)
                filtrarClicksporDia(estadisticas)
                consultarClicksGrado()
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
function grafica1(estadisticas) {
    var meses = ['EN.', 'FEB.', 'MAR', 'ABR', 'MAY', 'JUN', 'JUL', 'AGT', 'SEP', 'OCT', 'NOV', 'DIC']
    var frecuencia = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0] // frecuenciauencia por mes
    for (let index = 0; index < estadisticas.length; index++) {
        for (let i = 0; i < estadisticas[index].fechas.length; i++) {
            var fecha = estadisticas[index].fechas[i]
            for (let j = 0; j < meses.length; j++) {
                var n = frecuencia[j]
                if (fecha.charAt(6) == (j + 1).toString()) {
                    n++
                    frecuencia[j] = n
                } else if ((fecha.charAt(5) + fecha.charAt(6)) == (j + 1).toString()) {
                    n++
                    frecuencia[j] = n
                }
            }
        }
    }
    parametrosGrafica(meses, frecuencia)
    // console.log(frecuencia)
}

function parametrosGrafica(x, y) {
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
            legend: {
                display: false
            }
        }
    });
}


function filtrarClicksporDia(estadisticas) {
    var clicksdiarios = 0;
    var dias = [
        'domingo',
        'lunes',
        'martes',
        'miércoles',
        'jueves',
        'viernes',
        'sábado',
    ];
    var frec = [0, 0, 0, 0, 0, 0, 0];
    for (let index = 0; index < estadisticas.length; index++) {
        for (let i = 0; i < estadisticas[index].fechas.length; i++) {
            var fecha = estadisticas[index].fechas[i];
            ms = Date.parse(estadisticas[index].fechas[i]);
            fecha2 = new Date(ms).getDay();
            const nombreDia = dias[fecha2];
            for (let j = 0; j < dias.length; j++) {
                if (fecha2 == (j).toString()) {
                    var n = frec[j];
                    n++;
                    frec[j] = n
                }
            }
        }
    }
    var tablaInscripcionAconversatorio = new Chart(document.getElementById('conversatoriosComunes').getContext('2d'), {
        type: 'bar',
        data: {
            labels: dias,
            datasets: [{
                label: 'Días',
                data: frec,
                backgroundColor: [
                    'rgb(136, 145, 200,0.6)',
                    'rgba(210, 180, 140, 0.6)',
                    'rgba(188, 143, 143, 0.6)',
                    'rgba(153, 102, 255, 0.6)'
                ],
                borderColor: [
                    'rgb(136, 145, 200)',
                    'rgba(210, 180, 140, 1)',
                    'rgba(188, 143, 143, 1)',
                    'rgba(153, 102, 255, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            title: {
                display: true,
                text: 'Número de estudiantes por grado que han asistido a conversatorios'
            },
            layout: {
                padding: {
                    left: 0,
                    right: 25,
                    top: 0,
                    bottom: 0
                }
            }
        }
    });
}



function consultarClicksGrado() {
    $.ajax({
        method: 'GET',
        url: 'Estadisticas?tipoConsulta=ClicksPorGrado',
        data: "json",
        contentType: "JSON application/json charset=utf-8",
        success: function (response) {
            console.log(response);
            clicksGrado = response.Clicks;
            gradosTraidos = response.grado;
            frecuenciasClicksGrado.push(clicksGrado);
            var gradosHistograma = []
            for (let i = 0; i < grados.length; i++) {
                gradosHistograma.push(grados[i].grado);
            }
            crearHistograma(gradosHistograma);
        },
    });
}

function crearHistograma(gradosHistograma) {
    var tablaInscripcionAconversatorio = new Chart(document.getElementById('situacionesComunes').getContext('2d'), {
        type: 'bar',
        data: {
            labels: gradosHistograma,
            datasets: [{
                label: 'Grados',
                data: clicksGrado,
                backgroundColor: [
                    'rgb(136, 145, 200,0.6)',
                    'rgba(210, 180, 140, 0.6)',
                    'rgba(188, 143, 143, 0.6)',
                    'rgba(153, 102, 255, 0.6)'
                ],
                borderColor: [
                    'rgb(136, 145, 200)',
                    'rgba(210, 180, 140, 1)',
                    'rgba(188, 143, 143, 1)',
                    'rgba(153, 102, 255, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            title: {
                display: true,
                text: 'Número de estudiantes por grado que han usado el botón de pánico'
            },
            layout: {
                padding: {
                    left: 0,
                    right: 25,
                    top: 0,
                    bottom: 0
                }
            }
        }
    });


}
