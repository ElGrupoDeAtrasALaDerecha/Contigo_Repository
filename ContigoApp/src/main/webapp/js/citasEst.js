let monthNames = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
var personal
var horainicio
var horafin
var fechainicio
var fechafin

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
var fecha;

var horas = document.getElementById("horas");
var select = document.getElementById("horas2");
var listaPersonal = document.getElementById('listaPersonal');
listaPersonal.style.display= "none";
horas.style.display="none"

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
        fecha = currentYear + '-' + month + '-' + $(this).attr("id");
        console.log(fecha);
        console.log(contador);
        horas.style.display= "block";

    })
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


function selectHorario(){
    
        value = select.value, //El valor seleccionado
        text = select.options[select.selectedIndex].innerText; //El texto de la opción seleccionada
        console.log(value);
        console.log(text);
        ListaPersonalC(value);
}

function ListaPersonalC(e){  
    console.log(e);
    if(value !== ""){
        listaPersonal.style.display="block";
    }else{
        listaPersonal.style.display="none";
    }
}
