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

opcGrado.style.display = "none"
btnGrados.style.display = "none"
btnEstudiantes.style.display = "none"
//graficas.style.display = "none"
listaEstudiantes.style.display = "none"

$('.ui.dropdown')
    .dropdown()
    ;

$(document).ready(function () {
    listarGrados();
});


function obtenerSelect() {

    opcVisualiza = document.getElementById("consulta").value;
    if (opcVisualiza === "1" || opcVisualiza === "2") {
        opcGrado.style.display = "block"
        divge.style.display = "none"


    } else if (opcVisualiza === "") {
        opcGrado.style.display = "none"
    }
}

$(".ui.dropdown").click(function () {
    selects();
    aparecerSelectEst();
});

function selects() {
    grados = document.getElementById("txtGrado").value;
    var tiempo = document.getElementById("frecuenciaEstadisticas").value;
    if (grados !== "" && tiempo !== "") {
        btnGrados.style.display = "block"
    } else {
        btnGrados.style.display = "none"
    }
}

function aparecerSelectEst() {
    opcVisualizar = document.getElementById("consulta").value;
    grados = document.getElementById("txtGrado").value;
    if (opcVisualizar === "2") {
        if (grados !== "") {
            listaEstudiantes.style.display = "block"
            btnEstudiantes.style.display = "block"
        }
    }
}

$("#btnGerarE").on("click", function () {
    //window.location.assign("gestionCurso.html")
    graficas.style.display = "block"
});

$("#btnGerarG").on("click", function () {
    //window.location.assign("gestionCurso.html")
    graficas.style.display = "block"
    solicitarDatosTOPgrado();
    
    let codigoGrado =document.getElementById('txtGrado');
    $.ajax({
        url: 'Estadisticas?tipoConsulta=BtnPorGrado&grado='+ codigoGrado.value,
        method: 'GET',
        dataType: 'json',
        success: function (response) {
            arregloGrados = response.clasificaciones;
            console.log(response)
            GraficaTorta(response.datos);
        },
        error: function (response) {
            // console.log("Error en la petición GET")
            // console.log(JSON.stringify(response))
        }
    })
    
});

function solicitarDatosTOPgrado(){
    let codigoGrado =document.getElementById('txtGrado');
    $.ajax({
        url: 'Estadisticas?tipoConsulta=ConversatorioPorGrado&grado='+ codigoGrado.value,
        method: 'GET',
        dataType: 'json',
        success: function (response) {
            arregloGrados = response.clasificaciones;
            console.log(response)
            graficaTopGrados(response.datos);
        },
        error: function (response) {
            // console.log("Error en la petición GET")
            // console.log(JSON.stringify(response))
        }
    })
}


//Gráficas

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
    
    var densityData = {
      label: 'CONVERSATORIOS MÁS VISTOS POR GRADOS TOP 5',
      data: [datos[1]],
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
        labels: [datos[0]],
        datasets: [densityData],
      },
      options: chartOptions
    });
}



function listarGrados() {
    $.ajax({
        url: 'ClasificacionServlet',
        method: 'GET',
        dataType: 'json',
        success: function (response) {
            arregloGrados = response.clasificaciones;
            console.log(response)
            llenarSelectGrados(arregloGrados);
        },
        error: function (response) {
            // console.log("Error en la petición GET")
            // console.log(JSON.stringify(response))
        }
    })
}

function llenarSelectGrados(a) {
    for (var i = 0; i < a.length; i++) {
        let txt = `<div class="item" data-value="${a[i].id}">${a[i].grado}</div>`
        $("#grados").append(txt);
    }
}

// ============================ Alternativa ============================
$('#selectGrados').click(function (e) {
    var gradoSelt = $('#txtGrado').val()
    // var id_inst = getCookie("ID_Inst")
    var obj = {
        grado: gradoSelt
    }
    listarEstudiantes(obj)
})

function listarEstudiantes(obj) {
    $.ajax({
        url: 'EstudiantePorGradoServlet',
        method: 'POST',
        dataType: "json",
        data: JSON.stringify(obj),
        contentType: "JSON application/json charset=utf-8",
        success: function (response) {
            if (response.tipo === "ok") {
                console.log(response);
                llenarSelectEstudiantes(response.estudiantes);
            } else {
                console.log(response.mensaje);
            }
        },
        error: function (response) {
            console.log(JSON.stringify(response))
            console.log((response))
        }
    })
}
function llenarSelectEstudiantes(arregloEstudiantes) {
    for (var i = 0; i < arregloEstudiantes.length; i++) {
        let txt = `<div class="item" data-value="${arregloEstudiantes[i].documento}">${arregloEstudiantes[i].primerNombre} ${arregloEstudiantes[i].segundoNombre} ${arregloEstudiantes[i].primerApellido} ${arregloEstudiantes[i].segundoApellido}</div>`
        $("#estudiantes").append(txt);
    }
}