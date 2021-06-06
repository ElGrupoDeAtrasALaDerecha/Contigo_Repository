var c = 1;

var arbol;
var idHistoria;
const params = new URLSearchParams(window.location.search)
var idHistoria;
$(document).ready(function () {
    idHistoria = getCookie("idHistoria");
    if(idHistoria===""){
        idHistoria = params.get("idHistoria");
        if(idHistoria===""){
            alert("inválido");
            window.location.assign("index.html");
        }
   
    }
    cargarHistoria()
    cargarSituacion()
});
function cargarHistoria() {
    $.ajax({
        url: "Historia?id=" + idHistoria,
        type: "GET",
        beforeSend: function () {
        },
        success: function (result) {
            $("#contenedor").css(
                'background-image', 'url(' + result.historia.urlImagen + ')'
            );
        },
        complete: function (result) {
        },
        error: function (result) {
        }
    });
}
function cargarSituacion() {
    $.ajax({
        url: "Situacion?id=" + idHistoria,
        type: "GET",
        beforeSend: function () {
        },
        success: function (result) {
            arbol = result.situaciones
            pintarSituacion(arbol.primerNodo);
        },
        complete: function (result) {
        },
        error: function (result) {
        }
    });
}


/***
 * 
 * @param {int} id 
 * @param {situacion} nodo 
 * @returns Un objeto situación o final
 */
function buscarNodo(id, nodo) {
    if (nodo === undefined) {
        nodo = arbol.primerNodo;
    }
    if (nodo.id === id) {
        return nodo;
    } else {
        let opciones = nodo.opciones;
        if (opciones !== undefined) {
            for (let i = 0; i < opciones.length; i++) {
                let opcion = opciones[i];
                if (opcion.id === id) {
                    return opcion;
                } else {
                    if (opcion.opciones !== undefined && opcion.opciones.length > 0) {
                        let nodoEncontrado = buscarNodo(id, opcion);
                        if (nodoEncontrado !== undefined) {
                            return nodoEncontrado;
                        }
                    }
                }
            }
        }
    }
}

var texto;
function pintarSituacion(nodo) {
    texto = nodo.texto;
    let opciones = nodo.opciones;
    let txtOpciones = "";
    for (let i = 0; i < opciones.length; i++) {
        let opcion = opciones[i];
        txtOpciones += '<li><a id="' + opcion.id + '" class="opcion">' + opcion.titulo + '</a></li>';
    }
    let txt =
        `<h2>${nodo.titulo}</h2>
        <div id="${nodo.id}">
            <aside id="contenedor_dsc">
                <p>${nodo.texto}</p>
            </aside>
            
            <section id="contenedor_esc">
                <ul id="esc">
                ${txtOpciones}
                </ul>
            </section>
            <div style="clear: both"></div>
        </div>
        `
    $("#color_bk").append(txt);
    $(".opcion").click(function () {
        let id = $(this).prop("id");
        let nodoNuevo = buscarNodo(parseInt(id), nodo);
        borrarSituacion();
        if (nodoNuevo.opciones !== undefined) {
            pintarSituacion(nodoNuevo);
        } else {
            pintarFinal(nodoNuevo);
        }
    });
}

document.getElementById('hablar').addEventListener("click", () => {
    console.log(texto)
    decir(texto)
});
function decir(texto) {
    //speechSynthesis.speak(new SpeechSynthesisUtterance(texto));
    speechSynthesis.onvoiceschanged = () => {
        const synth = speechSynthesis
        const voices = synth.getVoices()
        console.log(texto)
        const utterThis = new SpeechSynthesisUtterance(texto)
        utterThis.voice = voices.find(v => v.name === 'Jorge')
        utterThis.volume = 1
        utterThis.pitch = 0
        utterThis.rate = 1
        synth.speak(utterThis)
      }
}

function borrarSituacion() {
    $("#color_bk").hide('fast', 'swing');
    $("#color_bk").empty();
    $("#color_bk").show('slow', 'swing');
}

function pintarFinal(nodo) {
    let txt =
        `<h2>${nodo.titulo}</h2>
    <div id="${nodo.id}">
        <aside id="contenedor_dsc">
            <p>${nodo.texto}
            <span>Aquí la historia ha terminado. Da click en volver <br>
            al inicio para volver a la ventana principal</span>
            </p>
        </aside>
        <section id="contenedor_esc">
            <ul id="esc">
                <li><a href="listadoHistorias.html">Volver al inicio</a></li>
            </ul>
        </section>
        <div style="clear: both"></div>
    </div>`

    $("#color_bk").append(txt);
}