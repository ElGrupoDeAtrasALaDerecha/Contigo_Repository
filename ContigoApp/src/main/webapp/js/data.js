var data;
var byeAdd;
var byeEdit;
var byeDelete;
var byeFinal;
var enTutorial = true;
const params = new URLSearchParams(window.location.search)
var idHistoria;
var l = 0; // variable de tutorial
var tieneHistoria;
$(document).ready(function () {
    idHistoria = getCookie("idHistoria");
    if (idHistoria === "") {
        idHistoria = params.get("idHistoria");
        if (idHistoria === "") {
            alert("inválido");
            window.location.assign("index.html");
        }

        salirTutotial();

    } else {
        tutorial();
        desabilitarBoton();
    }
    obtenerhisotia();
    crearData();


})

function desabilitarBoton() {
    byeAdd = $(".btn.btn-primary.btn-block.btn-add");
    byeEdit = $('.btn.btn-success.btn-block.btn-edit');
    byeDelete = $('.btn.btn-danger.btn-block.btn-delete');
    let textoPaso2 = `<p id="TextoTutorial" class="TextoTutorial">
    Paso 2: Parate encima de la casilla que se muestra y selecciona el boton +, este te creara una nueva rama.
    </p>`;
    let textoPaso3 = `<p id="TextoTutorial" class="TextoTutorial">
  Paso 3: Para hacer que un nodo sea el final deberas ubicarte encima de la casilla creada y oprimir el boton de edición, esto te habrira un popup, en este llenaras los datos y se debe oprimir el boton establecer final
    
  </p>`;
    let textoPaso4 = `<p id="TextoTutorial" class="TextoTutorial">
  Paso 4: Por ultimo parate encima de la casilla de la nueva rama y oprime el boton -, este eliminara la casilla seleccionada
    
  </p>`;

    byeFinal = $(".btn-final.crearFinal");
    if (l == 0) {
        console.log('estoy en el if 1')
        console.log(byeAdd)
        $(byeAdd[0]).css("visibility", "hidden")
        byeDelete.css("visibility", "hidden")
        byeFinal.css("visibility", "hidden")
    }

    if (l === 1) {

        console.log('estoy en el if 2')
        byeAdd.css("visibility", "visible")
        byeEdit.css("visibility", "hidden")
        byeDelete.css("visibility", "hidden")
        $(".TextoTutorial").replaceWith(textoPaso2);


    }
    if (l === 2) {
        console.log('estoy en el if 3')
        $(byeAdd[0]).css("visibility", "hidden")
        $(byeEdit[0]).css("visibility", "hidden")
        $(byeDelete[0]).css("visibility", "hidden")
        $(byeAdd[1]).css("visibility", "hidden")
        $(byeEdit[1]).css("visibility", "visible")
        $(byeDelete[1]).css("visibility", "hidden")
        $(".TextoTutorial").replaceWith(textoPaso3);



    }
    if (l === 3) {
        console.log('estoy en el if 4')
        $(byeAdd[0]).css("visibility", "hidden")
        $(byeEdit[0]).css("visibility", "hidden")
        $(byeDelete[0]).css("visibility", "hidden")
        $(byeAdd[1]).css("visibility", "hidden")
        $(byeEdit[1]).css("visibility", "hidden")
        $(byeDelete[1]).css("visibility", "visible")
        $(".TextoTutorial").replaceWith(textoPaso4);

    }

    if (l === 4) {
        byeAdd.css("visibility", "visible")
        byeEdit.css("visibility", "visible")
        byeDelete.css("visibility", "visible")
        salirTutotial();
    }
}

function crearData() {
    $.ajax({
        url: "Situacion?id=" + parseInt(idHistoria),
        type: "GET",
        dataType: "json",
        headers: {
            token: getCookie("token"),
        },
        success: function (result, textStatus, request) {
            if (result != "error") {
                console.log(result);
                data = result.situaciones.primerNodo;
                tieneHistoria = result.tieneHistoria;
                (function () {
                    // Carga de datos para el organigrama
                    organigrama.data = data;
                    // creación del organigrama, se le manda el id del contenedor
                    organigrama.create('organigrama');
                    // Agregamos los eventos para los botones
                    organigrama.eventAdd(EventoAdd);
                    organigrama.eventEdit(EventoEdit);
                    organigrama.eventDelete(EventoDelete);
                    var cont = 0;
                    function EventoAdd(id) {
                        l = 2;// variable de tutorial
                        var situacionActual = buscarNodo(parseInt(id));
                        cont = situacionActual.opciones.length;
                        console.log(cont)
                        if (cont <= 2) {
                            var obj = {
                                idHistoria: historia.id,
                                titulo: "",
                                texto: "",
                                predecesor: id
                            }
                            console.log(obj)
                            registrar(obj, "POST", "situacion");
                        } else {
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
            let botones = $(".btn.btn-primary.btn-block.btn-add");
            for (let i = 0; i < botones.length; i++) {
                let btnAgregarId = parseInt($(botones[i]).attr("data-id"), 10);
                let nodo = buscarNodo(btnAgregarId);
                if (nodo.opciones === undefined) {
                    cuadroGrande = $(botones[i]).parent().parent()
                    $(cuadroGrande).css("background", "#4e6582")
                    $(botones[i]).remove();
                }
            }
            if (tieneHistoria && enTutorial && getCookie('idHistoria') !== "") {
                salirTutotial();
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

function obtenerhisotia() {
    $.ajax({
        url: "Historia?id=" + idHistoria,
        type: "GET",
        dataType: "json",
        success: function (result, textStatus, request) {
            if (result != "error") {
                console.log(result);
                historia = result.historia;
                //document.getElementById('tituloHistoria').innerHTML = historia.titulo; 
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
