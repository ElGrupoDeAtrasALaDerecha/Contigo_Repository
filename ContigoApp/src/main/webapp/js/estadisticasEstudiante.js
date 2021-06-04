var estudiantes;
function agregarFuncionesDeGraficasDeEstudiantes() {
    $("#contenidoGraficas").empty();

    let vistaGraficaEstudiante = `<div class="ui grid">
        <div class="ui row">
            <div class="ui three column grid">
                    <div class="ui column">
                        <label>Seleccione el grado: </label>
                        <select name="" id="selectGrado" class="ui search fluid dropdown">
                            
                        </select>
                    </div>
                    <div class="ui column">
                        <label>Seleccione el estudiante: </label>
                        <select name="" id="selectEstudiante" class="ui search fluid dropdown">
                            
                            
                        </select>
                    </div>
                <div class="ui column">
                </div>
            </div>        

        </div>
        <div id="informacionEstudiante" class="ui stackable row">
            
        </div>
    </div>`;
    $("#contenidoGraficas").append(vistaGraficaEstudiante);
    $(".ui.dropdown").dropdown();
    $("#selectGrado").append('<option value="">Seleccione el grado</option>');
    for (let i = 0; i < arregloGrados.length; i++) {
        let grado = arregloGrados[i];
        let txt =`<option value="${grado.codigo}">${grado.grado}</option>`
        $("#selectGrado").append(txt);
    }


    

    $("#selectGrado").change(function () {
        let codigoGrado = $("#selectGrado").val();
        $("#selectEstudiante").empty();
        agregarEstudiantesEnSelect(codigoGrado);
    })

}

/**
 * Función que agrega estudiantes en un select a partir de su código
 * @param {string} codigoGrado 
 */
function agregarEstudiantesEnSelect(codigoGrado) {
    $.ajax({
        url: 'Estudiante/Grado?codigo=' + codigoGrado,
        method: 'GET',
        dataType: 'json',
        headers: {
            token: getCookie("ID_Inst")
        },
        success: function (response) {
            estudiantes= response.estudiantes
            pintarEstudiantesEnSelect({estudiantes})
        },
        error: function (response) {

        }
    })
}

/**
 * Función que coloca una lista de estudiantes en un select que contiene la lista 
 * de estudiantes de un grado
 * @param {Array} estudiantes 
 */
function pintarEstudiantesEnSelect({estudiantes}) {
    let txt='<option value="">Seleccione el estudiante</option>'
    $("#selectEstudiante").append(txt);
    for (let i = 0; i < estudiantes.length; i++) {
        let estudiante= estudiantes[i];
        txt=`
        <option value="${estudiante.documento}">
            ${estudiante.primerNombre} ${estudiante.primerApellido}
        </option>`
        $("#selectEstudiante").append(txt);
    }
    $("#selectEstudiante").change(function () {
        let documentoEstudiante = $("#selectEstudiante").val();
        $("#informacionEstudiante").empty();
        agregarInformacionEstudiante(documentoEstudiante);
    })
}

/**
 * Función que solicita la información de un estudiante particular
 */
function agregarInformacionEstudiante(documento){
    $.ajax({
        url: 'Estadisticas?tipoConsulta=porEstudiante&id=' + documento,
        method: 'GET',
        dataType: 'json',
        headers: {
            token: getCookie("ID_Inst")
        },
        success: function (response) {
            pintarInformacionEstudiante(response.datos)
        },
        error: function (response) {

        }
    })
}
var clicksEstudiante;

