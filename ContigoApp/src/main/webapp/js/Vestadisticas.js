var opcGrado = document.getElementById('opcGrado');
var divge = document.getElementById('ge');
var btnDatos = document.getElementById('btn');
var graficas = document.getElementById('Graficas');
var listaEstudiantes = document.getElementById('ListaEstudiantes');

var opcVisualizar;//Select de opcion a visualizar tipo de graficas a analizar 
var grados;
var estudiante
var listaEstudiantes = document.getElementById('ListaEstudiantes');;
var arregloEstudiantes;

opcGrado.style.display = "none"
btnDatos.style.display = "none"
graficas.style.display = "none"
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
        btnDatos.style.display = "block"
    } else {
        btnDatos.style.display = "none"
    }
}

function aparecerSelectEst() {
    opcVisualizar = document.getElementById("consulta").value;
    grados = document.getElementById("txtGrado").value;
    if (opcVisualizar === "2") {
        if (grados !== "") {
            listaEstudiantes.style.display = "block"
        }
    }
}

$("#btnGerar").on("click", function () {
    //window.location.assign("gestionCurso.html")
    graficas.style.display = "block"
    GraficaTorta();
});



function GraficaTorta() {
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
                data: [300, 60],
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