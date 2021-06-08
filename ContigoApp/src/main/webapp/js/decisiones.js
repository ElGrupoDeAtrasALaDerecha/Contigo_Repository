var c = 1;

var arbol;
var idHistoria;
const params = new URLSearchParams(window.location.search)
var idHistoria;
$(document).ready(function () {
    idHistoria = getCookie("idHistoria");
    if (idHistoria === "") {
        idHistoria = params.get("idHistoria");
        if (idHistoria === "") {
            alert("inválido");
            window.location.assign("index.html");
        }

        cargarHistoria()
        cargarSituacion()
    }
});
function cargarHistoria() {
    $.ajax({
        url: "Historia?id=" + idHistoria,
        type: "GET",
        beforeSend: function () {
        },
        success: function (result) {
            $(".fondo").css(
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
        /*txtOpciones += '<li><a id="' + opcion.id + '" class="opcion">' + opcion.titulo + '</a></li>';*/
        txtOpciones += `<button id="${opcion.id}" class="ui primary basic button"> ${opcion.titulo}</button>`
    }
    
    let txt =
    `      <h1>${nodo.titulo}</h1>
            <div id="${nodo.id}" class="contenido">
                <p>${nodo.texto}</p>
            </div>
            <br>
            <div class="botones">
            ${txtOpciones}
            </div>
            
    `
    $(".historia").append(txt);
    $(".ui.primary.basic.button").click(function () {
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



function borrarSituacion() {
    $(".historia").hide('fast', 'swing');
    $(".historia").empty();
    $(".historia").show('slow', 'swing');
}

function pintarFinal(nodo) {
    let txt =
        `<h1>${nodo.titulo}</h1>
        <div id="${nodo.id}" class="contenido">
            <p>${nodo.texto}</p>
        </div>
        <br>
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

    $(".historia").append(txt);
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
        utterThis.volume = 2
        utterThis.pitch = 0
        utterThis.rate = 1
        synth.speak(utterThis)
    }
}