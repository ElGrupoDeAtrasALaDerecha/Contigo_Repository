
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
