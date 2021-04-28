
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

//Registro de PERCA
$("#Button2").click(function(e){
    e.preventDefault();
    crearPerca();
})
function crearPerca() {
    documento = $("#documento").val();
    primerNombre = $("#primerNombre").val();
    primerApellido = $("#primerApellido").val();
    correo = $("#correo").val();
    fechaDeNacimiento= $("#fecha").val();
    segundoNombre = $("#segundoNombre").val();
    segundoApellido = $("#segundoApellido").val();
    contraseña = $("#contraseña").val();
    genero = "femenino" 

    if (document === "" || primerNombre === ""|| primerApellido === ""|| correo === "" || fechaDeNacimiento === ""|| segundoNombre === "" || segundoApellido === "" || contraseña === "" || genero === "") {
        toastr.warning("Verifica que todos los datos estén compeltos")
    } else {
        informacion = {
            documento: documento,
            tipoDocumento:"1",
            primerNombre: primerNombre,
            primerApellido:primerApellido,
            correo: correo,
            fechaDeNacimiento:fechaDeNacimiento,
            segundoNombre:segundoNombre,
            segundoApellido:segundoApellido,
            contraseña:contraseña,
            imagen:img,
            genero: genero
        };
    
    
        console.log(informacion);
        $.ajax({
            url: "PersonalCalificado",
            type: "POST",
            dataType: "json",
            data: JSON.stringify(informacion), 
            contentType: "JSON application/json charset=utf-8",
            beforeSend: function () {
            },
            success: function (result, textStatus, request) {
                if (result.tipo === "ok") {
                    console.log(result);
                    setCookie("tipoUsuario", 2, 0.5);
                    $(location).attr('href', 'admin_perca.html');
                } else {
                    console.log(result);
                    toastr.warning(result.mensaje)
                }
            },
            complete: function (result) {
            },
            error: function (result) {
            }
        });

    }   
}