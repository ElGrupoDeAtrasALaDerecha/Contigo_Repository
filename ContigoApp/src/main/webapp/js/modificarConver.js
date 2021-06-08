
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
            document.getElementsByClassName("item form-floating")[3].removeAttribute('data-error')
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
            document.getElementsByClassName("item form-floating")[4].removeAttribute('data-error')
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
                if (usuario === 3) {
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
            /*console.log("Actualizando")
            e.preventDefault();
            if ($("#Texto").val() == "" || $("#Descripcion").val() == "" || $("#cronograma").val() == "" || $("#Lugar").val() == "" || $("#linkImagen").val() == "" || $("#linkInfografia").val() == "" || $("#grados").val() == "") {
                toastr.error("Complete los campos")
            } else {
                ActualizarConverOrador();
            }*/
            validar_actulizar();
        });

    } else if (id === "") {
        $("#btnCrear").on("click", function (e) {
            /*console.log("Creando")
            e.preventDefault();
            if ($("#Texto").val() == "" || $("#Descripcion").val() == "" || $("#cronograma").val() == "" || $("#Lugar").val() == "" || $("#linkImagen").val() == "" || $("#linkInfografia").val() == "" || $("#grados").val() == "") {
                toastr.error("Complete los campos")
            } else {
                CrearConverOrador();
            }*/
            validar_crear();
        });

    }
});




function CrearConverOrador() {
    $.ajax({
        url: "PersonalCalificado",
        type: "GET",
        headers: {
            token: getCookie("token")
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
        headers: {
            token: getCookie("token")
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
                if (metodo == "POST") {
                    setTimeout(function () {
                        window.location.href = "Conversatorios.html";
                    }, 2000);

                } else {
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

function BorrarTexto() {
    document.querySelectorAll('.item[data-error] .Texto').forEach(inpEl => {
        inpEl.addEventListener('input', () => inpEl.parentElement.removeAttribute('data-error'));
    })

    document.querySelectorAll('.item[data-error] .Texto2').forEach(inpEl => {
        inpEl.addEventListener('input', () => inpEl.parentElement.removeAttribute('data-error'));
    })

    document.querySelectorAll('.item[data-error] .Texto3').forEach(inpEl => {
        inpEl.addEventListener('input', () => inpEl.parentElement.removeAttribute('data-error'));
    })

    document.querySelectorAll('.item[data-error] .Texto4').forEach(inpEl => {
        inpEl.addEventListener('input', () => inpEl.parentElement.removeAttribute('data-error'));
    })
}

function validar_crear() {
    let titulo = $("#Texto").val();
    let des = $("#Descripcion").val();
    let lugar = $("#Lugar").val();
    let portada = $("#img-uploader").val();
    let imagen = $("#img-uploader2").val();
    let grados = $("#grados").val();
    let fechaConver = $("#cronograma").val();
    if (titulo == "" || des == "" || lugar == "" || portada == "" || /*imagen == "" ||*/ grados.length == 0 || fechaConver == "") {
        if (titulo == "") {
            document.getElementsByClassName("item form-floating")[0].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor ingrese el nombre del conversatorio')
            BorrarTexto();
        }
        if (des == "") {
            document.getElementsByClassName("item form-floating")[1].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor ingrese la descripción del conversatorio')
            BorrarTexto();
        }
        if (lugar == "") {
            document.getElementsByClassName("item form-floating")[2].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor ingrese el lugar del conversatorio')
            BorrarTexto();
        }
        if (portada == "") {
            document.getElementsByClassName("item form-floating")[3].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor ingrese una portada para el conversatorio')
        }
        /*if (imagen == "") {
            document.getElementsByClassName("item form-floating")[4].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor ingrese la infografía del conversatorio')
        }*/
        if (grados.length == 0) {
            document.getElementsByClassName("item form-floating")[5].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor selecciona los grados del conversatorio')
        }
        if (fechaConver == "") {
            document.getElementsByClassName("item form-floating")[6].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor seleccione la fecha del del conversatorio')
        }
    } else {
        CrearConverOrador();
    }
}

function validar_actulizar() {
    let titulo2 = $("#Texto").val();
    let des2 = $("#Descripcion").val();
    let lugar2 = $("#Lugar").val();
    let portada2 = $("#img-uploader").val();
    let imagen2 = $("#img-uploader2").val();
    let grados2 = $("#grados").val();
    let fechaConver2 = $("#cronograma").val();
    if (titulo2 == "" || des2 == "" || lugar2 == "" || portada2 == "" || /*imagen == "" ||*/ grados2.length == 0 || fechaConver2 == "") {
        if (titulo2 == "") {
            document.getElementsByClassName("item form-floating")[0].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor ingrese el nombre del conversatorio')
            BorrarTexto();
        }
        if (des2 == "") {
            document.getElementsByClassName("item form-floating")[1].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor ingrese la descripción del conversatorio')
            BorrarTexto();
        }
        if (lugar2 == "") {
            document.getElementsByClassName("item form-floating")[2].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor ingrese el lugar del conversatorio')
            BorrarTexto();
        }
        if (portada2 == "") {
            document.getElementsByClassName("item form-floating")[3].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor ingrese una portada para el conversatorio')
        }
        /*if (imagen2 == "") {
            document.getElementsByClassName("item form-floating")[4].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor ingrese la infografía del conversatorio')
        }*/
        if (grados2.length == 0) {
            document.getElementsByClassName("item form-floating")[5].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor selecciona los grados del conversatorio')
        }
        if (fechaConver2 == "") {
            document.getElementsByClassName("item form-floating")[6].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Por favor seleccione la fecha del del conversatorio')
        }
    } else {
        ActualizarConverOrador();
    }
}

$("#dv3").click(function () {
    document.getElementsByClassName("item form-floating")[6].removeAttribute('data-error')
});