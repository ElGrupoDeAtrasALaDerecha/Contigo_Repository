$(document).ready(function () {

});

$("#recuperarContra").click(function (e) {
    pintarModal();
});
var correo;
function pintarModal(){
    let elModal = `
    <div id="modalContra" class="ui tiny modal">
        <div class="header">
            Recuperación de contraseña
        </div>
        <div class="content">
            <form class="ui form">
                <div class="ui field">
                    <p>Ingresa el correo electronico con el cual te registraste. Te enviaremos un enlace para la
                        recuperación
                        de tu contraseña.</p>
                </div>
                <div class="ui field">
                    <label for="correo">Correo electrónico:</label>
                    <input type="email" class="credencial" id="correoRecuperacion" placeholder="Correo" name="correo" required>
                </div>
                <div class="actions">
                    <div class="ui two fields">
                        <div class="ui field">
                            <button id="btnRecuperar" class="ui primary fluid button">Enviar correo</button>
                        </div>
                        <div class="ui field">
                            <button id="btnCancelarRecuperacion" class="ui fluid button">Cancelar</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>`

    $("body").append(elModal);
    $('.ui.modal')
        .modal('show');
    ;
    $("#btnRecuperar").click(function (e) {
        e.preventDefault()
        correo = $("#correoRecuperacion").val();
        recuperarContraseña(correo);
    });
    $("#btnCancelarRecuperacion").click(function (e) {
        e.preventDefault();
        $('.ui.modal').modal('hide');
        $('#modalContra').remove();
    });
}

/**
 * Método que manda una petición para recuperar contraseña
 * @param {string} correo 
 */
function recuperarContraseña(correo) {
    let obj={correo:correo};
    $.ajax({
        method: 'POST',
        url: 'Password',
        data: JSON.stringify(obj),
        dataType: "json",
        beforeSend: function(e){
            let txt=`<div class="ui active inverted dimmer">
            <div class="ui indeterminate text loader">Por favor, espera</div>
          </div>`;
            $("body").append(txt);
        },
        success: function (response) {
            if (response.tipo === "ok") {
                toastr.success(response.mensaje)
                $('.ui.modal').modal('hide');
                $('#modalContra').remove();
            } else {
                alert("Error: " + response.mensaje)
                toastr.warning(response.mensaje)
            }
        },
        error: function (response) {

            toastr.warning(response.mensaje)
        },
        complete: function(e){
            $('.ui.active.inverted.dimmer').remove();
        }
    });
}