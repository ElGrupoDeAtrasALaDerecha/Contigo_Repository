var usuario
var token
var documento

/*******************************************DIV EMERGENTE************************************************* */
const openEls = document.querySelectorAll("[data-open]");
const closeEls = document.querySelectorAll("[data-close]");
const isVisible = "is-visible";
for (const el of openEls) {
    el.addEventListener("click", function () {
        const modalId = this.dataset.open;
        console.log(modalId)
        document.getElementById(modalId).classList.add(isVisible);
    });
}

for (const el of closeEls) {
    el.addEventListener("click", function () {
        this.parentElement.parentElement.parentElement.classList.remove(isVisible);
    });
}

document.addEventListener("click", (e) => {
    if (e.target == document.querySelector(".modal.is-visible")) {
        document.querySelector(".modal.is-visible").classList.remove(isVisible);
        limpiarDiv();
    }
});

document.addEventListener("keyup", (e) => {
    // if we press the ESC
    if (e.key == "Escape" && document.querySelector(".modal.is-visible")) {
        document.querySelector(".modal.is-visible").classList.remove(isVisible);
        limpiarDiv();
    }
});
/************************************************DIV EMERGENTE******************** */
var divTexto = document.getElementById("divEmergente");
var contConf = 0;
var contCanc = 0;

function llenarDiv(array, oradordiv) {
    divTexto =
        `<p>DATOS DEL CONVERSATORIO: </p>` +
        `<p>Conversatorio: ${array.titulo} </p>` +
        `<p>Orador: ${oradordiv.primerNombre} ${oradordiv.primerApellido} </p>` +
        `<p>Lugar: ${array.lugar} </p>` +
        `<p>Cronograma: ${array.cronograma} </p>` +
        `<div class="ui buttons">
        <button id="btnCancelarC" class="ui button">Cancelar registro</button>
        <div class="o"></div>
        <button id="btnConfirmarC" class="ui blue button">Confirmar registro</button>
        </div>`;
    $("#divEmergente").append(divTexto);

    $("#btnCancelarC").click(function () {
        cancelarRegistro();
        document.querySelector(".modal.is-visible").classList.remove(isVisible);
        limpiarDiv();
        return false;
    });

    $("#btnConfirmarC").click(function () {
        registrarEstudiante();
    });
}


function limpiarDiv() {
    $("#divEmergente").empty();
}

/* Inico de la pagina y carga de los elementos*/

$(document).ready(function () {

    usuario = parseInt(getCookie("tipoUsuario"));
    token = getCookie("token");
    documento = getCookie("documento");
    $('.ui.dropdown').dropdown();
    console.log(usuario)
    console.log(documento)
    console.log(token)
    if (usuario === 1) {
        LlamarGrado();
    } else {
        $.ajax({
            url: "Conversatorio",
            type: "GET",
            dataType: "json",
            contentType: "JSON application/json charset=utf-8",
            beforeSend: function () {
            },
            success: function (result, textStatus, request) {
                console.log(result)
                conversatorios = result.conversatorios;
                listarConver(conversatorios);
                if (result != "error") {
                    console.log(result);
                } else {
                    console.log("error");
                }
            }, complete: function (result) {

            }, error: function (result) {
                console.log(result)
            }
        });
    }

});

/* Se hace un llamado general a los grados*/

function LlamarGrado() {

    $.ajax({
        url: "Grado",
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            grado = result.Grados;
            LlamarEstudiante(grado);
            if (result != "error") {
            } else {
                console.log("error");
            }
        }, complete: function (result) {

        }, error: function (result) {
            console.log(result)
        }
    });
}

/* Hacemos el llamado de los estudiente por el documento */

function LlamarEstudiante(grado) {

    $.ajax({
        url: "Estudiante?id=" + documento + "",
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            estudiante = result.estudiante;
            console.log(result)
            var clasificacion;
            for (var i = 0; i < grado.length; i++) {
                if (grado[i].codigo == estudiante.grado) {
                    clasificacion = grado[i].clasificacion_id
                }
            }
            console.log(clasificacion)
            LlamarClasi(clasificacion)
            if (result != "error") {

            } else {
                console.log("error");
            }
        }, complete: function (result) {

        }, error: function (result) {
            console.log(result)
        }
    });
}



function LlamarClasi(clasi) {
    informacion = {
        id: clasi,
    };
    $.ajax({
        url: "ClasificacionServlet",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(informacion),
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            arregloConver = result.clasificacion
            LlamarConver(arregloConver)
            if (result != "error") {
                console.log(result);
            } else {
                console.log("error");
            }
        },
        complete: function (result) {

        },
        error: function (result) {
            console.log(result);
        }
    });

}
var converEstudiante = [];
function LlamarConver(arregloConver) {
    console.log(arregloConver)
    $.ajax({
        url: "Conversatorio",
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            console.log(result)
            conversatorios = result.conversatorios;
            console.log(conversatorios)
            for (var i = 0; i < conversatorios.length; i++) {
                for (var j = 0; j < arregloConver.length; j++) {
                    if (conversatorios[i].id === arregloConver[j].idConversatorio) {
                        converEstudiante[j] = conversatorios[i];
                    }
                }
            }
            listarConver(converEstudiante);

        }, complete: function (result) {

        }, error: function (result) {
            console.log(result)
        }
    });
}

