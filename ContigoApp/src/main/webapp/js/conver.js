var usuario
var token
$(document).ready(function () {
    LlamarGrado();
    usuario = parseInt(getCookie("tipoUsuario"));
    token = parseInt(getCookie("token"));
    documento = parseInt(getCookie("documento"));
    $('.ui.dropdown').dropdown();
});

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
        }
    });
}


function LlamarEstudiante(grado) {

    $.ajax({
        url: "Estudiante?id=" + documento + "",
        type: "GET",
        dataType: "json",

        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            estudiante = result.estudiante;
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
        }
    });

}

function LlamarConver(arregloConver) {

    $.ajax({
        url: "Conversatorio",
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            conversatorios = result.conversatorios;
            if (usuario === 1) {
                var converEstudiante = [];
                console.log(conversatorios)
                for (var i = 0; i < conversatorios.length; i++) {
                    for (var j = 0; j < arregloConver.length; j++) {
                        if (conversatorios[i].id === arregloConver[j].idConversatorio) {
                            converEstudiante[j] = conversatorios[i];
                        }
                    }
                }

                listarConver(converEstudiante);
            } else {
                listarConver(conversatorios);
            }



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
    });
};

function TraerOrador(conver, orador) {
    $.ajax({
        url: "PersonalCalificado",
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
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
function colocarInfo(array, orador, personal) {
    for (var i = 0; i < personal.length; i++) {
        if (personal[i].documento === orador) {
            text = '<br>' +
                '<img src="https://www.definicionabc.com/wp-content/uploads/2015/03/orador.jpg" class="imgRedonda">' +
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
        //'<a id="btnRegistrarEstu" class="banner-button">Registrarse</a>'
    $("#titulo").append(text);


}

$("#btnCrear").on("click", function () {
if ($("#Texto").val() == "" || $("#Descripcion").val() == "" || $("#cronograma").val()=="" || $("#Lugar").val() == "" || $("#linkImagen").val() == "" ||  $("#linkInfografia").val() == "" || $("#grados").val() == "") {
    
} else{
    CrearConverOrador();
    alert("Conversatorio Creado")
}
});



function CrearConverOrador() {
    $.ajax({
        url: "PersonalCalificado",
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            personal = result.personales;
            crearConversatorio(personal)
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

function crearConversatorio(personal) {
    var documento;
    console.log(getCookie("token"));
    for (var i = 0; i < personal.length; i++) {
        if (parseInt(getCookie("token")) == parseInt(personal[i].token)) {
            documento = personal[i].documento
        }
    }

    titulo = $("#Texto").val();
    descripcion = $("#Descripcion").val();
    cronograma = $("#cronograma").val();
    lugar = $("#Lugar").val();
    imagen = $("#linkImagen").val();
    infografia = $("#linkInfografia").val();
    clasifica = $("#grados").val();

    informacion = {
        orador: documento,
        titulo: titulo,
        descripcion: descripcion,
        cronograma: cronograma,
        lugar: lugar,
        imagen: imagen,
        infografia: infografia,
        clasificacion: clasifica

    };

    console.log(informacion);
    $.ajax({
        url: "Conversatorio",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(informacion),
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            console.log(result);
            if (result != "error") {
                console.log(result);
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

