var n = 0;
var historia;
var situaciones;
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
                document.getElementById('tituloHistoria').innerHTML = historia.titulo;
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

function buscarNodo(id, nodo) {
    if (nodo === undefined) {
        nodo = data;
    }
    if (nodo.id === id) {
        return nodo;
    } else {
        let opciones = nodo.opciones;
        if (opciones !== undefined) {
            for (let i = 0; i < opciones.length; i++) {
                let opcion = opciones[i];
                if (opcion.id === id) {
                    return opcion;
                } else {
                    if (opcion.opciones !== undefined && opcion.opciones.length > 0) {
                        let nodoEncontrado = buscarNodo(id, opcion);
                        if (nodoEncontrado !== undefined) {
                            return nodoEncontrado;
                        }
                    }
                }
            }
        }
    }
}

function crear(id) {
    var header
    let situacion = buscarNodo(parseInt(id));
    console.log(situacion)
    let textoAdicional =""
    if(situacion.opciones !== undefined){
        textoAdicional += '  <input type="submit"  class="btn-final crearFinal" value="Establecer final">';
        header="situacion"
    }else{
        header="final"
    }
    let txt = '<div class="overlay active" id="overlay">' +
        '<div class="popup active" id="popup">' +
        '<a href="#" id="btn-cerrar-popup" class="btn-cerrar-popup" onclick="eliminar()"> ' +
        '<i class="fa fa-times" aria-hidden="true"></i>' + '</a>' +
        '<h4>Editar Situacion</h4>' +
        '<form action="">' +
        '<div class="contenedor-inputs">' +
        '<input type="text" id="titulo" placeholder="Titulo" value="' + situacion.titulo + '">' +
        '<input type="text" id="descripcion" placeholder="Descripción" value="' + situacion.texto + '">' +
        '<div class="numeroOpc">' +
        //'<p>Numero de opciones (Max 3)</p>' +
        // '<button id="opciones" class="Aumentar" onclick=mas()>+</button>' +
        //  '<button class="Disminuir" id="menos" onclick=quitaropc()>-</button>' +
        '</div>' +
        '<div id="extra"></div>' +
        '</div>' +
        '<div class="botonesForm">' +
        //'<input type="submit"  class="btn-submit crearSituacion" value="Crear">'
        '<input type="submit" class="btn-submit actualizarSituacion" value="Actualizar" >  '
        + textoAdicional
        + '</div>'
        + '</form>'
        + '</div>'
        + '</div';
    $("#ventana").append(txt);
    $(".Aumentar ").click(function (e) {
        e.preventDefault();
    });
    $(".Disminuir").click(function (e) {
        e.preventDefault();
    });
    $(".btn-submit").click(function (e) {
        e.preventDefault();
    });
    /*
    $(".crearSituacion").click(function (e) {
   
        let inputsTitulos = $(".opcion");
        aux = 0;
        for (let i = 0; i < inputsTitulos.length; i++) {
            if ($(inputsTitulos[i]).val() === "") {
                aux++;
            }
        }
        for (let i = 0; i < inputsTitulos.length; i++) {
            if (aux === 0) {
                var obj = {
                    idHistoria: historia.id,
                    titulo: $(inputsTitulos[i]).val(),
                    predecesor: id
                }
                console.log(obj)
                registrar(obj, "POST");
            }
        }
        if (aux !== 0) {
            toastr.error('Complete los campos')
        }
    });
*/
    $(".crearFinal").click(function (e) {
        var titulo = $("#titulo").val();
        var descripcion = $("#descripcion").val();
        var obj = {
            texto: descripcion,
            titulo: titulo,
            predecesor: situacion.predecesor,
            id:parseInt(id)

        }
        console.log(obj)
        registrar(obj, "POST", "final");
    });

    $(".actualizarSituacion").click(function (e) {
        var titulo = $("#titulo").val();
        var descripcion = $("#descripcion").val();
        var situacionActualizada = {
            idHistoria: historia.id,
            texto: descripcion,
            titulo: titulo,
            id: parseInt(id)
        }
        console.log(situacionActualizada);
        console.log(header)
        registrar(situacionActualizada, "PUT", header);
    });
}

function eliminarSituacion(id){
    let situacion = buscarNodo(parseInt(id));
    if(situacion.opciones !== undefined){
        header="situacion"
    }else{
        header="final"
    }

    $.ajax({
        url: "Situacion?id=" +parseInt(id),
        type: "DELETE",
        headers: {
            tipo: header
        },
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            if (result.tipo != "error") {
                console.log(result)
                toastr.success(result.mensaje)
                crearData();
            } else {
                console.log(result)
                toastr.error(result.mensaje)
            }
        },
        complete: function (result) {

        },
        error: function (result) {
            console.log(result);
        }

    });


}

/**
 * Método para registrar la historia (aquí va el ajax)
 * @param {*} obj 
 */
function registrar(obj, metodo, header) {
    $.ajax({
        url: "Situacion",
        type: metodo,
        dataType: "json",
        data: JSON.stringify(obj),
        headers: {
            tipo: header
        },
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            if (result.tipo != "error") {
                console.log(result)
                toastr.success(result.mensaje)
                crearData();
            
            } else {
                console.log(result)
                toastr.error(result.mensaje)
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



