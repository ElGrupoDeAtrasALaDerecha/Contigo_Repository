var c=1;

var arbol;
$(document).ready(function(){
    cargarSituacion()
});

function cargarSituacion(){
    $.ajax({
        url: "Situacion?id="+getCookie("idHistoria"),
        type: "GET",
        beforeSend: function () {
        },
        success: function (result) {
            arbol=situaciones
            pintarSituacion(arbol.primerNodo);    
        },
        complete: function (result) {
        },
        error: function (result) {
        }
    });
}


/**
 * 
 * @param {int} id 
 * @param {situacion} nodo 
 * @returns Un objeto situación o final
 */
function buscarNodo(id,nodo){
    if(nodo===undefined){
        nodo===situaciones.primerNodo;
    }
    if(nodo.id===id){
        return nodo;
    }else{
        opciones=nodo.opciones;
        if(opciones!==undefined){
            for (let i = 0; i < opciones.length; i++) {
                if(opciones[i].id){
                    return opciones[i];
                }else{
                    if(opciones[i].opciones!==undefined){
                        return buscarNodo(id,opciones[i]);
                    }
                }
                
            }
        }
    }
}


function pintarSituacion(nodo){
    let opciones=nodo.opciones;
    let txtOpciones="";
    for (let i = 0; i < opciones.length; i++) {
        let opcion= opciones[i];
        txtOpciones+='<li><a id="'+opcion.id+'" class="opcion">'+opcion.titulo+'</a></li>';
    }
    let txt=
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
    $(".opcion").click(function(){
        let id=$(this).prop("id");
        let nodo=buscarNodo(id);
        if(nodo.opciones!==undefined){
            borrarSituacion();
            pintarSituacion(nodo);
        }else{

        }
    });
}

function borrarSituacion(){
    $("#color_bk").empty();
}
$("#Primera").click(function() {
    $("#situa").hide("slow");
    mostrar();
});
$("#Segunda").click(function() {
    $("#situa").toggle("slow");
    $("#situa3").toggle("slow");
});
$("#Primera2").click(function() {
    $("#situa2").toggle("slow");
    $("#resp1").toggle("slow");
});
$("#Segunda2").click(function() {
    $("#situa2").toggle("slow");
    $("#resp2").toggle("slow");
});
$("#Tercera2").click(function() {
    $("#situa2").toggle("slow");
    $("#situa3").toggle("slow");
});
$("#Primera3").click(function() {
    $("#situa3").toggle("slow");
    $("#resp2").toggle("slow");
});
$("#Segunda3").click(function() {
    $("#situa3").toggle("slow");
    $("#resp3").toggle("slow");
});
$("#Tercera3").click(function() {
    $("#situa3").toggle("slow");
    $("#resp1").toggle("slow");
});


function mostrar(){
    $("#situa2").toggle("slow");
}
