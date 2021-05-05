var motivo;
var recomendaciones;
var estado;

$("#btnCrear").on("click", function () {
    id =4;
    estado = $('#Asistencia2').val();
    console.log(estado)
    motivo = $("#Motivo").val();
    console.log(motivo)
    recomendaciones = $("#Recomendaciones").val();
    console.log(recomendaciones)

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
        url: 'Cita',
        method: 'PUT',
        dataType: 'json',
        data: JSON.stringify(obj),
        contentType: 'JSON application/json charset=utf-8',
        success: function (response) {
            if (response.tipo == "ok") {
                toastr.success("Se ha ha registrado la información"+ response.mensaje)
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