var motivo;
var recomendaciones;
var estado;

$("#btnCrear").click( function () {
    /*id = 4;
    estado = $('#Asistencia2').val();
    console.log(estado)
    motivo = $("#Motivo").val();
    console.log(motivo)
    recomendaciones = $("#Recomendaciones").val();
    console.log(recomendaciones)

    var obj = {
        id: id,
        estado: estado,
        motivo: motivo,
        recomendaciones: recomendaciones
    }
    guardarRegistro(obj)
    */
   validar_campos();
});
function guardarRegistro(obj) {
    $.ajax({
        url: 'Cita',
        method: 'PUT',
        dataType: 'json',
        data: JSON.stringify(obj),
        contentType: 'JSON application/json charset=utf-8',
        success: function (response) {
            if (response.tipo == "ok") {
                toastr.success("Se ha ha registrado la información" + response.mensaje)
                limpiarInput()
            }
        },
        error: function (response) {
            toastr.error('Error al guardar el registro')
            console.log(response.mensaje)
        }
    })
}

/*
function limpiarInput() {
    document.getElementById("estado").value = "";
    document.getElementById("horafin").value = "";
    document.getElementById("fecha").value = "";
    document.getElementById("fecha2").value = "";
}*/

function BorrarTexto() {
    document.querySelectorAll('.item[data-error] .Texto').forEach(inpEl => {
        inpEl.addEventListener('input', () => inpEl.parentElement.removeAttribute('data-error'));
    })

    document.querySelectorAll('.item[data-error] .Texto2').forEach(inpEl => {
        inpEl.addEventListener('input', () => inpEl.parentElement.removeAttribute('data-error'));
    })

    document.querySelectorAll('.item[data-error] .Texto3').forEach(inpEl => {
        inpEl.addEventListener('input', () => inpEl.parentElement.removeAttribute('data-error'));
    })

    document.querySelectorAll('.item[data-error] .Texto4').forEach(inpEl => {
        inpEl.addEventListener('input', () => inpEl.parentElement.removeAttribute('data-error'));
    })
}

function validar_campos() {
    let id = 4;
    let nombreP = $("#Nombre").val();
    let apellidoP = $("#Apellido").val();
    let numDocumento = $("#Documento").val();
    let edad = $("#Edad").val();
    let asistencia = $("#Asistencia2").val();
    let motivo = $("#Motivo").val();
    let descripcion = $("#Recomendaciones").val();
    
    if (nombreP == "" || apellidoP == "" || numDocumento == "" || edad == "" || asistencia == "" || motivo == "" || descripcion == "") {
        if (nombreP == "") {
            document.getElementsByClassName("item form-floating")[0].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese el nombre del paciente')
            BorrarTexto();
        }
        if (apellidoP == "") {
            document.getElementsByClassName("item form-floating")[1].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese el apellido del paciente')
            BorrarTexto();
        }
        if (numDocumento == "") {
            document.getElementsByClassName("item form-floating")[2].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese el número de docuemnto del paciente')
            BorrarTexto();
        }
        if (edad == "") {
            document.getElementsByClassName("item form-floating")[3].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese la edad del paciente')
            BorrarTexto();
        }
        if (asistencia == "") {
            document.getElementsByClassName("item form-floating")[4].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor seleccione el estado de la asistencia ')
        }
        if (motivo == "") {
            document.getElementsByClassName("item form-floating")[5].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese el motivo de la cita')
            BorrarTexto();
        }
        if (descripcion == "") {
            document.getElementsByClassName("item form-floating")[6].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese una descripción')
            BorrarTexto();
        }
    } else {
        let obj = {
            id: id,
            estado: asistencia,
            motivo: motivo,
            recomendaciones: descripcion
        }
        guardarRegistro(obj)
    }
}

$("#dv11").click(function(){
    document.getElementsByClassName("item form-floating mb-4")[4].removeAttribute('data-error')
});