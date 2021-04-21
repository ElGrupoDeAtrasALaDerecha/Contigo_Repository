var identificaciones;

//porfi funciona quiero mimir :(

window.onload = function tipos_id() {
    $.ajax({
        url: "TipoDocumento",
        type: "GET",
        dataType: "json",
        success: function (result, textStatus, request) {
            if (result != "error") {
                console.log(result);
                identificaciones = result.Identificaciones;
                for (var i = 0; i < identificaciones.length; i++) {
                    let departa = '<option value ="' + identificaciones[i].id + '">' + identificaciones[i].tipo +
                        '</option>';
                    $("#TipoDocumento").append(departa);
                }
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

function registrar_estudiante() {
    tidocu = $("#TipoDocumento").val();
    numdocu = $("#TIoCC").val();
    pnom = $("#PrimerNombre").val();
    pape = $("#PrimerApellido").val();
    gen = $("#Genero").val();
    fena = $("#FechaNacimiento").val();
    codins = $("#CodigoInstitucional").val();
    snom = $("#SegundoNombre").val();
    sape = $("#SegundoApellido").val();
    con = $("#contra").val();
    correo= $("#correo").val();
    if (tidocu == "") {
        toastr.warning('Por favor escoja un tipo de documento')
    }

    informacion = {
        documento: numdocu,
        tipoDocumento: parseInt(tidocu, 10),
        primerNombre: pnom,
        segundoNombre: snom,
        primerApellido: pape,
        segundoApellido: sape,
        fechaDeNacimiento: fena,
        contraseña: con,
        genero: gen,
        grado: codins,
        correo: correo
    };

    $.ajax({
        url: "Estudiante",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(informacion),
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {

        },
        success: function (result, textStatus, request) {
            if (result.tipo != "error") {
                console.log(result);
                toastr.success('Estudiante creado con exito')
                $(location).attr('href', 'ingresar.html');

            } else {
                if (result.mensaje === "Error el estudiante ya esta registrado") {
                    console.log(result);
                    toastr.error('Error ya existe un estudiante registrado con este documento')    
                } else {
                    console.log(result);
                    toastr.error('Código institucional erroneo')
                }
            }

        },
        complete: function (result) {


        },
        error: function (result) {
            console.log(result);
        }

    });


}


function BorrarTexto() {
    document.querySelectorAll('.Espacios[data-error] .Texto').forEach(inpEl => {
        inpEl.addEventListener('input', () => inpEl.parentElement.removeAttribute('data-error'));
    })
}

function Ingresar() {
    TipoDocumento = document.getElementById("TipoDocumento").value;
    TIoCC = document.getElementById("TIoCC").value;
    PrimerNombre = document.getElementById("PrimerNombre").value;
    PrimerApellido = document.getElementById("PrimerApellido").value;
    Genero = document.getElementById("Genero").value;
    CodigoInstitucional = document.getElementById("CodigoInstitucional").value;
    SegundoApellido = document.getElementById("SegundoApellido").value;
    correo = document.getElementById("correo").value;
    FechaNacimiento = document.getElementById("FechaNacimiento").value;
    contra = document.getElementById("contra").value;
    conficontra = document.getElementById("conficontra").value;
    var expReg = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
    var esValido = expReg.test(correo);

    if (TipoDocumento == "Tipo de documento" || TIoCC == "" || PrimerNombre == "" || PrimerApellido == "" || Genero == "Genero" || CodigoInstitucional == "" || SegundoApellido == "" || correo == "" || FechaNacimiento == "" || contra == "" || conficontra == "") {
        if (TipoDocumento == "") {
            document.getElementsByClassName("Espacios tip")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (TIoCC == "") {
            document.getElementsByClassName("Espacios num")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (PrimerNombre == "") {
            document.getElementsByClassName("Espacios pri")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (PrimerApellido == "") {
            document.getElementsByClassName("Espacios ape")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (Genero == "Genero") {
            document.getElementsByClassName("Espacios gen")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (CodigoInstitucional == "") {
            document.getElementsByClassName("Espacios cod")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }

        if (FechaNacimiento == "") {
            document.getElementsByClassName("Espacios fec")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (SegundoApellido == "") {
            document.getElementsByClassName("Espacios seg")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (correo == "") {
            document.getElementsByClassName("Espacios cor")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }

        if (contra == "") {
            document.getElementsByClassName("Espacios con")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        } else if (contra.length <= 8) {
            document.getElementsByClassName("Espacios con")[0].setAttribute("data-error", "La contraseña debe tener mas de 8 digitos");
            BorrarTexto();
        }
        if (conficontra == "") {
            document.getElementsByClassName("Espacios confi")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        } else if (conficontra.length <= 8) {
            document.getElementsByClassName("Espacios confi")[0].setAttribute("data-error", "La contraseña debe tener mas de 8 digitos");
            BorrarTexto();
        }


    } else if (contra != conficontra) {
        toastr.warning('Las contraseñas no coinciden')
    }else if(esValido != true){
        toastr.error('Correo no valido')
    } else {
        registrar_estudiante();

    }
}

