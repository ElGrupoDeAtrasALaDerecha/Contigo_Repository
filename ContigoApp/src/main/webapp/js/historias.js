$(document).ready(function () {
    usuario = getCookie("tipoUsuario");
    token = getCookie("token");
    if(usuario!=="2"||token===undefined){
        alert("No autorizado");
        window.location.assign("index.html");
    }
    $('.ui.dropdown').dropdown();
    $('#crearHistoria').prop('disabled',true);
});


$("#img-uploader").change(function(){
    filename=this.files[0].filename
    console.log(filename);
})


const imageUploader = document.getElementById('img-uploader');
const CLOUDINARY_URL = 'https://api.cloudinary.com/v1_1/miguel26697/image/upload';
const CLOUDINARY_UPLOAD_PRESET = 'wmruximj';
var img


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
            let txt=`<div class="ui active inverted dimmer">
            <div class="ui indeterminate text loader">Cargando imagen</div>
          </div>`;
            $(".espacioImagen").append(txt);
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
            $('.ui.active.inverted.dimmer').remove();
            $('#crearHistoria').prop('disabled',false);
        },
        error: function (result) {
        }
    });
});


$("#crearHistoria").click(function(){
    crearHistoria();
})
function crearHistoria() {

    nombre = $("#Nombre").val();
    descripcion = $("#Descripcion").val();
    clasifica = $("#grados").val();

    informacion = {
        documentoCreador: "1000853623",
        titulo: nombre,
        descripcion: descripcion,
        urlImagen: img,
        clasificacion: clasifica
    };


    console.log(informacion);
    $.ajax({
        url: "Historia",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(informacion),
        headers:{
            token:getCookie("token"),
        },
        
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            console.log(result);
            if (result != "error") {
                console.log(result);
                setCookie('idHistoria',result.idHistoria, 0.1)
                $(location).attr('href', 'situaciones.html?id='+getCookie("idHistoria"));
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

function actualizarGradosHistorias(){
    clasifica = $("#grados").val();
    $.ajax({
        url: "Historia",
        type: "DELETE",
        headers:{
            id:getCookie("idHistoria"),
            clasificacion: clasifica
        },
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            console.log(result);
            if (result != "error") {
                console.log('siiiii funciono')
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


