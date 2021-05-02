var horas = [7, 8, 9, 10, 11, 12, 1,2,3,4,5,6];
var horainicio 
var horafin
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
            } else {
                toastr.error('Error al crear agenda')
            }
        },
        error: function (response) {

        }
    })
}

function validarFechasHoras() {
    var f1 = new Date(fechainicio);
    var f2 = new Date(fechafin);
    if (f1 < f2) {
        if (horainicio > horafin) {
            var obj = {
                personal: personal,
                fechainicio: fechainicio,
                fechafin: fechafin,
                horainicio: horainicio,
                horafin: horafin
            }
            crearAgenda(obj)
        } else {
            console.log(horainicio)
            console.log(horafin)
            alert("Error , las horas ingresadas no son válidas")
        }
    } else {
        
        alert("Error , las fechas ingresadas no son válidas")
    }

}

function cargarHorasSelect() {
    var m=""
    for (var i = 0; i < horas.length; i++) {
        if(horas[i]<7){
            m=" pm"
        }
        else{
            m=" am"
        }
        let horasSelect = '<option value ="' + horas[i]  + '">' + horas[i] + m+
            '</option>';
        $("#horainicio").append(horasSelect);
        $("#horafin").append(horasSelect);

    }
}