function pintarInformacionEstudiante(datos){
    let estudiante= datos.estudiante;
    let citas = datos.citas;
    let historias=datos.historias;
    
    clicksEstudiante = datos.presion;
    let edad = calcularEdad(estudiante.fechaDeNacimiento);
    let listadoHistorias=""
    for (let i = 0; i < historias.length; i++) {
        listadoHistorias+='<li>'+historias[i].titulo+'</li>'
    }
    let sumaClicks=0;
    for (let i = 0; i < clicksEstudiante[1].length; i++) {
        let numeroClicks=clicksEstudiante[1][i];
        sumaClicks+=numeroClicks;
        
    }
    let mesesCitas = new Array(); 
    for (let i = 0; i < citas.meses.length; i++) {
        mesesCitas.push(buscarMesPorNumero(citas.meses[i]));
    }

    let mesesClicks = new Array();
    for (let i = 0; i < datos.presion[0].length; i++) {
        mesesClicks.push(buscarMesPorNumero(datos.presion[0][i]));
        
    }
    let informacionEstudiante = `<div class="ui one wide column">
    
    </div>
    <div class="ui seven wide column">
        <div class="segments">
        <div class="ui segment">
                <div class="ui segments">
                    <h5 class="ui top attached header">
                        Nombre:
                    </h5>
                    <div class="ui attached segment">
                        <p>${estudiante.primerNombre} 
                        ${estudiante.segundoNombre} 
                        ${estudiante.primerApellido}
                        ${estudiante.segundoApellido} </p>

                    </div>
                    <h5 class="ui top attached header">
                        Edad
                    </h5>
                    <div class="ui attached segment">
                        <p>${edad} años</p>
                    </div>
                    <h5 class="ui top attached header">
                        Género
                    </h5>
                    <div class="ui attached segment">
                        <p>${estudiante.genero}</p>
                    </div>

                    <h5 class="ui top attached header">
                        Historias de situaciones de decisión completas
                    </h5>
                    <div class="ui attached segment">
                        ${estudiante.primerNombre} ${estudiante.primerApellido} ha completado ${historias.length} historias:
                        <ul>
                            ${listadoHistorias}
                        </ul>
                    </div>
                    <h5 class="ui top attached header">
                        Uso del botón de pánico
                    </h5>
                    <div class="ui attached segment">
                        <p>${estudiante.primerNombre} ${estudiante.primerApellido} ha utilizado el botón ${sumaClicks} veces en los últimos 6 meses</p>
                    </div>
                </div>

            </div>
        </div>
        

    </div>
    <div class="ui seven wide column"> 
        <div class=" segments">        
            <div class="ui segment">
                <div class="ui segments">
                    <h5 class="ui top attached header">
                        Citas solicitadas previamente
                    </h5>
                    <div class="ui attached segment">
                        <div class="ui one column grid">
                            <div class="ui row">
                                <div class="ui column">
                                    <div class="ui segment">
                                        <canvas id="canvaCitasEstudiante"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <h5 class="ui top attached header">
                        Uso del botón de pánico en el tiempo
                    </h5>
                    <div class="ui attached segment">
                        <div class="ui one column grid">
                            <div class="ui column">
                                <div class="ui segment">
                                    <canvas id="canvaBotonPanicoTiempo"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="ui one wide column">

    </div>
    `
    $("#informacionEstudiante").append(informacionEstudiante);
    chartCitasEstudiante = document.getElementById('canvaCitasEstudiante').getContext('2d');

    var chartCitas = new Chart(chartCitasEstudiante, {
        type: 'bar',
        data: {
            labels: mesesCitas,
            datasets: [{
                label: 'Citas solicitadas previamente',
                data: citas.citas,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(255, 159, 64, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 206, 86, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 159, 64, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 206, 86, 1)'
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
                text: 'Numero de citas en el tiempo'
            },
            layout: {
                padding: {
                    left: 0,
                    right: 25,
                    top: 0,
                    bottom: 0
                }
            },
            legend: {
                display: false
            },
            tooltips: {
                callbacks: {
                    label: function (tooltipItem) {
                        return tooltipItem.yLabel;
                    }
                }
            }
        }
    });


    chartBoton = document.getElementById('canvaBotonPanicoTiempo').getContext('2d');
    var chartBotonPanico = new Chart(chartBoton, {
        type: 'horizontalBar',
        data: {
            labels: mesesClicks,
            datasets: [{
                label: 'Tiempo',
                data: clicksEstudiante[1],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(255, 159, 64, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 206, 86, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(255, 159, 64, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 206, 86, 1)'
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
                text: 'Uso del botón de pánico en funcion del tiempo'
            },
            layout: {
                padding: {
                    left: 0,
                    right: 25,
                    top: 0,
                    bottom: 0
                }
            },
            legend: {
                display: false
            },
            tooltips: {
                callbacks: {
                    label: function (tooltipItem) {
                        return tooltipItem.yLabel;
                    }
                }
            }
        }
    });
}
/**
 * Función que busca el nombre del mes a partir de su número 
 * @param {int} numero que es el número del mes
 * @returns el nombre del mes
 */
function buscarMesPorNumero(numero){
    let meses =['enero','febrero','marzo','abril','mayo','junio','julio','agosto','septiembre','octubre','noviembre','diciembre'];
    return (meses[numero-1]);
}
