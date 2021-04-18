
$(document).ready(function () {
    let txt = `<button id="btnAyuda" class="red inverted circular massive ui icon button"  data-content="¿Necesitas ayuda?">
                <i class="icon heartbeat"></i>
            </button>`
    $("body").append(txt);
    $('#btnAyuda').popup();
    $("#btnAyuda").css({
        "position": "fixed",
        "z-index": "10000",
        "top": "90%",
        "left": "95%"
    });
    if (getCookie("tipoUsuario") === "1") {
        $("#btnAyuda").click(function () {
            estudiante = getCookie("token");
            var obj = {
                estudiante: estudiante
            }
            enviarInformacion(obj)
            window.location.assign("chat.html");
        })
    } else if (getCookie("tipoUsuario") === "2") {
        $("#btnAyuda").prop("data-content", "¡Mira las conversaciones privadas!");
        $("#btnAyuda").click(function () {
            window.location.assign("admin_perca.html");
        })
    } else {
        $("#btnAyuda").remove();
    }
});



function enviarInformacion(obj) {
    console.log(obj)
    $.ajax({
        method: 'POST',
        url: 'Estadisticas_btn_Panico',
        data: JSON.stringify(obj),
        dataType: "json",
        success: function (response) {
            if (response.tipo === "ok") {
                console.log(response);
            } else {
                console.log(response.mensaje);
            }
        },
        error: function (response) {
            console.log(JSON.stringify(response))
        }
    });
}
