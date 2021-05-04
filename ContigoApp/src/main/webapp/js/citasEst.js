let monthNames = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
var horasN = [7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18];
var personal
var horainicio
var horafin
var fechainicio
var fechafin
var listCitas
var horasdisponibles = [];
var fecha

$(document).ready(function () {
    cargarCitas();
});

let currentDate = new Date();//fecha del pc como ref
let currentDay = currentDate.getDate();//dia de la semana
let monthNumber = currentDate.getMonth();//numero mes 0-11
let currentYear = currentDate.getFullYear();//año
//console.log(currentDay+'--'+monthNumber+'--'+currentYear);
let dates = document.getElementById('dates');//
let month = document.getElementById('month');
let year = document.getElementById('year');
//flechas
let prevMonthDOM = document.getElementById('prev-month');
let nextMonthDOM = document.getElementById('next-month');

month.textContent = monthNames[monthNumber];
year.textContent = currentYear.toString();

prevMonthDOM.addEventListener('click', mesAnterior);
nextMonthDOM.addEventListener('click', mesSiguiente);

escribirMeses(monthNumber);

var horas = document.getElementById("horas");
var select = document.getElementById("horas2");
var listaPersonal = document.getElementById('listaPersonal');
listaPersonal.style.display = "none";
horas.style.display = "none"

function escribirMeses(month, botonPresionado) {
    if (month < 4) {
        for (let i = inicioDia(); i > 0; i--) {
            dates.innerHTML += `<div class="date item ">
            <button id="${obtenerDias(monthNumber - 1) - (i - 1)}" class="ui inverted  disabled basic button">
                ${obtenerDias(monthNumber - 1) - (i - 1)}
                </button>
                </div>`;
        }
        for (let i = 1; i <= obtenerDias(month); i++) {
            dates.innerHTML += `<div class="date">
            <button id="${i}" class="ui inverted  disabled basic button">
            ${i}
            </button>
            </div>`;
        }
    }
    if (month === 4) {
        for (let i = inicioDia(); i > 0; i--) {
            dates.innerHTML += `<div class="date item ">
            <button id="${obtenerDias(monthNumber - 1) - (i - 1)}" class="ui  inverted  disabled  basic button">
                ${obtenerDias(monthNumber - 1) - (i - 1)}
                </button>
                </div>`;
        }
        for (let i = 1; i <= obtenerDias(month); i++) {
            if (i === currentDay) {
                dates.innerHTML += `<div class="date item today">
                <button id="${i}" class="ui inverted  blue basic button">
                ${i}
                </button>
                </div>`;

            } else if (i < currentDay) {
                dates.innerHTML += `<div class="date">
                <button id="${i}" class="ui inverted disabled basic   button">
                ${i}
                </button>
                </div>`;
            } else if (i > currentDay) {
                dates.innerHTML += `<div class="date">
                <button id="${i}"class="ui inverted  blue basic button">
                ${i}
                </button>
                </div>`;
            }
        }
    } else if (month > 3) {
        for (let i = inicioDia(); i > 0; i--) {
            dates.innerHTML += `<div class="date item ">
            <button id="${obtenerDias(monthNumber - 1) - (i - 1)}" class="ui inverted blue basic button">
                ${obtenerDias(monthNumber - 1) - (i - 1)}
                </button>
                </div>`;
        }
        for (let i = 1; i <= obtenerDias(month); i++) {
            dates.innerHTML += `<div class="date">
            <button id="${i}"class="ui inverted blue basic button">
            ${i}
            </button>
            </div>`;
        }

    }

    var contador = 0;
    $(".ui.inverted.basic.button").click(function (e) {
        contador++;
        variable = $(this).attr("id");
        console.log(variable)
        if (month < 10 && variable > 10) {
            fecha = currentYear + '-' + 0 + month + '-' + variable;
        } else if (variable < 10 && month < 10) {
            fecha = currentYear + '-' + 0 + month + '-' + 0 + variable;
        } else if (month > 10 && variable < 10) {
            fecha = currentYear + '-' + month + '-' + 0 + variable;
        } else if (month > 10 && variable > 10) {
            fecha = currentYear + '-' + month + '-' + variable;
        }
        for (let i = 0; i < listCitas.length; i++) {
            console.log("for" + i)
            if (listCitas[i].fecha === fecha) {
                console.log(listCitas[i].horaInicio)
                horasdisponibles.push(listCitas[i].horaInicio)
                console.log("entro")
            }
        }
        filtrarHorasRepetidas()
        console.log(fecha);
        console.log(contador);
        horas.style.display = "block";

    })
}

function filtrarHorasRepetidas() {
    var horasUnicas = horasdisponibles.filter(function (item, index, array) {
        return array.indexOf(item) === index;
    })
    cargarHorasSelect(horasUnicas)

}

function obtenerDias(month) {
    if (month === -1) month = 11;
    if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11) {
        return 31;

    } else if (month == 3 || month == 5 || month == 8 || month == 10) {
        return 30;

    } else {

        return mesEspecial() ? 29 : 28;
    }
}

function mesEspecial() {//mes bisiesto
    return ((currentYear % 100 !== 0) && (currentYear % 4 === 0) || (currentYear % 400 === 0));

}

function inicioDia() {//saber el dia 1 del mes
    let start = new Date(currentYear, monthNumber, 1);
    if ((start.getDay() - 1) === -1) {
        return 6;
    } else {
        return start.getDay() - 1;

    }
    //return ((start.getDay()-1) === -1) ? 6 : start.getDay()-1;
}
function mesAnterior() {
    if (monthNumber !== 0) {
        monthNumber--;
    } else {
        monthNumber = 11;
        currentYear--;
    }
    nuevaFecha();
}

function mesSiguiente() {
    if (monthNumber !== 11) {
        monthNumber++;
    } else {
        monthNumber = 0;
        currentYear++;
    }
    nuevaFecha();
}

function nuevaFecha() {
    currentDate.setFullYear(currentYear, monthNumber, currentDay);
    month.textContent = monthNames[monthNumber];
    year.textContent = currentYear.toString();
    dates.textContent = '';
    escribirMeses(monthNumber);
}


function selectHorario(fecha) {
    console.log(fecha)
    value = select.value, //El valor seleccionado
        text = select.options[select.selectedIndex].innerText; //El texto de la opción seleccionada
    ListaPersonalC(value);
}

function ListaPersonalC(e) {
    console.log(e);
    if (value !== "") {
        listaPersonal.style.display = "block";
    } else {
        listaPersonal.style.display = "none";
    }
}


function cargarHorasSelect(horasdisponibles) {
    var m = ""
    for (var i = 0; i < horasdisponibles.length; i++) {
        if (horasdisponibles[i] > 11) {
            m = " :00 pm"
        }
        else {
            m = " :00 am"
        }
        let horasSelect2 = '<option value ="' + horasdisponibles[i] + '">' + horasdisponibles[i] + m +
            '</option>';
        $("#horas2").append(horasSelect2);
    }
}

function cargarCitas() {
    $.ajax({
        url: "Cita",
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (response) {
            if (response.tipo === "ok") {
                listCitas = response.citas;
            }
        }, complete: function (result) {

        }, error: function (result) {
            alert("Error interno")
        }
    });
}

