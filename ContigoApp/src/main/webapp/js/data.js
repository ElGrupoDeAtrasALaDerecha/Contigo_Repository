var data;
$(document).ready(function () {
crearData();
})

function crearData(){
$.ajax({
    url: "Situacion?id=" + getCookie("idHistoria"),
    type: "GET",
    dataType: "json",
    success: function (result, textStatus, request) {
        if (result != "error") {
            console.log(result);
            data = result.situaciones.primerNodo;
            (function () {
                // Carga de datos para el organigrama
                organigrama.data = data;
                // creación del organigrama, se le manda el id del contenedor
                organigrama.create('organigrama');
                // Agregamos los eventos para los botones
                organigrama.eventAdd(EventoAdd);
                organigrama.eventEdit(EventoEdit);
    
                function EventoAdd(id) {
                    crear(id);
                }
    
                function EventoEdit(id) {
                    console.log(id)
                }
    
            })();
        
        } else {
            console.log("error");
        }

    },
    complete: function (result) {
     },
    error: function (result) {
    }

});
}


