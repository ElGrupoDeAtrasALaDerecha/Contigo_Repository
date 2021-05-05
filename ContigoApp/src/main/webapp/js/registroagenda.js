let horas = [7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18];
var horainicio=0;
var horafin=0;
var fechainicio
var fechafin

$(document).ready(function () {
    personal = getCookie("token");
    cargarHorasSelect();
});

$("#Button3").on("click", function () {
    horainicio = $('#horainicio').val();
    horafin = $('#horafin').val();
    fechainicio = $('#fecha').val();
    fechafin = $('#fecha2').val();
    validarFechasHoras();
});

function crearAgenda(obj) {
    $.ajax({
        url: 'Agenda',
        method: 'POST',
        dataType: 'json',
        data: JSON.stringify(obj),
        contentType: 'JSON application/json charset=utf-8',
        success: function (response) {
            if (response.tipo == "ok") {
                toastr.success('Se ha creado la agenda del personal')
                limpiarInput()
            } 
        },
        error: function (response) {

        }
    })
}

function validarFechasHoras() {
    var f1 = new Date(fechainicio);
    var f2 = new Date(fechafin);
    if (f1 <= f2) {
        if (horainicio < horafin+1) {
            var obj = {
                personal: personal,
                fechainicio: fechainicio,
                fechafin: fechafin,
                horainicio: horainicio,
                horafin: horafin
            }
            crearAgenda(obj)
        } else {
            toastr.error("Error , las horas ingresadas no son válidas")
        }
    } else {

        toastr.error("Error , las fechas ingresadas no son válidas")
    }

}

function cargarHorasSelect() {
    var m = ""
    for (var i = 0; i < horas.length; i++) {
        if (horas[i] > 11) {
            m = " :00 pm"
        }
        else {
            m = " :00 am"
        }
        let horasSelect = '<option value ="' + horas[i] + '">' + horas[i] + m +
            '</option>';
        $("#horainicio").append(horasSelect);
        $("#horafin").append(horasSelect);

    }
}


function limpiarInput(){
    document.getElementById("horainicio").value = "";
    document.getElementById("horafin").value = "";
    document.getElementById("fecha").value = "";
    document.getElementById("fecha2").value = "";
}