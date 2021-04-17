// Login Institucion
$("#ing_inst").click(function (e) {
    e.preventDefault();
    var email = $("#correo").val();
    var pass = $("#password_inst").val();
    var obj = {
        correo: email,
        contraseña: pass
    };
    //console.log(obj);
    loginInstitucion(obj);
});
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
            }
        },
        error: function (response) {
            console.log("Error: " + response.mensaje)
            //console.log(JSON.stringify(response))
        }
    });
}


// Login Estudiante
$("#ing_est").click(function (e) {
    e.preventDefault();
    var doc = $("#documento").val();
    var pass = $("#contraseña").val();
    var obj = {
        documento: doc,
        contraseña: pass
    };
    console.log(obj);
    loginEstudiante(obj);
});

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
                alert("Mensaje: " + response.mensaje);
                setCookie("tipoUsuario", 1, 0.5);
                $(location).attr('href', 'opciones.html');
            } else {
                alert("Error: " + response.mensaje)
            }
        },
        error: function (response) {
            console.log(JSON.stringify(response))
        }
    });
}

