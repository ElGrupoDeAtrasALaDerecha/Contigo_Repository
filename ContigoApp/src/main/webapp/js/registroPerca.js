
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
            document.getElementsByClassName("item form-floating mb-4")[8].removeAttribute('data-error')
        },
        error: function (result) {
        }
    });
});

//Registro de PERCA
$("#Button2").click(function () {
    /*e.preventDefault();
    crearPerca();*/
    validar();
})

function crearPerca() {
    documento = $("#documento").val();
    primerNombre = $("#primerNombre").val();
    primerApellido = $("#primerApellido").val();
    correo = $("#correo").val();
    fechaDeNacimiento = $("#fecha").val();
    segundoNombre = $("#segundoNombre").val();
    segundoApellido = $("#segundoApellido").val();
    contraseña = $("#contraseña").val();
    genero = "femenino"


    informacion = {
        documento: documento,
        tipoDocumento: "1",
        primerNombre: primerNombre,
        primerApellido: primerApellido,
        correo: correo,
        fechaDeNacimiento: fechaDeNacimiento,
        segundoNombre: segundoNombre,
        segundoApellido: segundoApellido,
        contraseña: contraseña,
        imagen: img,
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
                toastr.warning(result.mensaje2)
            }
        },
        complete: function (result) {
        },
        error: function (result) {
        }
    });

}

function validar() {
    let doc = $("#documento").val();
    let pNombre = $("#primerNombre").val();
    let pApellido = $("#primerApellido").val();
    let correo = $("#correo").val();
    let fechaNacimiento = $("#fecha").val();
    let sApellido = $("#segundoApellido").val();
    let con = $("#contraseña").val();
    let conficontra = $("#contraseña2").val();
    let gen = "femenino"
    let img = $("#img-uploader").val();
    var expReg = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
    var esValido = expReg.test(correo);
    

    if (doc == "" || pNombre == "" || pApellido == "" || correo == "" || fechaNacimiento == "" || sApellido == "" || con == "" || gen == "" || conficontra == "" || img == "") {

        if (pNombre == "") {
            document.getElementsByClassName("item form-floating mb-4")[0].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning("Porfavor ingrese su nombre")
        }
        if (pApellido == "") {
            document.getElementsByClassName("item form-floating mb-4")[1].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Porfavor ingrese su primer apellido')
        }
        if (doc == "") {
            document.getElementsByClassName("item form-floating mb-4")[2].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning("Falta ingresar el documento")
            toastr.warning('Por favor ingrese su número de documento')
        }
        if (fechaNacimiento == "") {
            document.getElementsByClassName("item form-floating mb-4")[3].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Porfavor ingrese una fecha de nacimiento')
        }
        if (con == "") {
            document.getElementsByClassName("item form-floating mb-4")[4].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Debe ingresar una contraseña')
        }
        if (sApellido == "") {
            document.getElementsByClassName("item form-floating mb-4")[6].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Debe ingresar su segundo apellido')
        }
        if (correo == "") {
            document.getElementsByClassName("item form-floating mb-4")[7].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Debe ingresar un correo')
        }
        if (img == "") {
            document.getElementsByClassName("item form-floating mb-4")[8].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Porfavor suba una imagen de perfil')
        }
        if (conficontra == "") {
            document.getElementsByClassName("item form-floating mb-4")[9].setAttribute('data-error', 'Campo Obligatorio')
            toastr.warning('Falta la confirmación de la contraseña')
        }
        if (gen == "") {
            toastr.warning('Debe seleccionar un genero')
        }
        if (con.length < 8 || conficontra.length < 8) {
            toastr.warning('la contraseña debe ser de minimo 8 caracteres')
        }
    } else if (con !== conficontra) {
        toastr.error('Las contraseñas no coinciden')
    } else if (!esValido ) {
        toastr.error('Correo no valido')
    } else {
        crearPerca();
    }
}


$("#dv0").click(function () {
    document.getElementsByClassName("item form-floating mb-4")[0].removeAttribute('data-error')
});
$("#dv1").click(function () {
    document.getElementsByClassName("item form-floating mb-4")[1].removeAttribute('data-error')
});
$("#dv2").click(function () {
    document.getElementsByClassName("item form-floating mb-4")[2].removeAttribute('data-error')
});
$("#dv3").click(function () {
    document.getElementsByClassName("item form-floating mb-4")[3].removeAttribute('data-error')
});
$("#dv4").click(function () {
    document.getElementsByClassName("item form-floating mb-4")[4].removeAttribute('data-error')
});
$("#dv6").click(function () {
    document.getElementsByClassName("item form-floating mb-4")[6].removeAttribute('data-error')
});
$("#dv7").click(function () {
    document.getElementsByClassName("item form-floating mb-4")[7].removeAttribute('data-error')
});
$("#dv9").click(function () {
    document.getElementsByClassName("item form-floating mb-4")[9].removeAttribute('data-error')
});