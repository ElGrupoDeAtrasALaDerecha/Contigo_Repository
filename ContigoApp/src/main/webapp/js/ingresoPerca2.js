var ingresoPersonal = document.getElementById("ing_perca");

/* Se agrega el evento al elemento */
ingresoPersonal.addEventListener("click", ingresoPersonalCalificado);

function ingresoPersonalCalificado() {
  var email = $('#correo').val()
  var pass = $('#contraseña').val()
  if (validarEmail(email)) {
    var obj = {
      correo: email,
      contraseña: pass
    }
    console.log(obj)
    loginPersonalCalificado(obj)
  } else {
    alert('Formato de correo inválido')
  }
}


/**
 * Función login
 * @param {*} obj
 */
function loginPersonalCalificado(obj) {
  $.ajax({
    method: 'POST',
    url: 'LoginPersonalCalificado',
    data: JSON.stringify(obj),
    dataType: "json",
    success: function (response) {
      console.log(response);
      if (response.tipo === "ok") {
        setCookie("token", response.personal.token, 0.3);
        setCookie("tipoUsuario", 2, 0.5);
        toastr.success("Mensaje: " + response.mensaje);

        $(location).attr('href', 'admin_perca.html');
      }
      else {
        toastr.error("Error: " + response.mensaje)
      }
    },
    error: function (response) {
      console.log(JSON.stringify(response))
    }
  });
}

/**
 * Función tecla Enter
 * 
 */
function ingresarEnter() {
  tecla_enter = event.keyCode;

  if (tecla_enter == 13) {
      return ingresoPersonalCalificado();
  }
}

window.onkeydown = ingresarEnter;