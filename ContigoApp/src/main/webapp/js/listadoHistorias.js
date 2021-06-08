var listaHistorias;
var usu;


$(document).ready(function () {
    cargarListaDeHistorias();
    usu = parseInt(getCookie("tipoUsuario"));
    if (usu !== 2) {
        document.getElementById('btnCrear').style.display = 'none';

    }
    if (usu === 3) {
        traer_grados_ins();    
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
                            <a  href="deci.html?idHistoria=${historia.id}" class="textTitle">${historia.titulo}
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
                window.location.assign("deci.html");
            })
        } else if (tipo === 2) {
            $("#editar" + historia.id).click(function () {
                setCookie("idHistoria", historia.id, 0.1);
                window.location.assign("situaciones.html?idHistoria=" + historia.id);
            });
            $("#OpcBotonesVer" + historia.id).click(function () {
                setCookie("idHistoria", historia.id, 0.1);
                window.location.assign("deci.html?idHistoria=" + historia.id);
            });
        } else if (tipo == 3) {
            $("#editar" + historia.id).click(function () {
                traer_clasificaciones(historia.id);
                
            });
            $("#OpcBotonesVer" + historia.id).click(function () {
                setCookie("idHistoria", historia.id, 0.1);
                window.location.assign("deci.html?idHistoria=" + historia.id);
            });
        }
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
        url: "Historia?tipoConsulta=clasificaciones&id=" + id,
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (response) {
            console.log('IdHistoria', id)
            console.log(response.clasificacion)
            let clasifi = response.clasificacion;
            pintarClasificaciones(clasifi, id);

        }, complete: function (result) {
            
        }, error: function (result) {
            toastr.error('Error interno')
        }
    });
}


function actualizarGradosHistorias(id_historia) {
    let clasifica = $("#grados").val();
    $.ajax({
        url: "Historia",
        type: "DELETE",
        headers: {
            id: id_historia,
            clasificacion: clasifica
        },
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            console.log(result);
            if (result.tipo == "ok") {
                console.log(result.mensaje)
                toastr.success(result.mensaje)
            } else {
                toastr.error(result.mensaje)
            }
        },
        complete: function (result) {
        },
        error: function (result) {
        }
    });
}

function pintarClasificaciones(clasificaciones, idHistoria) {
    let opt = '';
    let arreglo = new Array();

    let texto = `
                <div class="ui tiny modal pop">
                    <div class="header">Asignación de grados</div>
                        <div class="content">
                            <div id="cursos" class="credencial">   
                                <select id="grados" name="" class="ui search fluid dropdown selection multiple" multiple="" id="multi-select">
                                    <option value="1">Transición</option>
                                    <option value="2">Primero</option>
                                    <option value="3">Segundo</option>
                                    <option value="4">Tercero</option>
                                    <option value="5">Cuarto</option>
                                    <option value="6">Quinto</option>
                                    <option value="7">Sexto</option>
                                    <option value="8">Séptimo</option>
                                    <option value="9">Octavo</option>
                                    <option value="10">Noveno</option>
                                    <option value="11">Décimo</option>
                                    <option value="12">Once</option>
                                </select>
                            </div>
                            <button class="ui cancel red button cerrar">Cerrar</button>
                            <button class="ui approve green button aceptar">Asignar</button>
                        </div>
                            
                                
                            
                </div>
                    
                `;
    $("#modal").append(texto);

    for (let index = 0; index < clasificaciones.length; index++) {
        opt += '<a class="ui label transition visible" data-value="' + clasificaciones[index].id + '" style="display: inline-block !important;">' + clasificaciones[index].grado + '<i class="delete icon"></i></a>';
        //$("#grados").val(clasificaciones[index].id)
        arreglo.push(clasificaciones[index].id)
    }
    $("#grados").val(arreglo)
    $(".ui.search.fluid.dropdown.selection.multiple").append(opt);
    $('.ui.dropdown').dropdown();
    $('.ui.modal')
        .modal('show')
        ;

    $(".cerrar").click(function () {
        $('.ui.modal').modal('hide');
        $(".cerrar").off('click');
        $(".ui.modal").remove();
    });
    $(".aceptar").click(function () {
        actualizarGradosHistorias(idHistoria);
    });
}

function traer_grados_ins() {
    $.ajax({
        url: "Historia?tipoConsulta=idInstitucion",
        type: "GET",
        headers:{
            idInstitu: getCookie('ID_Inst'),
        },
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (response) {
            console.log(response.Grados)
        }, complete: function (result) {
            //ocultarBotones();
        }, error: function (result) {
            toastr.error('Error interno')
        }
    });
}