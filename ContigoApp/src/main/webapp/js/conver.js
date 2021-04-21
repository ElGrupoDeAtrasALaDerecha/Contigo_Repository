var usuario
var token
var documento
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
function colocarInfo(array, orador, personal) {
    for (var i = 0; i < personal.length; i++) {
        if (personal[i].documento === orador) {
            text = '<br>' +
                '<img src="'+personal[i].imagen +'" class="imgRedonda">' +
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
        '<h3><span></span> </h3>'+
    '<button id="btnRegistrarEstu" class="banner-button" onclick="registrarEstudiante();">Registrarse</button>'
    $("#titulo").append(text);

    document.getElementById("banner2").style.background = "url(" + array.imagen + ") repeat";


}

$("#btnCrear").on("click", function (e) {
    e.preventDefault();
    if ($("#Texto").val() == "" || $("#Descripcion").val() == "" || $("#cronograma").val() == "" || $("#Lugar").val() == "" || $("#linkImagen").val() == "" || $("#linkInfografia").val() == "" || $("#grados").val() == "") {

    } else {
        CrearConverOrador();

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


const imageUploader = document.getElementById('img-uploader');
const imageUploader2 = document.getElementById('img-uploader2');


const CLOUDINARY_URL = 'https://api.cloudinary.com/v1_1/miguel26697/image/upload';
const CLOUDINARY_UPLOAD_PRESET = 'wmruximj';
var ima
var inforgra

imageUploader.addEventListener('change', (e) => {
    console.log(e)
    e.preventDefault();
    const file = e.target.files[0];
    const formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', CLOUDINARY_UPLOAD_PRESET);

    $.ajax({
        url: CLOUDINARY_URL,
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            informacion = result
            img = informacion.url;
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
});

imageUploader2.addEventListener('change', (e) => {
    console.log(e)
    e.preventDefault();
    const file = e.target.files[0];
    const formData = new FormData();
    formData.append('file', file);
    formData.append('upload_preset', CLOUDINARY_UPLOAD_PRESET);

    $.ajax({
        url: CLOUDINARY_URL,
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            informacion = result
            infogra = informacion.url;
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
});

function crearConversatorio(personal) {
    var documento;
    console.log(getCookie("token"));
    for (var i = 0; i < personal.length; i++) {
        if (getCookie("token") == personal[i].token) {
            documento = personal[i].documento
        }
    }

    titulo = $("#Texto").val();
    descripcion = $("#Descripcion").val();
    cronograma = $("#cronograma").val();
    lugar = $("#Lugar").val();
    infografia = $("#linkInfografia").val();
    clasifica = $("#grados").val();

    informacion = {
        orador: documento,
        titulo: titulo,
        descripcion: descripcion,
        cronograma: cronograma,
        lugar: lugar,
        imagen: img,
        infografia: infogra,
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
            alert("Conversatorio Creado")
            window.location.assign("Conversatorios.html");
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


function registrarEstudiante(){
console.log(conversatorio.id)
idConversatorio=conversatorio.id
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


