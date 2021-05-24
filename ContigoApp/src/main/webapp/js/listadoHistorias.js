var listaHistorias;
var usu;

$(document).ready(function () {
    cargarListaDeHistorias();
    usu = parseInt(getCookie("tipoUsuario"));
    if (usu !== 2) {
        document.getElementById('btnCrear').style.display = 'none';

    }
});

function cargarListaDeHistorias() {
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
                listaHistorias = response.historias;
                pintarHistorias();
                if (usu === 3) {
                    let btnasignar = $(".OpcBotonesEditar");
                    $(btnasignar).text('Asignar');
                }
            }

        }, complete: function (result) {
            //ocultarBotones();
        }, error: function (result) {
            alert("Error interno")
        }
    });

}

function pintarHistorias() {
    for (let i = 0; i < listaHistorias.length; i++) {
        let historia = listaHistorias[i];
        let txt = `
                    <div id="${historia.id}" class="item">
                    <div id="opacityImg" class="opacityImg">
                        <div id="OpcBotones" class="OpcBotones">
                            <a id="OpcBotonesVer" class="OpcBotonesVer" onClick="verHistoria()">
                            <ion-icon name="eye"></ion-icon> <span class="Ver">Ver</span></a>
                            <a id="editar" class="OpcBotonesEditar" onclick="redirigirEdit()">
                            <ion-icon name="create"></ion-icon> Editar</a>
                        </div>
                        <div id="bottom" class="bottom" >
                            <a  class="textTitle">${historia.titulo}
                                <p class="text">${historia.descripcion}</p>
                            </a>
                        </div>
                        <div>
                    </div>
        `

        $("#contenedorHistorias").append(txt);
        $("#" + historia.id).css('background-image', 'url(' + historia.urlImagen + ')');
        /*$("#" + historia.id).click(function () {
            setCookie("idHistoria", historia.id, 0.1);
             window.location.assign("decisiones.html");
        })*/
        ocultarBotones();
    }

}

function ocultarBotones() {
    usu = parseInt(getCookie("tipoUsuario"));
    if (usu === 1) {
        let OpcBotones = document.getElementsByClassName('OpcBotones');
        let opacityImg = document.getElementsByClassName('opacityImg');
        let bottom = document.getElementsByClassName('bottom');
        for (let index = 0; index < OpcBotones.length; index++) {
            OpcBotones[index].style.display = "none";
            opacityImg[index].style.alignItems = 'Flex-end'
            bottom[index].style.display = 'block';


        }
        console.log("Holi")
    }
}




function verHistoria() {
    usu = parseInt(getCookie("tipoUsuario"));
    if (usu === 2) {
        for (let i = 0; i < listaHistorias.length; i++) {
            let historia = listaHistorias[i];
            $("#" + historia.id).click(function () {
                setCookie("idHistoria", historia.id, 0.1);
                window.location.assign("decisiones.html");
            })

        }
    }

    if (usu === 3) {

        for (let i = 0; i < listaHistorias.length; i++) {
            let historia = listaHistorias[i];
            $("#" + historia.id).click(function () {
                setCookie("idHistoria", historia.id, 0.1);
                window.location.assign("decisiones.html");
            })

        }

    }

}


function redirigirEdit() {
    usu = parseInt(getCookie("tipoUsuario"));
    if (usu === 2) {
        for (let index = 0; index < listaHistorias.length; index++) {
            let historia = listaHistorias[index];
            $("#" + historia.id).click(function () {
                setCookie("idHistoria", historia.id, 0.1)
                $(location).attr('href', 'situaciones.html?id=' + getCookie("idHistoria"));
            })

        }
    }

    if (usu === 3) {
        console.log('wenas')
    }


}