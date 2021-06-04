var identificaciones;

var TipoDocumento;
var TIoCC;
var PrimerNombre;
var PrimerApellido;
var SegundoNombre;
var Genero;
var CodigoInstitucional;
var SegundoApellido;
var correo;
var FechaNacimiento;
var contra;
var conficontra;

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

    informacion = {
        documento: TIoCC,
        tipoDocumento: parseInt(TipoDocumento, 10),
        primerNombre: PrimerNombre,
        segundoNombre: SegundoNombre,
        primerApellido: PrimerApellido,
        segundoApellido: SegundoApellido,
        fechaDeNacimiento: FechaNacimiento,
        contraseña: contra,
        genero: Genero,
        grado: CodigoInstitucional,
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

function validar_Uno() {
    TipoDocumento = $("#TipoDocumento").val();
    TIoCC = $("#TIoCC").val();
    PrimerNombre = $("#PrimerNombre").val();
    PrimerApellido = $("#PrimerApellido").val();
    Genero = $("#Genero").val();
    SegundoApellido = $("#SegundoApellido").val();
    FechaNacimiento = $("#FechaNacimiento").val();
    SegundoNombre = $("#SegundoNombre").val();

    if (TipoDocumento == "Tipo" || TIoCC == "" || PrimerNombre == "" || PrimerApellido == "" || Genero == "Genero" || SegundoApellido == "" || FechaNacimiento == "") {
        if (TipoDocumento == "Tipo") {
            document.getElementsByClassName("item form-floating")[0].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor seleccione que tipo de documento tiene')
        }
        if (TIoCC == "") {
            document.getElementsByClassName("item form-floating")[1].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese su número de documento')
            BorrarTexto();
        }
        if (PrimerNombre == "") {
            document.getElementsByClassName("item form-floating")[2].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese su primer nombre')
            BorrarTexto();
        }
        if (PrimerApellido == "") {
            document.getElementsByClassName("item form-floating")[3].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese su primer apellido')
            BorrarTexto();
        }
        if (FechaNacimiento == "") {
            document.getElementsByClassName("item form-floating")[4].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor seleccione su fecha de nacimiento')
        }
        if (SegundoApellido == "") {
            document.getElementsByClassName("item form-floating")[6].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese su segundo apellido')
            BorrarTexto();
        }
        if (Genero == "Genero") {
            document.getElementsByClassName("item form-floating")[7].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor seleccione su genero')
        }
    } else {
        Siguiente();
    }
}


$("#dv3").click(function () {
    document.getElementsByClassName("item form-floating")[4].removeAttribute('data-error')
});
$("#dv11").click(function () {
    document.getElementsByClassName("item form-floating")[7].removeAttribute('data-error')
});
$("#dv12").click(function () {
    document.getElementsByClassName("item form-floating")[0].removeAttribute('data-error')
});

$("#Button2").click(function () {
    validar_Uno();
});

function Siguiente() {
    let txt = `<div id="Datos">
    <section id="table-datos">
    <div class="item form-floating mb-4 especial">
        <input type="email" class="form-control Texto4" id="correo" placeholder=" "
        name="Correo" required>
        <label for="contraseña" class="label">Correo</label>
    </div>
    <div class="box1Table">
        <div class="box1--section1">
            <div class="item form-floating mb-4">
	            <input type="password" class="form-control Texto" id="contra" placeholder=" "
	            name="Contraseña" required>
	            <label for="contraseña" class="label">Contraseña</label>
            </div>
        </div>
        <div class="box1--section2">
            <div class="item form-floating mb-4">
	            <input type="password" class="form-control Texto" id="conficontra" placeholder=" "
	            name="ConfirmarContraseña" required>
	            <label for="contraseña" class="label">Confirmar contraseña</label>
            </div>
        </div>
    </div>
    <p class="parametro">Usa 8 o más caracteres con una combinación de letras, números y símbolos</p>
    <div class="item form-floating mb-4 especial">
        <input type="text" class="form-control Texto4" id="CodigoInstitucional" placeholder=" "
        name="Correo" required>
        <label for="contraseña" class="label">Código institucional</label>
    </div>
    <p class="parametro">Código de 6 caracteres compuesto por letras y números</p>
</section>

<div id="boton">
    <input type="submit" id="Button3" class="buttonRegistrarme" value="Registrarme" name="CrearUsuario" onclick="validar_Dos()">
</div>
</div>
</div>`
    $("#Datos").replaceWith(txt);
}

function validar_Dos() {
    CodigoInstitucional = $("#CodigoInstitucional").val();
    correo = $("#correo").val();
    contra = $("#contra").val();
    conficontra = $("#conficontra").val();
    let expReg = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
    let esValido = expReg.test(correo);

    if (CodigoInstitucional == "" || correo == "" || contra == "" || conficontra == "" || contra.length < 8 || conficontra < 8 ) {
        if (correo == "") {
            document.getElementsByClassName("item form-floating")[0].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese un correo')
            BorrarTexto();
        }
        if (contra == "") {
            document.getElementsByClassName("item form-floating")[1].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese una contraseña')
            BorrarTexto();
        }
        if (conficontra == "") {
            document.getElementsByClassName("item form-floating")[2].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese la confirmación de la contraseña')
            BorrarTexto();
        }
        if (CodigoInstitucional == "") {
            document.getElementsByClassName("item form-floating")[3].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese un código institucional')
        }
        if(contra.length < 8 || conficontra < 8){
            toastr.warning('La contraseña debe tener minimo 8 caracteres')
        }
    } else if (!esValido) {
        toastr.error('El correo ingresado no es valido')
    } else if (contra != conficontra) {
        toastr.error('Las contraseñas no coinciden')
    } else {
        registrar_estudiante();
    }
}