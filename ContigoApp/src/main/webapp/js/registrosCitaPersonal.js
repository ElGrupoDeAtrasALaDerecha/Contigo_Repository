var motivo;
var recomendaciones;
var estado;

$(document).ready(function () {
    id =4;
    estado = $("#Asistencia").val();
    motivo = $("#Motivo").val();
    recomendaciones = $("#Recomendaciones").val();
    var obj={
        id :id,
        estado :estado,
        motivo : motivo,
        recomendaciones :recomendaciones
    }
    guardarRegistro(obj)
});

function guardarRegistro(obj){
    $.ajax({
        url: 'Agenda',
        method: 'PUT',
        dataType: 'json',
        data: JSON.stringify(obj),
        contentType: 'JSON application/json charset=utf-8',
        success: function (response) {
            if (response.tipo == "ok") {
                toastr.success(response.mensaje)
                limpiarInput()
            } 
        },
        error: function (response) {

        }
    })
}

function limpiarInput(){
        document.getElementById("estado").value = "";
        document.getElementById("horafin").value = "";
        document.getElementById("fecha").value = "";
        document.getElementById("fecha2").value = "";
}