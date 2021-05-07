$("#sigin2").click(function(){
    window.location.href="Registro.html"
})
//Ingreso con tecla enter
$("body").keyup(function (e) {
    var email = $("#correo").val();
    var pass = $("#password_inst").val();
    var doc = $("#documento").val();
    var passw = $("#contraseña").val();
	if (e.keyCode == 13) {
        if (email != "" & pass != "" ) {
            $('#ing_inst').click();
        }
        else if (doc != "" & passw != "" ) {
            $('#ing_est').click();
        }
	}
});

// Login Institucion
var ingresoI = document.getElementById("ing_inst");

/* Se agrega el evento al elemento */
//ingresoI.addEventListener("click", ingresoInstitucion);
$("#ing_inst").click(function(e){
    e.preventDefault()
    ingresoInstitucion();
})
function ingresoInstitucion() {
    var email = $("#correo").val();
    var pass = $("#password_inst").val();
    if(email && pass){    
        var obj = {
            correo: email,
            contraseña: pass
        };
        //console.log(obj);
        loginInstitucion(obj);
    }else{
        toastr.warning('Por favor completa todos los daots')

    }
};
/**
 * Función login
 * @param {*} obj 
 */
function loginInstitucion(obj) {
    console.log("Inicio de verificación de credenciales")
    $.ajax({
        method: 'POST',
        url: 'LoginInstitucion',
        dataType: "json",
        data: JSON.stringify(obj),
        contentType: 'JSON application/json charset=utf-8',
        success: function (response) {
            if (response.tipo === "ok") {
                //console.log(response);
                setCookie("ID_Inst", response.ID, 0.3);
                setCookie("tipoUsuario", 3, 0.5)
                // console.log(response.mensaje)
                $(location).attr('href', 'admin_inst.html');
            }else{
                console.log(response.mensaje);
                toastr.warning('El correo o la contraseña son incorrectos')
            }
        },
        error: function (response) {
            console.log("Error: " + response.mensaje)
            toastr.warning('El correo o la contraseña son incorrectos')
            //console.log(JSON.stringify(response))
        }
    });
}

$("#sigin").click(function(){
    window.location.href="RegistroEstudiante.html"
})
// Login Estudiante
var ingresoE= document.getElementById("ing_est");
$("#ing_est").click(
    function (e){
        e.preventDefault();
        ingresoEstudiante();
    }
);
function ingresoEstudiante() {
  
    var doc = $("#documento").val();
    var pass = $("#contraseña").val();
    if (doc!=="" && pass!=="") {
        var obj = {
            documento: doc,
            contraseña: pass
        };
        console.log(obj);
        loginEstudiante(obj);
    } else{
        toastr.warning('Por favor completa todos los daots')
    }
}
;

/**
 * Función login de estudiante
 * @param {*} obj 
 */
function loginEstudiante(obj) {
    $.ajax({
        method: 'POST',
        url: 'LoginEstudiante',
        data: JSON.stringify(obj),
        dataType: "json",
        success: function (response) {
            console.log(response);
            if (response.tipo === "ok") {
                setCookie("token", response.estudiante.token, 0.3);
                setCookie("documento", response.estudiante.documento, 0.3);
                
                setCookie("tipoUsuario", 1, 0.5);
                $(location).attr('href', 'opciones.html');
            } else {
                alert("Error: " + response.mensaje)
                toastr.warning('El documento de identidad o la contraseña son incorrectos')
            }
        },
        error: function (response) {
            console.log(JSON.stringify(response))
            toastr.warning('El documento de identidad o la contraseña son incorrectos')
        }
    });
}


function ingresarEnter() {
    tecla_enter = event.keyCode;
    if (tecla_enter == 13) {
        return ingresoInstitucion();
    }
}

window.onkeydown = ingresarEnter;
