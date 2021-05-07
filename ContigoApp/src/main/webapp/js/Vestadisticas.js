var opcGrado = document.getElementById('opcGrado');
var divge = document.getElementById('ge');
var btnGrados = document.getElementById('btnGrados');
var btnEstudiantes = document.getElementById('btnEstudiantes');
var graficas = document.getElementById('Graficas');
var listaEstudiantes = document.getElementById('ListaEstudiantes');
var opcVisualizar;//Select de opcion a visualizar tipo de graficas a analizar 
var grados;
var estudiante
var listaEstudiantes = document.getElementById('ListaEstudiantes');;
var arregloEstudiantes;
var codigoGrado = [];

graficas.style.display = "none"

$('.ui.dropdown')
    .dropdown();

$(document).ready(function () {
    listarGrados();
});


function obtenerSelect() {
    opcVisualiza = document.getElementById("txtConsulta").value;
    if (opcVisualiza === "1" || opcVisualiza === "2") {
        opcGrado.style.display = "block"
        divge.style.display = "none"
        

    } else if (opcVisualiza === "") {
        opcGrado.style.display = "none"
    }
}

$(".ui.dropdown").dropdown();
$(".ui.dropdown").click(function () {
    selects();
    aparecerSelectEst();
});


function selects() {
    grados = document.getElementById("txtGrado").value;
    //var tiempo = document.getElementById("txtTiempo").value;
    if (grados !== "" ) {
        btnGrados.style.display = "block"
    } else {
        btnGrados.style.display = "none"
    }
}

function listarGrados() {
    $.ajax({
        url: 'ClasificacionServlet',
        method: 'GET',
        dataType: 'json',
        success: function (response) {
            arregloGrados = response.clasificaciones;
            for (let index = 0; index < response.clasificaciones.length; index++) {
                codigoGrado[index]= response.clasificaciones.codigo;                
            }
            llenarSelectGrados(arregloGrados);
        },
        error: function (response) {
            console.log("Error en la petición GET")
            console.log(JSON.stringify(response))
        }
    })
}

function llenarSelectGrados(a) {    
    for (var i = 0; i < a.length; i++) {
        $("#gradosSlt").append($("<option>", {
            value: a[i].codigo,
            text: a[i].grado
        }));
    }
}

$("#btnGerarE").on("click", function () {
    //window.location.assign("gestionCurso.html")
    graficas.style.display = "block"
});

$("#gradosSlt").on("click", function () {
    graficas.style.display = "block"
    let codigoGrado = document.getElementById('gradosSlt').value
    solicitarDatosGrafica(codigoGrado);
    consultarInformacion()
});

function solicitarDatosGrafica(codigoGrado){
    $.ajax({
        url: 'Estadisticas?tipoConsulta=PorGrado&grado='+ codigoGrado,
        method: 'GET',
        dataType: 'json',
        success: function (response) {
            arregloGrados = response.clasificaciones;
            //console.log(response)
            GraficaTorta(response.boton.datos);
            graficaTopGrados(response.conversatorios)
        },
        error: function (response) {
            // console.log("Error en la petición GET")
            // console.log(JSON.stringify(response))
        }
    }) 
}

//=========================================== Gráficas ============================================================

function GraficaTorta(data) {
    var oilCanvas = document.getElementById("usoChat");
    Chart.defaults.global.defaultFontFamily = "Lato";
    Chart.defaults.global.defaultFontSize = 18;
    var oilData = {
        labels: [
            "no usaron el boton",
            "Si usaron el boton "
        ],
        datasets: [
            {
                data: [data[1],data[2]],
                backgroundColor: [
                    "#684CB6",
                    "#C5BCDD"
                ]
            }]
    };
    var pieChart = new Chart(oilCanvas, {
        type: 'pie',
        data: oilData
    });
}

function graficaTopGrados(datos){
    var densityCanvas = document.getElementById("GraficasTOPgrados");

    Chart.defaults.global.defaultFontFamily = "Lato";
    Chart.defaults.global.defaultFontSize = 18;
    var data = parseInt(datos.inscritos)
    var densityData = {
      label: 'Conversatorios más vistos',
      data: [data],
      backgroundColor: [
        'rgba(0, 99, 132, 0.6)',
        'rgba(30, 99, 132, 0.6)',
        'rgba(60, 99, 132, 0.6)',
        'rgba(90, 99, 132, 0.6)',
        'rgba(120, 99, 132, 0.6)',
        'rgba(150, 99, 132, 0.6)',
        'rgba(180, 99, 132, 0.6)',
        'rgba(210, 99, 132, 0.6)',
        'rgba(240, 99, 132, 0.6)'
      ],
      borderColor: [
        'rgba(0, 99, 132, 1)',
        'rgba(30, 99, 132, 1)',
        'rgba(60, 99, 132, 1)',
        'rgba(90, 99, 132, 1)',
        'rgba(120, 99, 132, 1)',
        'rgba(150, 99, 132, 1)',
        'rgba(180, 99, 132, 1)',
        'rgba(210, 99, 132, 1)',
        'rgba(240, 99, 132, 1)'
      ],
      borderWidth: 2,
      hoverBorderWidth: 0
    };
    
    var chartOptions = {
      scales: {
        yAxes: [{
          barPercentage: 0.5
        }]
      },
      elements: {
        rectangle: {
          borderSkipped: 'left',
        }
      }
    };
    
    var barChart = new Chart(densityCanvas, {
      type: 'horizontalBar',
      data: {
        labels: [datos.titulos],
        datasets: [densityData],
      },
      options: chartOptions
    });
}

var estudiante
var estadisticas
var gradosV
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
            //console.log(response);
            gradosV = response.clasificaciones;
            // cargarSelectGrados(gradosV);
        },
        error: function (response) {
            // console.log(JSON.stringify(response))
        }
    });
}


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
                //console.log(response.mensaje);
            }
        },
        error: function (response) {
            //console.log(JSON.stringify(response))
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
    var tablaInscripcionAconversatorio = new Chart(document.getElementById('clickDia').getContext('2d'), {
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
            //console.log(response);
            clicksGrado = response.Clicks;
            frecuenciasClicksGrado.push(clicksGrado);
            var gradosHistograma = []
            for (let i = 0; i < gradosV.length; i++) {
                gradosHistograma.push(gradosV[i].grado);
            }
            crearHistograma(gradosHistograma);
        },
    });
}

function crearHistograma(gradosHistograma) {
    for (let i = 0; i < gradosHistograma.length; i++) {
        //console.log(gradosHistograma[i])
    }
    var tablaInscripcionAconversatorio = new Chart(document.getElementById('clickGrado').getContext('2d'), {
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
