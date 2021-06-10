const params = new URLSearchParams(window.location.search)
var codigo;
$(document).ready(function () {
    codigo = params.get("codigo")
    if (codigo === null) {
        window.location.assign("404.html");
    } else {
        $.ajax({
            method: 'GET',
            url: 'Password?codigo=' + codigo,
            dataType: "json",
            success: function (response) {
                if (response.tipo === "ok" && response.allowed) {
                    pintarPagina();
                }
            },
            error: function (response) {
                window.location.assign("404.html");
            }
        });
    }
});
/**
 * Función que pinta la página completa para cambiar la contraseña
 */
function pintarPagina() {
    let txt = `<div class="ui grid">
                    <div class="ui row">

                    </div>
                    <div class="ui row">
                        <div class="ui five wide column">

                        </div>
                        <div class="ui seven wide column">
                            <div class="ui form">
                                <h3 class="ui dividing header">Recuperación de contraseña: </h3>
                                <div class="ui field">
                                    <label for="nueva">Nueva contraseña: </label>
                                    <input id="contra" type="password" name="nueva" placeholder="Nueva contraseña">
                                </div>
                                <div class="ui field">
                                    <label for="repetir">Confirmación de contraseña: </label>
                                    <input id="confirmacion" type="password" name="repetir" placeholder="Confirmación de contraseña">
                                </div>

                                <button id="btnCambiar" class="ui primary button">Cambiar contraseña</button>
                            </div>
                        </div>
                    </div>
                    <div class="ui row">

                    </div>
                </div>`
    $("body").append(txt);
    /**$("input").keydown(function (e) {
        $(".ui.segment").remove();
        $("#confirmacion").removeClass("error");
        let contraseña = $("#contra").val();
        let repetirContraseña = $("#confirmacion").val();
        if (repetirContraseña !== "") {
            if (contraseña !== repetirContraseña) {
                $("#confirmacion").addClass("error");
                $(".ui.form").append(`
                    <div class="ui red segment">
                    <p>Las contraseñas no coinciden</p>
                </div>
            `)
            }else{
                $(".ui.segment").remove();
                $("#confirmacion").removeClass("error");
            }
        }
    });*/
    $("#btnCambiar").click(function () {
        let contraseña = $("#contra").val();
        let repetirContraseña = $("#confirmacion").val();
        if (contraseña !== repetirContraseña) {
            toastr.warning("Las contraseñas no coinciden");
        } else {

            var obj = {
                contraseña: contraseña,
                repetirContraseña: repetirContraseña
            }
            cambiarContraseña(obj)
        }
    });
}


/**
 * Función que permite cambiar una contraseña 
 * @param {*} obj que contiene la contraseña y su confirmación
 */
function cambiarContraseña(obj) {
    $.ajax({
        method: 'PUT',
        url: 'Password?codigo=' + codigo,
        data: JSON.stringify(obj),
        dataType: "json",
        success: function (response) {
            toastr.success(response.mensaje);
            setTimeout(function () { window.location.assign("index.html"); }, 3000);
        },
        error: function (response) {
            window.location.assign("404.html");
        }
    });
}