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
                organigrama.eventDelete(EventoDelete);
                var cont=0;
                function EventoAdd(id) {
                    var situacionActual = buscarNodo(parseInt(id));
                    cont = situacionActual.opciones.length;
                    console.log(cont)
                    if (cont <= 2){
                        var obj = {
                            idHistoria: historia.id,
                            titulo: "",
                            texto: "",
                            predecesor: id
                        }
                        console.log(obj)
                        registrar(obj, "POST","situacion");
                    }else{
                        toastr.error('No puedes agregar mas de tres opciones');
                    }
                }
                function EventoEdit(id) {
                    crear(id);
                }
                function EventoDelete(id) {
                    eliminarSituacion(id);
                }
            })();
        
        } else {
            console.log("error");
        }
        let botones= $(".btn.btn-primary.btn-block.btn-add");
        for(let i =0; i < botones.length; i++){
            let btnAgregarId= parseInt($(botones[i]).attr("data-id"),10);
            let nodo = buscarNodo(btnAgregarId);
            if(nodo.opciones === undefined){
                cuadroGrande=$(botones[i]).parent().parent()
                $(cuadroGrande).css("background","#4e6582")
                $(botones[i]).remove();
            }
        }
    },
    complete: function (result) {
     },
    error: function (result) {
    }

});
}


