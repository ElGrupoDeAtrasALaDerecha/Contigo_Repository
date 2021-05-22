var id = getCookie("idConversatorio");
$(document).ready(function () {
    usuario = parseInt(getCookie("tipoUsuario"));
    token = getCookie("token");
    documento = getCookie("documento");
    $('.ui.dropdown').dropdown();
    console.log(id);
    if (id == ! "") {
        console.log("hola")
    }
});


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