function listarConver(conversatorio) {
    $("#conver").empty();
    for (var i = 0; i < conversatorio.length; i++) {
        text = '<div id = "' + conversatorio[i].id + '" class="col-md-6 col-sm-6 conversatorio">' +
            '<div id="Caja-texto">' +
            '<img src="' + conversatorio[i].imagen + '" class="img-portafolio">' +
            '<div class="textoSobre-img">' + conversatorio[i].titulo +
            '</div>' +
            '</div>' +
            '</div>';
        $("#conver").append(text);
    }
    if (usuario === 2) {
        text = '<a id="btnAgregar" class="banner-button">Agregar Conversatorio</a>'
        $("#Agregar").append(text);
    }

    $(".conversatorio").on("click", function () {
        idcar = $(this).prop("id");
        setCookie("idcar", idcar, 0.5);
        window.location.assign("Conversatorio_unico.html")
    });

    for (var i = 0; i < conversatorio.length; i++) {
        if (parseInt(conversatorio[i].id) === parseInt(getCookie("idcar"))) {
            TraerOrador(conversatorio[i], conversatorio[i].orador);
        }
    }

    $("#btnAgregar").on("click", function () {
        window.location.assign("crear_cnv.html")
        setCookie("idConversatorio", "", 0.5);
    });
};
var conversatorio
function TraerOrador(conver, orador) {
    conversatorio = conver
    $.ajax({
        url: "PersonalCalificado",
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            console.log(result)
            personal = result.personales;
            colocarInfo(conver, orador, personal)
            if (result != "error") {
                console.log(result);
            } else {
                console.log("error");
            }
        }, complete: function (result) {

        }, error: function (result) {
        }
    });
}
var btnRegistrar = document.getElementById("btnRegistrarEstu");

function colocarInfo(array, orador, personal) {
    for (var i = 0; i < personal.length; i++) {
        if (personal[i].documento === orador) {
            var oradordiv = personal[i];
            text = '<br>' +
                '<img src="' + personal[i].imagen + '" class="imgRedonda">' +
                '<br> Orador:' +
                '<center>' +
                '<h2>' +
                '<span> ' + personal[i].primerNombre + ' ' + personal[i].primerApellido + '</span>' +
                '<br>' +
                '</h2>' +
                '</center>' +
                '<br>' +
                '<p></p>' +
                '<br>'
            $("#orador").append(text);
        }
    }
    text = '<br>' +
        'Descripcion:' +
        '<br><br>' +
        '<p>' + array.descripcion + '</p>' +
        '<br><br>' +
        'Lugar:' +
        '<br><br>' +
        '<p>' + array.lugar + '</p>' +
        '<br><br>' +
        'Cronograma:' +
        '<br><br>' +
        '<p>' + array.cronograma + '</p>' +
        '<br><br><br><br><br><br><br><br>' +
        '<br><br><br><br><br><br><br><br>' +
        '<br><br><br><br>'

    $("#cronograma").append(text);

    text = '<br>' +
        '<img src="' + array.infografia + '">'
    '<br>' +
        '<br>'
    $("#infografia").append(text);
    text = '<h2>' +
        '<span>' + array.titulo + '</span>' +
        '<br>' +
        '</h2>' +
        '<p> </p>' +
        '<h3><span></span> </h3>'
    if (usuario === 1) {
        btnRegistrar.style.display = "block"
        //text += '<button id="btnRegistrarEstu"  class="banner-button" onclick="divConfRegistro();">Registrarse</button>'
    } else if (usuario === 2) {
       btnRegistrar.style.display = "none"
        text += '<button id="btnModificar" class="banner-button" onclick="ModificarConversatorio();">Modificar</button>'
    }else if (usuario === 3) {
        btnRegistrar.style.display = "none"
         text += '<button id="btnModificar" class="banner-button" onclick="ModificarConversatorio();">Modificar</button>'
     }
    $("#titulo").append(text);
    document.getElementById("banner2").style.background = "url(" + array.imagen + ") repeat";

    $("#btnRegistrarEstu").click(function () {
        llenarDiv(array, oradordiv);
    })
}



function cancelarRegistro() {
    console.log(conversatorio.id)
    $.ajax({
        url: "REstudianteConversatorio?id=" + parseInt(idConversatorio)+"&idEstudiante="+ documento,
        type: "DELETE",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            if (result.tipo == "error") {
                toastr.error(result.mensaje)
            } else {
                toastr.success(result.mensaje)
            }
        }, complete: function (result) {
            console.log(result)
        }, error: function (result) {
            console.log(result)
        }
    });
}



function registrarEstudiante() {

    console.log(conversatorio.id)
    idConversatorio = conversatorio.id
    informacion = {
        idConversatorio: idConversatorio,
        idEstudiante: documento,
    };
    $.ajax({
        url: "REstudianteConversatorio",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(informacion),
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            if (result.tipo == "error") {
                toastr.error(result.mensaje)
            } else {
                toastr.success(result.mensaje)
            }
        }, complete: function (result) {
            console.log(result)
        }, error: function (result) {
            console.log(result)
        }
    });
}

function ModificarConversatorio() {
    setCookie("idConversatorio",conversatorio.id , 0.3);
    window.location.assign("crear_cnv.html")

}







