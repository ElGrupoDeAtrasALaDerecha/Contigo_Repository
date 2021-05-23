var data;
var byeAdd;
var byeEdit ;
var byeDelete ;
var enTutorial = true;
var l = 0; // variable de tutorial
$(document).ready(function () {
crearData();
})

function desabilitarBoton(){
    byeAdd = $(".btn.btn-primary.btn-block.btn-add");
    byeEdit = $('.btn.btn-success.btn-block.btn-edit');
    byeDelete = $('.btn.btn-danger.btn-block.btn-delete');

    if (l == 0) {
        console.log('estou en el if 1')
        console.log(byeAdd)
        $(byeAdd[0]).css("visibility","hidden") 
        byeDelete.css("visibility", "hidden")
    }

    if (l === 1) {
        console.log('estou en el if 2')
        byeAdd.css("visibility", "visible")
        byeEdit.css("visibility", "hidden")
        byeDelete.css("visibility", "hidden")
    }

    if (l === 2) {
        console.log('estoy en el if 3')
        $(byeAdd[0]).css("visibility", "hidden")
        $(byeEdit[0]).css("visibility", "hidden")
        $(byeDelete[0]).css("visibility", "hidden")
        $(byeAdd[1]).css("visibility", "hidden")
        $(byeEdit[1]).css("visibility", "hidden")
        $(byeDelete[1]).css("visibility", "visible")
        
    }

    if (l === 3) {
        byeAdd.css("visibility", "visible")
        byeEdit.css("visibility", "visible")
        byeDelete.css("visibility", "visible")
        salirTutotial();
    }
}

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
                    l = 2;// variable de tutorial
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
        if (enTutorial) {
            desabilitarBoton();    
        }
        
    },
    complete: function (result) {
     },
    error: function (result) {
    }

});
}


