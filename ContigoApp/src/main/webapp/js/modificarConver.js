
// Subir la imagen al servidor de CLOUDINARY

const imageUploader = document.getElementById('img-uploader');
const imageUploader2 = document.getElementById('img-uploader2');


const CLOUDINARY_URL = 'https://api.cloudinary.com/v1_1/miguel26697/image/upload';
const CLOUDINARY_UPLOAD_PRESET = 'wmruximj';
var img
var infogra
var documento;
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

// Inicio de la pagina, caraga de datos
var id = "";
var conversatorio;

$(document).ready(function () {
    id = getCookie("idConversatorio");
    usuario = parseInt(getCookie("tipoUsuario"));
    token = getCookie("token");
    documento = getCookie("documento");
    $('.ui.dropdown').dropdown();
    console.log(id);
    console.log(usuario)
    console.log(token)
    if (id !== "") {

        btnActualizar = document.getElementById("btnCrear");
        btnActualizar.innerText = "Actualizar"
        btnActualizar.setAttribute("id", "btnActualizar");
        document.getElementById("tituloPagina").innerText = "Actualizar Conversatorio"
        $.ajax({
            url: "Conversatorio?id=" + id,
            type: "GET",
            dataType: "json",
            contentType: "JSON application/json charset=utf-8",
            headers:{
                token:token
            },
            beforeSend: function () {
            },
            success: function (result, textStatus, request) {
                console.log(result)
                conversatorios = result.conversatorios;
                var clasificacion = result.clasificacion
                for (var i = 0; i < conversatorios.length; i++) {
                    if (conversatorios[i].id === parseInt(id)) {
                        conversatorio = conversatorios[i];
                    }
                }
                console.log(conversatorio)
                $("#Texto").val(conversatorio.titulo);
                $("#Descripcion").val(conversatorio.descripcion);
                $("#Lugar").val(conversatorio.lugar)
                $("#cronograma").val(conversatorio.cronograma)
                img = conversatorio.imagen
                infogra = conversatorio.infografia
                documento = conversatorio.orador

                var text = ""
                if ( usuario === 3){
                   document.getElementById("Texto").disabled = true;
                   document.getElementById("Descripcion").disabled = true;
                   document.getElementById("Lugar").disabled = true;
                   document.getElementById("cronograma").disabled = true;
                   document.getElementById("img-uploader").disabled = true;
                   document.getElementById("img-uploader2").disabled = true;
                }
                for (var i = 0; i < clasificacion.length; i++) {
                    console.log("hola")
                    text += '<a class="ui label transition visible" data-value="' + clasificacion[i].id + '" style="display: inline-block !important;">' + clasificacion[i].grado + '<i class="delete icon"></i></a>'
                    $("#grados").val(clasificacion[i].id)

                }
                $(".ui.search.fluid.dropdown.selection.multiple").append(text);



            }, complete: function (result) {

            }, error: function (result) {
                console.log(result)
            }
        });


        $("#btnActualizar").on("click", function (e) {
            console.log("Actualizando")
            e.preventDefault();
            if ($("#Texto").val() == "" || $("#Descripcion").val() == "" || $("#cronograma").val() == "" || $("#Lugar").val() == "" || $("#linkImagen").val() == "" || $("#linkInfografia").val() == "" || $("#grados").val() == "") {
                toastr.error("Complete los campos")
            } else {
                ActualizarConverOrador();
            }
        });

    } else if (id === "") {
        $("#btnCrear").on("click", function (e) {
            console.log("Creando")
            e.preventDefault();
            if ($("#Texto").val() == "" || $("#Descripcion").val() == "" || $("#cronograma").val() == "" || $("#Lugar").val() == "" || $("#linkImagen").val() == "" || $("#linkInfografia").val() == "" || $("#grados").val() == "") {
                toastr.error("Complete los campos")
            } else {
                CrearConverOrador();
            }
        });

    }
});




function CrearConverOrador() {
    $.ajax({
        url: "PersonalCalificado",
        type: "GET",
        headers:{
            token:getCookie("token")
        },
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            personal = result.personales;
            crearConversatorio(personal, "POST")
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


function ActualizarConverOrador() {
    $.ajax({
        url: "PersonalCalificado",
        type: "GET",
        headers:{
            token:getCookie("token")
        },
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            personal = result.personales;
            crearConversatorio(personal, "PUT")
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





function crearConversatorio(personal, metodo) {
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


    console.log(img)
    console.log(infogra)

    clasifica = $("#grados").val();
    console.log(clasifica)
    if (metodo === "POST") {
        id = 0;
    }
    informacion = {
        id: id,
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
        type: metodo,
        dataType: "json",
        headers:{
            token:getCookie("token")
        },
        data: JSON.stringify(informacion),
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            console.log(result);
            if (result.tipo == "error") {
                console.log(result);
                toastr.error(result.mensaje)
                
            } else {
                toastr.success(result.mensaje)
                if(metodo == "POST"){
                    setTimeout(function () {
                        window.location.href = "Conversatorios.html"; 
                     }, 2000); 
               
                }else{
                    setTimeout(function () {
                        window.location.href = "Conversatorio_unico.html"; 
                     }, 2000);  
                }
       
           
            }
        },
        complete: function (result) {
           
        },
        error: function (result) {
        }
    });

}


