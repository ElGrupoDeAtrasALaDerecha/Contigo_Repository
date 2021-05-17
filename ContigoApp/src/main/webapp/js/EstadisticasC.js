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
    if (grados !== "") {
        btnDatos.style.display = "block"
    } else {
        btnDatos.style.display = "none"
    }
}

function aparecerSelectEst() {
    opcVisualizar = document.getElementById("consulta").value;
    if (opcVisualizar === "2") {
        btnDatos.style.display = "block"
    } else {
        btnDatos.style.display = "none"
    }
}


$("#btnGerar").on("click", function () {
    //window.location.assign("gestionCurso.html")
    graficas.style.display = "block"
    GraficaTop5();
});


// Funciones creación de gráficas 
// Gráfica uso del chat 
function GraficaTop5(data) {
    var oilCanvas = document.getElementById("TOP5");
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
    var bar = new Chart(oilCanvas, {
        type: 'bar',
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
