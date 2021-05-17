let monthNames = [
  "Enero",
  "Febrero",
  "Marzo",
  "Abril",
  "Mayo",
  "Junio",
  "Julio",
  "Agosto",
  "Septiembre",
  "Octubre",
  "Noviembre",
  "Diciembre",
];
var horasN = [7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18];
var personal;
var horainicio;
var horafin;
var fechainicio;
var fechafin;
var listCitas;
var horasdisponibles = [];
var fecha;
var historialCitas;

$(document).ready(function () {
  cargarCitas();
});

let currentDate = new Date(); //fecha del pc como ref
let currentDay = currentDate.getDate(); //dia de la semana
let monthNumber = currentDate.getMonth(); //numero mes 0-11
let currentYear = currentDate.getFullYear(); //año
//console.log(currentDay+'--'+monthNumber+'--'+currentYear);
let dates = document.getElementById("dates"); //
let month = document.getElementById("month");
let year = document.getElementById("year");
//flechas
let prevMonthDOM = document.getElementById("prev-month");
let nextMonthDOM = document.getElementById("next-month");

month.textContent = monthNames[monthNumber];
year.textContent = currentYear.toString();

prevMonthDOM.addEventListener("click", mesAnterior);
nextMonthDOM.addEventListener("click", mesSiguiente);

escribirMeses(monthNumber);
var fecha;
var textHora;

var horas = document.getElementById("horas");
var select = document.getElementById("horas2");
var listaPersonal = document.getElementById("listaPersonal");
var btnCitasAgendada = document.getElementById("btnCitasAgendada");
btnCitasAgendada.style.display = "block";
listaPersonal.style.display = "none";
horas.style.display = "none";

