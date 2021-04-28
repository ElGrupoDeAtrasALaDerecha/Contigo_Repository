var n = 0;
var historia;
window.onload = function obtenerhisotia() {
    var text;
    $.ajax({
        url: "Historia?id=" + getCookie("idHistoria"),
        type: "GET",
        dataType: "json",
        success: function (result, textStatus, request) {
            if (result != "error") {
                console.log(result);
                historia = result.historia;
                console.log(getCookie("idHistoria"))
                
            } else {
                console.log("error");
            }

        },
        complete: function (result) {


        },
        error: function (result) {

        }

    });
    crearRamita();
}


function mas() {
    n = n + 1;
    console.log('N', n)
    if (n < 4) {
        let txt = '<div>' +
            '<input type="text" class="opcion" placeholder="ingrese la opción ' + n + '" id="' + n + '"></input>' +
            '</div>';
        $('#extra').append(txt);
    }
}

function quitaropc() {
    console.log('Nr', n)
    if (n >= 3) {
        n = 3;
    }
    if (n != 0) {
        $('#' + n + '').remove();
        n = n - 1;
    }
}



function obtenerNombre() {
    var nom = $('#Nombre').val();
    return nom;
}


function crear(id) {
    /*aquí se supone que va otro ajax*/
    let txt = '<div class="overlay active" id="overlay">' +
        '<div class="popup active" id="popup">' +
        '<a href="#" id="btn-cerrar-popup" class="btn-cerrar-popup" onclick="eliminar()"> ' +
        '<i class="fa fa-times" aria-hidden="true"></i>' + '</a>' +
        '<h3> ' + historia.titulo + ' </h3>' +
        '<h4>Formulario</h4>' +
        '<form action="">' +
        '<div class="contenedor-inputs">' +
        '<input type="text" id="descripcion" placeholder="Descripción" value="'+situacion.texto+'">' +
        '<div class="numeroOpc">' +
        '<p>Numero de opciones (Max 3)</p>' +
        '<button id="opciones" class="Aumentar" onclick=mas()>+</button>' +
        '<button class="Disminuir" id="menos" onclick=quitaropc()>-</button>' +
        '</div>' +
        '<div id="extra"></div>' +
        '</div>' +
        '<input type="submit"  class="btn-submit crearSituacion" value="Crear">'
        + '</form>'
        + '</div>'
        + '</div';
    $("#ventana").append(txt);
    $(".Aumentar ").click(function(e){
        e.preventDefault();
    });
    $(".Disminuir").click(function(e){
        e.preventDefault();
    });
    $(".btn-submit").click(function(e){
        e.preventDefault();
    });
    $(".crearSituacion").click(function(){
        let descripcion=$("#descripcion").val();
        let situacionActualizada={
            texto:descripcion,
            id:id
        }
        registrar(situacionActualizada,"PUT");
        let inputsTitulos=$(".opcion");
        for (let i = 0; i < inputsTitulos.length; i++) {
            let obj = {
                idHistoria:historia.id,
                titulo:$(inputsTitulos[i]).val(),
                predecesor:id
            }
            registrar(obj,"POST"); 
        }  
    });
}

/**
 * Método para registrar la historia (aquí va el ajax)
 * @param {*} obj 
 */
function registrar(obj,metodo){
    $.ajax({
        url: "Situacion",
        type: metodo,
        dataType: "json",
        data: JSON.stringify(obj),
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            if (result.tipo != "error") {
                console.log(result)
            } else {
                console.log(result)
                
            }
        },
        complete: function (result) {

        },
        error: function (result) {
            console.log(result);
        }

    });
}

function eliminar() {
    $('#overlay').remove();
    n = 0;
}





var situaciones
function crearRamita() {
   
}




