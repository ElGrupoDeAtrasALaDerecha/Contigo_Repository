



$(document).ready(function () {
    cargarHistorialE();
});
function cargarHistorialE() {
    $.ajax({
        url: "Cita?tipo=historialEstudiante",
        type: "GET",
        headers: {
            token: getCookie("token")
        },
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (response) {
            var historialCita = response.citas
            mostrarHistorial(historialCita);

        }, complete: function (result) {

        }, error: function (result) {
            console.log(result);

        }
    });
}

function mostrarHistorial(historialCita) {
    console.log(historialCita)
    $("#historial").append("")
    var texto = "";
    for (let i = 0; i < historialCita.length; i++) {
        texto = ""
        if (historialCita[i].estado === 1) {
            texto += '<div class="item ui blue segment">'
        } else if (historialCita[i].estado === 2) {
            texto += '<div class="item ui green segment">'
        } else if (historialCita[i].estado === 3) {
            texto += '<div class="item ui grey segment">'
        } else if (historialCita[i].estado === 4) {
            texto += '<div class="item ui red segment">'
        }
        texto += ' <div class="image">' +
            '<img src="' + historialCita[i].imagen + '">' +
            '</div>' +
            '<div class="content">' +
            '<a class="header">Fecha: '+historialCita[i].fecha+' </a>' +
            '<div class="meta">' +
            '<span class="cinema">Personal Calificado:  ' + historialCita[i].nombre_perca + '</span>' +
            '<br><br>' +
            '<span class="cinema">Lugar o link: ' + historialCita[i].lugar + '</span>' +
            '<br><br>' +
            '<span class="cinema">Hora: ' + historialCita[i].horaInicio + ':00 </span>' +
            '</div>' +
            '<div class="description">' +
            '<p></p>' +
            '</div>' +
            '<div class="extra">'
        if (historialCita[i].estado === 1) {
            texto += '<div id="estado" class="ui blue inverted segment">Cita sin Asignar</div>'
        } else if (historialCita[i].estado === 2) {
            texto += '<div id="estado" class="ui green inverted segment">Cita Asignada</div> '
        } else if (historialCita[i].estado === 3) {
            texto += '<div id="estado" class="ui grey inverted segment">Cita Asistida</div>'
        } else if (historialCita[i].estado === 4) {
            texto += '<div id="estado" class="ui red inverted segment">Cita Cancelada</div>'
        }
        texto += '</div>' +
            '</div>' +
            '</div>'
        $("#historial").append(texto)
        $(".cancelar").click(function(){
            /*Aquí se supone que se cancela la cita
            $.ajax({
                url: "Cita",
                type: "",
                headers: {
                    token: getCookie("token")
                },
                dataType: "json",
                contentType: "JSON application/json charset=utf-8",
                beforeSend: function () {
                },
                success: function (response) {
                    var historialCita = response.citas
                    mostrarHistorial(historialCita);
        
                }, complete: function (result) {
        
                }, error: function (result) {
                    console.log(result);
        
                }
            });*/
        })
    }
}
