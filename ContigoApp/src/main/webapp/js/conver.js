var usuario
var token
var documento
var conversatorio
var btnRegistrar = document.getElementById("btnRegistrarEstu");
var oradordiv;

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
    limpiarDiv();
    divTexto =
        `<p><b>DATOS DEL CONVERSATORIO </b></p>` +
        `<p><b>Conversatorio:</b> ${array.titulo} </p>` +
        `<p><b>Orador: </b> ${oradordiv.primerNombre} ${oradordiv.primerApellido} </p>` +
        `<p><b>Lugar:</b> ${array.lugar} </p>` +
        `<p><b>Cronograma:</b> ${array.cronograma} </p>` +
        `<div class="ui buttons">
        <button id="btnConfirmarC" class="ui blue button">Confirmar registro</button>
        </div>`;
    $("#divEmergente").append(divTexto);
    $("#btnCancelarC").click(function () {
        cancelarRegistro();
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
    confimarSiEstaRegistrado();
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
            headers: {
                token: getCookie("token")
            },
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
            console.log(result)
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
            '<p>'+ conversatorio[i].descripcion + '</p>'+
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

function TraerOrador(conver, orador) {
    conversatorio = conver
    $.ajax({
        url: "PersonalCalificado",
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        headers: {
            token: getCookie("token")
        },
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
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

/**
 * 
 * @param {*} array 
 * @param {*} orador 
 * @param {*} personal 
 */
function colocarInfo(array, orador, personal) {
    for (var i = 0; i < personal.length; i++) {
        if (personal[i].documento === orador) {
            var bio = buscarBiografia(personal[i]);
            var txtBio = "";
            if (bio !== undefined) {
                txtBio = bio;
            }
            oradordiv = personal[i];
            text = '<br>' +
            '<div class= "divimg">'+
                '<img src="' + personal[i].imagen + '" class="imgRedonda">' +
                '</div>'+
                '<h2>' +
                '<div class= "oradorAling">'+
                '<span > ' + personal[i].primerNombre + ' ' + personal[i].primerApellido + '</span>' +
                '<p> Orador </p>'
                '</div>'
                '<br>' +
                '</h2>' 
              
                
            $("#orador").append(text);
            break;
        }
    }
    
    text = '<br>' +
        '<h2>Descripcion:</h2>' +
        '<br>' +
        '<p>' + array.descripcion + '</p>' +
        '<br>' +
        '<h2>Lugar:</h2>' +
        '<br>' +
        '<p>' + array.lugar + '</p>' +
        '<br>' +
        '<h2>Cronograma:</h2>' +
        '<br>' +
        '<p>' + array.cronograma + '</p>'+
        '<br>' +
        '<h2>Orador:</h2>' +
        '<br>' +
        `<p>${txtBio}</p>`
        

    

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
    } else if (usuario === 2) {
        btnRegistrar.style.display = "none"
        text += '<button id="btnModificar" class="banner-button" onclick="ModificarConversatorio();">Modificar</button>'
    } else if (usuario === 3) {
        btnRegistrar.style.display = "none"
        text += '<button id="btnModificar" class="banner-button" onclick="ModificarConversatorio();">Modificar</button>'
    }
    $("#titulo").append(text);
    document.getElementById("banner2").style.background = "url(" + array.imagen + ") repeat";

    $("#btnRegistrarEstu").click(function () {
        llenarDiv(array, oradordiv);
    })
}

/* Registro del estudiante en el conversatorio */
function confimarSiEstaRegistrado(){

    $.ajax({
        url: "REstudianteConversatorio?id=" + parseInt(getCookie("idcar")) + "&idEstudiante=" + documento,
        type: "GET",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            console.log(result)
            if (result.tipo == "error") {
            
                btnRegistrar.innerText = "Registrarse"
                btnRegistrar.setAttribute("id", "btnRegistrarEstu");
                btnRegistrar.removeAttribute("onClick", "cancelarRegistro();");
                btnRegistrar.setAttribute("data-open", "modal1")
                
            } else {
                btnRegistrar.innerText = "Cancelar Registro"
                btnRegistrar.setAttribute("id", "btnCancelarC");
                btnRegistrar.removeAttribute("data-open")
                btnRegistrar.setAttribute("onClick", "cancelarRegistro();");
            }
        }, complete: function (result) {
          
        }, error: function (result) {
    
        }
    });


}


function cancelarRegistro() {

    $.ajax({
        url: "REstudianteConversatorio?id=" + parseInt(getCookie("idcar")) + "&idEstudiante=" + documento,
        type: "DELETE",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            if (result.tipo == "error") {
               
                confimarSiEstaRegistrado();
            } else {
                toastr.success(result.mensaje)
                confimarSiEstaRegistrado();
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
             
                confimarSiEstaRegistrado();
            } else {
                toastr.success(result.mensaje)
                document.querySelector(".modal.is-visible").classList.remove(isVisible);
                confimarSiEstaRegistrado();
               
            }
        }, complete: function (result) {
            console.log(result)
        }, error: function (result) {
            console.log(result)
        }
    });
}

function ModificarConversatorio() {
    setCookie("idConversatorio", conversatorio.id, 0.3);
    window.location.assign("crear_cnv.html")

}



/**
 * Método que permite buscar la biografía de un personal
 * @param {personal} personal 
 */
function buscarBiografia(personal) {
    let info = personal.info;
    if (info !== undefined) {
        for (let i = 0; i < info.length; i++) {
            if (info[i].hasOwnProperty("biografia"))
                return info[i].biografia;
        }
    } else {
        return undefined;
    }
}


