var departamentos;
var municipios;
var instituciones;
var ca = null;
var sec = null;
var idpago = 1;



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
    municipio = $("#municipio").val();
    nom = $("#nombre").val();
    sector = $("#sector").val();
    direccion = $("#direccion").val();
    barrio = $("#barrio").val();
    tel = $("#telefono").val();
    correo = $("#correo").val();
    pagw = $("#web").val();
    contra = $("#contra").val();
    calen = $("#Calendario").val();

    if (calen == 0) {
        ca = false;
    } else {
        ca = true;
    }

    if (sector == 0) {
        sec = false;
    } else {
        sec = true;
    }

    if (municipio == "") {
        toastr.warning('Por favor escoja un municipio')
    }


    informacion = {
        idMunicipio: parseInt(municipio, 10),
        nombre: nom,
        tipoInstitucion: sec,
        direccion: direccion,
        barrio: barrio,
        telefono: tel,
        correo: correo,
        pagina: pagw,
        contraseña: contra,
        calendario: ca,
        METODO_PAGO_id: idpago
    };

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
                $(location).attr('href', 'ingresar.html');
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
        if(i==0){
            $('#municipio').append(m);
        }
        txt = '<option value ="' + municipiosAPintar[i].id + '">' + municipiosAPintar[i].nombre +
            '</option>';
        $('#municipio').append(txt);
    };

}
function BorrarTexto() {
    document.querySelectorAll('.Espacios[data-error] .Texto').forEach(inpEl => {
        inpEl.addEventListener('input', () => inpEl.parentElement.removeAttribute('data-error'));
    })
}

function Ingresar() {
    departamento = document.getElementById("departamento").value;
    municipio = document.getElementById("municipio").value;
    nombre = document.getElementById("nombre").value;
    sector = document.getElementById("sector").value;
    direccion = document.getElementById("direccion").value;
    barrio = document.getElementById("barrio").value;
    telefono = document.getElementById("telefono").value;
    correo = document.getElementById("correo").value;
    web = document.getElementById("web").value;
    Calendario = document.getElementById("Calendario").value;
    contra = document.getElementById("contra").value;
    conficontra = document.getElementById("conficontra").value;
    var expReg = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
    var esValido = expReg.test(correo);


    

    if (departamento == "Departamentos" || municipio == "Municipio" || nombre == "" || sector == "Sector" || direccion == "" || barrio == "" || telefono == "" || correo == "" || Calendario == "" || contra == "" || conficontra == "") {
        if (departamento == "") {
            document.getElementsByClassName("Espacios dep")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (nombre == "") {
            document.getElementsByClassName("Espacios nom")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (sector == "Sector") {
            document.getElementsByClassName("Espacios sec")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (direccion == "") {
            document.getElementsByClassName("Espacios dir")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (municipio == "") {
            document.getElementsByClassName("Espacios mun")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (barrio == "") {
            document.getElementsByClassName("Espacios bar")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (telefono == "") {
            document.getElementsByClassName("Espacios Tel")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (correo == "") {
            document.getElementsByClassName("Espacios cor")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (Calendario == "Calendario") {
            document.getElementsByClassName("Espacios cal")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }
        if (contra == "") {
            document.getElementsByClassName("Espacios con")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }else if(contra.length<=8){
            document.getElementsByClassName("Espacios con")[0].setAttribute("data-error", "La contraseña debe tener mas de 8 digitos");
            BorrarTexto();
        }
        if (conficontra == "") {
            document.getElementsByClassName("Espacios confi")[0].setAttribute("data-error", "Campo obligatorio");
            BorrarTexto();
        }else if(conficontra.length<=8){
            document.getElementsByClassName("Espacios confi")[0].setAttribute("data-error", "La contraseña debe tener mas de 8 digitos");
            BorrarTexto();
        }
    } else if (contra != conficontra) {
        toastr.warning('Las contraseñas no coinciden')
    } else if (esValido != true) {
       toastr.error('Correo no valido')
    } else {
        registrar_institucion();
        
    }
}

