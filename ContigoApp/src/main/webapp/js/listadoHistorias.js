var listaHistorias;

$(document).ready(function(){
    cargarListaDeHistorias();
});

function cargarListaDeHistorias(){
    $.ajax({
        url: "Historia",
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (response) {
            if (response.tipo === "ok") {
                console.log(response);
                listaHistorias=response.historias;
                pintarHistorias();
            }
        }, complete: function (result) {

        }, error: function (result) {
            alert("Error interno")
        }
    });
}

function pintarHistorias(){
    for (let i = 0; i < listaHistorias.length; i++) {
        let historia =listaHistorias[i];
        let txt=`<div id="${historia.id}" class="item">
                    <div class="bottom">
                        <a href="decisiones.html" class="textTitle">${historia.titulo}</a>
                        <p class="text">${historia.descripcion}</p>
                        </div>
                </div>
        `
        $("#contenedorHistorias").append(txt);
        $("#"+historia.id).css('background-image', 'url(' + historia.urlImagen + ')');
    }
}