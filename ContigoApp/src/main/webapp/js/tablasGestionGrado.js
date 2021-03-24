
//Tabla de situaciones comunes
var tablaSituacionesComunes = new Chart(document.getElementById('situacionesComunes'), {
    type: 'horizontalBar',
    data: {
        labels: ['Historia 1','Historia 2', 'Historia 3', 'Historia 4'],
        datasets: [{
            label:'Situaciones de decisiones más frecuentadas',
            backgroundColor: ['rgb(66, 134, 244)',
            'rgb(255, 160, 122)',
            'rgb(176, 196, 222)',
            'rgb(255, 182, 193)'],
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
            'rgba(255, 99, 132, 0.2)',
            'rgba(255, 159, 64, 0.2)'],
            borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(255, 159, 64, 1)'],
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