function escribirMeses(month) {
  if (month < 4) {
    for (let i = inicioDia(); i > 0; i--) {
      dates.innerHTML += `<div class="date item ">
            <button id="${
              obtenerDias(monthNumber - 1) - (i - 1)
            }" class="ui inverted  disabled basic button">
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
            <button id="${
              obtenerDias(monthNumber - 1) - (i - 1)
            }" class="ui  inverted  disabled  basic button">
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
            <button id="${
              obtenerDias(monthNumber - 1) - (i - 1)
            }" class="ui inverted blue basic button">
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
    mes = month + 1;
    fecha = currentYear + "-" + mes + "-" + $(this).attr("id");
    console.log(fecha);
    console.log(contador);
    horas.style.display = "block";

    variable = $(this).attr("id");
    console.log(variable);
    var mes = month + 1;
    if (mes < 10 && variable > 10) {
      fecha = currentYear + "-" + 0 + mes + "-" + variable;
    } else if (variable < 10 && mes < 10) {
      fecha = currentYear + "-" + 0 + mes + "-" + 0 + variable;
    } else if (mes > 10 && variable < 10) {
      fecha = currentYear + "-" + mes + "-" + 0 + variable;
    } else if (mes > 10 && variable > 10) {
      fecha = currentYear + "-" + mes + "-" + variable;
    }
    for (let i = 0; i < listCitas.length; i++) {
      // console.log("for" + i)
      if (listCitas[i].fecha === fecha) {
        console.log(listCitas[i].horaInicio);
        horasdisponibles.push(listCitas[i].horaInicio);
      }
    }
    filtrarHorasRepetidas();
    horas.style.display = "block";
  });
}

function filtrarHorasRepetidas() {
  var horasUnicas = horasdisponibles.filter(function (item, index, array) {
    return array.indexOf(item) === index;
  });
  cargarHorasSelect(horasUnicas);
}

function obtenerDias(month) {
  if (month === -1) month = 11;
  if (
    month == 0 ||
    month == 2 ||
    month == 4 ||
    month == 6 ||
    month == 7 ||
    month == 9 ||
    month == 11
  ) {
    return 31;
  } else if (month == 3 || month == 5 || month == 8 || month == 10) {
    return 30;
  } else {
    return mesEspecial() ? 29 : 28;
  }
}

function mesEspecial() {
  //mes bisiesto
  return (
    (currentYear % 100 !== 0 && currentYear % 4 === 0) ||
    currentYear % 400 === 0
  );
}

function inicioDia() {
  //saber el dia 1 del mes
  let start = new Date(currentYear, monthNumber, 1);
  if (start.getDay() - 1 === -1) {
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
  dates.textContent = "";
  escribirMeses(monthNumber);
}

function selectHorario(fecha) {
  var cita = fecha + "-" + select;
  console.log(cita);
  // value = select.value, //El valor seleccionado
  // text = select.options[select.selectedIndex].innerText; //El texto de la opción seleccionada
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

/*******************************************DIV EMERGENTE************************************************* */
const openEls = document.querySelectorAll("[data-open]");
const closeEls = document.querySelectorAll("[data-close]");
const isVisible = "is-visible";
for (const el of openEls) {
  el.addEventListener("click", function () {
    const modalId = this.dataset.open;
    document.getElementById(modalId).classList.add(isVisible);
  });
}

for (const el of closeEls) {
  el.addEventListener("click", function () {
    this.parentElement.parentElement.parentElement.classList.remove(isVisible);
  });
}

document.addEventListener("click", (e) => {
  if (e.target == document.querySelector(".modal.is-visible")) {
    document.querySelector(".modal.is-visible").classList.remove(isVisible);
    limpiarDiv();
  }
});

document.addEventListener("keyup", (e) => {
  // if we press the ESC
  if (e.key == "Escape" && document.querySelector(".modal.is-visible")) {
    document.querySelector(".modal.is-visible").classList.remove(isVisible);
    limpiarDiv();
  }
});
/************************************************DIV EMERGENTE******************** */
var divTexto = document.getElementById("divEmergente");
var contConf = 0;
var contCanc = 0;
function llenarDiv(cita) {
  divTexto =
    `<p>DATOS DE SU CITA: </p>` +
    `<p>Fecha: ${cita.fecha} </p>` +
    `<p>Hora: ${cita.hora}:00</p>` +
    `<p>Personal calificado: ${cita.personal} </p>` +
    `<div class="ui buttons">
        <button id="btnCancelarC" class="ui button">Cancelar Cita</button>
        <div class="or"></div>
        <button id="btnConfirmarC" class="ui blue button">Confirmar cita</button>
        </div>`;
  $("#divEmergente").append(divTexto);

  $("#btnCancelarC").click(function () {
    contCanc++;
    console.log(contCanc);
    document.querySelector(".modal.is-visible").classList.remove(isVisible);
    limpiarDiv();
    return false;
  });

  $("#btnConfirmarC").click(function () {
    agendarCita(citaS,personal)
  });
}


function limpiarDiv() {
  $("#divEmergente").empty();
}


/**********************************************LISTA */
function cargarHorasSelect(horasdisponibles) {
  var m = "";
  $("#horas2").empty();
  for (var i = 0; i < horasdisponibles.length; i++) {
    if (horasdisponibles[i] > 11) {
      m = " :00 pm";
    } else {
      m = " :00 am";
    }
    let horasSelect2 =
      '<option value ="' +
      horasdisponibles[i] +
      '">' +
      horasdisponibles[i] +
      m +
      "</option>";
    $("#horas2").append(horasSelect2);
  }
}

$("#horas2").click(function percaHora() {
  var hora = $("#horas2").val();
  var cita = {
    fecha: fecha,
    hora: hora,
  };
  getPerca(cita)
});

var citaDelDia ;

function getPerca(cita) {    
    $.ajax({
      url: "Cita?tipo=getPerca",
      type: "GET",
      dataType: "json",
      headers: {
        fecha: cita.fecha,
        hora: cita.hora
      },
      contentType: "JSON application/json charset=utf-8",
      beforeSend: function () {},
      success: function (response) {
        if (response.tipo === "ok") {
            citaDelDia = response.perca;
            console.log(response)
            listarPerca(response.perca)
        }
      },
      complete: function (result) {},
      error: function (result) {},
    });   
  }

function cargarCitas() {
  $.ajax({
    url: "Cita",
    type: "GET",
    dataType: "json",
    contentType: "JSON application/json charset=utf-8",
    beforeSend: function () {},
    success: function (response) {
      if (response.tipo === "ok") {
        listCitas = response.citas;
        console.log(response.citas);
      }else{
        console.log("falla")
      }
    },
    complete: function (result) {},
    error: function (result) {
      alert("Error interno");
    },
  });
}
var personal
var citaS
function listarPerca(perca) {
    console.log(perca)
    personal = perca
    listaPersonal.style.display = "block";
    $("#perca").empty()
    for (let index = 0; index < perca.length; index++) {
        $("#perca").append('<div class="item"> <img src="'+ perca[index].imagen+'"> <input type="radio" id="' + perca[index].id_perca +'" name="percaD" value="' + perca[index].nombre_perca + '"> <label for="' + perca[index].id_perca +'"> <div class="content"> <a id="personalCalificadoLista" class="header" href="#">' + perca[index].nombre_perca + '</a> </div> </label> </div>') 
    }
}
// Comentario para arreglar la línea temporal del desfase por culpa de ustedes y no mia
$("#btnAgenddamiento").click(function getDatos() {
    var cita = {
        fecha: fecha,
        hora: $("#horas2 option:selected").val(),
        personal: $('input:radio[name=percaD]:checked').val()
    };
    citaS = cita
    llenarDiv(cita)
})

function agendarCita(cita, personal) {
  for (let index = 0; index < personal.length; index++) {
    if (personal[index].nombre_perca == cita.personal) {
      cita.idc = personal[index].id
      break;
    }
    console.log(cita)
  }
  var obj={
        id:cita.idc
  }
  solicitarCita(obj);
}

function obtenerHistorial() {
  $.ajax({
    url: "Cita?tipo=historialEstudiante",
    type: "GET",
    dataType: "json",
    headers: {
      token: getCookie("token"),
    },
    contentType: "JSON application/json charset=utf-8",
    beforeSend: function () {},
    success: function (response) {
      if (response.tipo === "ok") {
        historialCitas = response.citas;
      }
    },
    complete: function (result) {},
    error: function (result) {},
  });
}

function solicitarCita(obj){
  $.ajax({
    url: "Cita",
    type: "POST",
    dataType: "json",
    headers: {
      token: getCookie("token"),
    },
    data: JSON.stringify(obj),
    contentType: "JSON application/json charset=utf-8",
    beforeSend: function () {},
    success: function (response) {
      if(response.tipo==="ok"){
          alert("Cita asignada correctamente\n Se recomienda estar con diez minutos de anticipación en la cita")
          window.location.assign("opciones.html");
      }
    },
    complete: function (result) {},
    error: function (result) {
        console.log(result);
    },
  });
}
