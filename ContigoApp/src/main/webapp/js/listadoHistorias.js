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
                pintarHistorias(usu);
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

function pintarHistorias(tipo) {

    for (let i = 0; i < listaHistorias.length; i++) {

        let historia = listaHistorias[i];
        let txt = `
                    <div id="${historia.id}" class="item">
                    <div id="opacityImg" class="opacityImg">
                        <div id="OpcBotones" class="OpcBotones">
                            <a id="OpcBotonesVer${historia.id}" class="OpcBotonesVer">
                            <ion-icon name="eye"></ion-icon> <span class="Ver">Ver</span></a>
                            <a id="editar${historia.id}" class="OpcBotonesEditar">
                            <ion-icon name="create"></ion-icon> Editar</a>
                        </div>
                        <div id="bottom" class="bottom" >
                            <a  href="decisiones.html?idHistoria=${historia.id}" class="textTitle">${historia.titulo}
                                <p class="text">${historia.descripcion}</p>
                            </a>
                        </div>
                        <div>
                    </div>
        `

        $("#contenedorHistorias").append(txt);
        $("#" + historia.id).css('background-image', 'url(' + historia.urlImagen + ')');
        if (tipo == 1) {
            $("#" + historia.id).click(function () {
                setCookie("idHistoria", historia.id, 0.1);
                window.location.assign("decisiones.html");
            })
        } else if (tipo === 2) {
            $("#editar" + historia.id).click(function () {
                setCookie("idHistoria", historia.id, 0.1);
                window.location.assign("decisiones.html?idHistoria=" + historia.id);
            });
            $("#OpcBotonesVer" + historia.id).click(function () {
                setCookie("idHistoria", historia.id, 0.1);
                window.location.assign("decisiones.html?idHistoria=" + historia.id);
            });
        } else if (tipo == 3) {
            $("#editar" + historia.id).click(function () {
                traer_clasificaciones(historia.id);
            });
            $("#OpcBotonesVer" + historia.id).click(function () {
                setCookie("idHistoria", historia.id, 0.1);
                window.location.assign("decisiones.html?idHistoria=" + historia.id);
            });
        }
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
    }
}


function traer_clasificaciones(id) {
    $.ajax({
        url: "Historia?id=" + id,
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (response) {
            console.log('IdHistoria', id)
            console.log(response.clasificacion)
            /*if (response.tipo === "ok") {
                console.log('Clasificaciones',response.clasificacion);
            }*/

        }, complete: function (result) {
            //ocultarBotones();
        }, error: function (result) {
            alert("Error interno")
        }
    });
}


function actualizarGradosHistorias() {
    clasifica = $("#grados").val();
    $.ajax({
        url: "Historia",
        type: "DELETE",
        headers: {
            id: getCookie("idHistoria"),
            clasificacion: clasifica
        },
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            console.log(result);
            if (result != "error") {
                console.log('siiiii funciono')
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