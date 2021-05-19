
//Tabla de situaciones comunes
var tablaSituacionesComunes = new Chart(document.getElementById('situacionesComunes'), {
    type: 'horizontalBar',
    data: {
        labels: ['Historia 1','Historia 2', 'Historia 3', 'Historia 4'],
        datasets: [{
            label:'Situaciones de decisiones más frecuentadas',
            backgroundColor: ['rgb(86, 89, 107)',
            'rgb(57, 136, 136)',
            'rgb(176, 196, 222)',
            'rgb(230, 230, 250)'],
            data:[320,280,232,195],
        }]
    },
    options:{
        scales:{
            xAxes:[{
                ticks:{
                    beginAtZero:true
                }
            }]
        },
        title: {
            display: true,
            text: 'Situaciones más frecuentadas'
        },
        legend:{
            display:false
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


//Tabla inscripción a conversatorios

var tablaInscripcionAconversatorio = new Chart( document.getElementById('conversatoriosComunes').getContext('2d'), {
    type: 'bar',
    data: {
        labels: ['Sexto', 'Octavo', 'Noveno', 'Décimo'],
        datasets: [{
            label: 'Grados',
            data: [110, 100, 120, 150],
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


//Tabla uso de chat

var tablaUsoChatPrivado = new Chart(document.getElementById('usoChat').getContext('2d'), {
    type: 'pie',
    data: {
        labels: ['Sí', 'No'],
        datasets: [{
            data: [30, 70],
            backgroundColor: [
            'rgba(95, 158, 160)',
            'rgba(165, 42, 42)'],
            borderColor: [
            'rgba(95, 158, 160, 1)',
            'rgba(165, 42, 42, 1)'],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            xAxes: [{
                gridLines: {
                    display: false
                }
            }],
            yAxes: [{
                gridLines:{
                    display: false,
                    drawBorder:false
                },
                ticks: {
                    beginAtZero: true,
                    display:false
                }
            }]
        },
        title: {
            display: true,
            text: 'Uso del chat privado por parte de todos los estudiantes'
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


var chart = new Chart(document.getElementById('cantidadEstudiantes').getContext('2d'), {
    type: 'line',
    data: {
        labels: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo'],
        datasets: [{
            label: 'Cantidad de estudiantes usando Contigo en la Institución ',
            borderColor: '#2d4059',

            backgroundColor: 'rgba(176, 196, 222, 0.35)',
            data: [200, 240, 410, 391, 350]
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