var departamentos;
var municipios;
var instituciones;
var ca = null;
var sec = null;
var idpago = 1;

var departamento;
var munici;
var nombre;
var sector;
var dire;
var ba;
var Calendario;
var telefono;
var correo;
var web;
var contra;
var conficontra;


window.onload = function depas() {
    $.ajax({
        url: "Departamento",
        type: "GET",
        dataType: "json",
        success: function (result, textStatus, request) {
            if (result != "error") {
                console.log(result);
                departamentos = result.Departamentos;
                for (var i = 0; i < departamentos.length; i++) {
                    let departa = '<option value ="' + departamentos[i].id + '">' + departamentos[i].nombre +
                        '</option>';
                    $("#departamento").append(departa);
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
    munici();
}

function registrar_institucion() {
   
    if (Calendario == 0) {
        ca = false;
    } else {
        ca = true;
    }

    if (sector == 0) {
        sec = false;
    } else {
        sec = true;
    }

    informacion = {
        idMunicipio: parseInt(munici, 10),
        nombre: nombre,
        tipoInstitucion: sec,
        direccion: dire,
        barrio: ba,
        telefono: telefono,
        correo: correo,
        pagina: web,
        contraseña: contra,
        calendario: ca,
        METODO_PAGO_id: idpago
    };
    console.log(informacion)
    
    $.ajax({
        url: "Institucion",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(informacion),
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            if (result.tipo != "error") {
                console.log(result);
                toastr.success('Institcución creada con exito')
                $(location).attr('href', 'login_est.html');
            } else {
                if (result.mensaje === "Ya existe una institucion con este nombre") {
                    console.log(result);
                    toastr.error('Institución ya registrada')
                } else {
                    console.log(result);
                    toastr.error('Error al registrar la institución')
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


function munici() {
    $.ajax({
        url: "Municipio",
        type: "GET",
        dataType: "json",
        success: function (result, textStatus, request) {
            if (result != "error") {
                municipios = result.Municipios;
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

var departamento;

$("#departamento").click(function () {
    departamento = $("#departamento").val();
    consultarMunicipiosPorDepartamento(departamento);
});

function consultarMunicipiosPorDepartamento(departamento_ID) {
    let municipiosAPintar = new Array();
    for (let i = 0; i < municipios.length; i++) {
        if (municipios[i].departamentoId === parseInt(departamento_ID, 10)) {
            municipiosAPintar.push(municipios[i]);
        }
    }
    llenarMunicipios(municipiosAPintar);
}

function llenarMunicipios(municipiosAPintar) {
    $('#municipio').empty();
    let m = '<option value="">Municipio</option>';
    let txt = '';
    for (let i = 0; i < municipiosAPintar.length; i++) {
        if (i == 0) {
            $('#municipio').append(m);
        }
        txt = '<option value ="' + municipiosAPintar[i].id + '">' + municipiosAPintar[i].nombre +
            '</option>';
        $('#municipio').append(txt);
    };

}

function BorrarTexto() {
    document.querySelectorAll('.item[data-error] .Texto4').forEach(inpEl => {
        inpEl.addEventListener('input', () => inpEl.parentElement.removeAttribute('data-error'));
    })

    document.querySelectorAll('.item[data-error] .Texto').forEach(inpEl => {
        inpEl.addEventListener('input', () => inpEl.parentElement.removeAttribute('data-error'));
    })
}

function validar_uno() {
    departamento = document.getElementById("departamento").value;
    munici = document.getElementById("municipio").value;
    nombre = document.getElementById("nombre").value;
    sector = document.getElementById("sector").value;
    dire = document.getElementById("direccion").value;
    ba = document.getElementById("barrio").value;
    Calendario = document.getElementById("Calendario").value;

    if (departamento == "" || munici == "" || nombre == "" || sector == "Sector" || dire == "" || ba == "" || Calendario == "") {

        if (nombre == "") {
            document.getElementsByClassName("item form-floating mb-4")[0].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese el nombre de la instiución')
            BorrarTexto();
        }

        if (departamento == "") {
            document.getElementsByClassName("item form-floating mb-4")[1].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor seleccione un departamento')
        }

        if (sector == "Sector") {
            document.getElementsByClassName("item form-floating mb-4")[2].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor seleccione un sector')
        }
        if (dire == "") {
            document.getElementsByClassName("item form-floating mb-4")[3].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese una dirección')
            BorrarTexto();
        }
        if (munici == "") {
            document.getElementsByClassName("item form-floating mb-4")[4].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor seleccione un municipio')
        }
        if (Calendario == "Calendario") {
            document.getElementsByClassName("item form-floating mb-4")[5].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor seleccione el calendario de la instiución')
        }
        if (ba == "") {
            document.getElementsByClassName("item form-floating mb-4")[6].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese un barrio')
            BorrarTexto();
        }
    } else {
        Siguiente();
    }
}

function Siguiente() {
    let txt = `<div id="Datos">
    <section id="table-datos">
    <div class="item form-floating mb-4 especial">
	    <input type="text" class="form-control Texto4" id="web" placeholder=" "
	    name="PaginaWeb" required>
	    <label for="contraseña" class="label">Página Web:</label>
    </div>
    <div class="box1Table">
        
        <div class="box1--section1">
            <div class="item form-floating mb-4">
                <input type="number" class="form-control Texto" id="telefono" placeholder=" "
                name="Telefono" required>
                <label for="contraseña" class="label">Numero de contacto</label>
            </div>
            <div class="item form-floating mb-4">
	            <input type="password" class="form-control Texto" id="contra" placeholder=" "
	            name="Contraseña" required>
	            <label for="contraseña" class="label">Contraseña</label>
            </div>
        </div>
        <div class="box1--section2">
            <div class="item form-floating mb-4">
                <input type="email" class="form-control Texto" id="correo" placeholder=" "
                name="Correo" required>
                <label for="contraseña" class="label">Correo</label>
            </div>
            <div class="item form-floating mb-4">
	            <input type="password" class="form-control Texto" id="conficontra" placeholder=" "
	            name="ConfirmarContraseña" required>
	            <label for="contraseña" class="label">Confirmar contraseña</label>
            </div>
        </div>
    </div>
</section>
<p class="parametro">Usa 8 o más caracteres con una combinación de letras, números y símbolos</p>
<div id="boton">
    <input type="submit" id="Button3" class="buttonRegistrarme" value="Registrarme" name="CrearUsuario" onclick="validar_dos()">
</div>
</div>
</div>`
    $("#Datos").replaceWith(txt);
}

$("#Button2").click(function () {
    validar_uno();
});

$("#departamento").click(function () {
    document.getElementsByClassName("item form-floating mb-4")[1].removeAttribute('data-error')
});
$("#sector").click(function () {
    document.getElementsByClassName("item form-floating mb-4")[2].removeAttribute('data-error')
});
$("#municipio").click(function () {
    document.getElementsByClassName("item form-floating mb-4")[4].removeAttribute('data-error')
});
$("#Calendario").click(function () {
    document.getElementsByClassName("item form-floating mb-4")[5].removeAttribute('data-error')
});

function validar_dos() {
    telefono = document.getElementById("telefono").value;
    correo = document.getElementById("correo").value;
    web = document.getElementById("web").value;
    contra = document.getElementById("contra").value;
    conficontra = document.getElementById("conficontra").value;
    var expReg = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
    var esValido = expReg.test(correo);


    if (telefono == "" || correo == "" || contra == "Sector" || conficontra == "") {

        if (telefono == "") {
            document.getElementsByClassName("item form-floating mb-4")[1].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese un número de contacto')
            BorrarTexto();
        }
        if (contra == "" ) {
            document.getElementsByClassName("item form-floating mb-4")[2].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese una contraseña')
            BorrarTexto();
        }
        if (correo == "") {
            document.getElementsByClassName("item form-floating mb-4")[3].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese un correo')
            BorrarTexto();
        }
        if (conficontra == "") {
            document.getElementsByClassName("item form-floating mb-4")[4].setAttribute("data-error", "Campo obligatorio");
            toastr.warning('Por favor ingrese la confirmación de la contraseña')
            BorrarTexto();
        }
        if (conficontra.length < 8 || contra.length < 8) {
            toastr.warning('La contraseña debe tener minimo 8 caracteres')
        }
    } else if (contra !== conficontra) {
        toastr.error('Las contraseñas no coinciden')
    } else if (!esValido) {
        toastr.error('El correo no valido')
    } else {
        registrar_institucion();
    }
}

