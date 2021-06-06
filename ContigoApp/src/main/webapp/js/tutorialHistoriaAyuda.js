$(document).ready(function () {
    prepararBoton()
});

/**
 * Función que carga el botón en la vista de tutorial
 */
function prepararBoton(){

    let txt = `<button id="btnAyudaTutorial" class="ui grey circular massive icon button"  data-content="¿Ayuda?" data-variation="huge">
                <i class="icon question circle"></i>
            </button>`
    $("#general").prepend(txt);
    $('#btnAyudaTutorial').popup();
    $("#btnAyudaTutorial").css({
        "position": "fixed",
        "z-index": "10000",
        "top": "20%",
        "left": "95%",
        "font-size":"150%"
    });
    $(".massive.item").css("font-size","2rem");

    $("#btnAyudaTutorial").click(function(){
        
        $(".sidebar").sidebar('setting', 'transition', 'overlay').
        sidebar("toggle");
    })
    $("#cerrarMenu").click(function(){
        $(".sidebar").sidebar('setting', 'transition', 'overlay').
        sidebar("toggle");
    });
    $("#btnAyudaCrearSituacion").click(function(){
        mostrarAyudaCrearSituacion();
    })
    
    $("#btnAyudaEditarSituacion").click(function(){
        mostrarAyudaEditarSituacion();
    })

    $("#btnAyudaFinal").click(function(){
        mostrarAyudaCrearFinal();
    })

    $("#btnAyudaEliminarSituacion").click(function(){
        mostrarAyudaEliminarSituacion();
    });
    $("#btnAyudaComoQuedaHistoria").click(function(){
        mostrarComoDebeQuedarLaHistoria();
    })

}

function mostrarAyudaCrearSituacion(){
    let elModal = `
    <div id="modalCrearSituacion" class="ui modal">
    <div class="header">
      ¿Como crear una situación?
    </div>
    <div class="content">
      <div class="ui form">
        <div class="ui field">
            <p>Para crear una nueva situación se debe dar click en el botón con un signo de más (+).
            Con esta opción tiene la posibilidad de indicar continuidad con la historia ya que así se crearán
            nuevas ramas de la historia. Tiene una posibilidad de crear hasta 3 opciones por situación</p>            
        </div> 
        <div class="ui field image">
            <img src="img/Tutorial/Crear situacion.gif" alt="crear situación">
        </div>   
        <div class="actions">
            <div class="ui field">
            <button id="btnEntendidoCrearSituacion" class="ui primary fluid button">¡Entendido!</button>
            </div>
        </div>
      </div>
    </div>
  </div>
    `

    $("body").append(elModal);

    $('#modalCrearSituacion').find(".header").css("font-size","2.3em");
    $('#modalCrearSituacion').find(".field").css("font-size","1.7em");
    $('#modalCrearSituacion').find("button").css("font-size","1em");
    $('#modalCrearSituacion').find(".image").css("text-align","right")
    $('#modalCrearSituacion').css("overflow-y", "scroll")
    $("#modalCrearSituacion").find("img").css({
        "width":"98%",
        "height":"25%"
    })
     
    $('#modalCrearSituacion')
        .modal('show');
    ;
    $("#btnEntendidoCrearSituacion").click(function (e) {
        e.preventDefault()
        $('#modalCrearSituacion')
        .modal('hide');
        $('#modalCrearSituacion')
        .remove();
        $(this).off("click");
    });
}


function mostrarAyudaEditarSituacion(){
    let elModal = `
    <div id="modalEditarSituación" class="ui modal">
    <div class="header">
      ¿Cómo editar una situación?
    </div>
    <div class="content">
      <form class="ui form">
        <div class="ui field">
            <p>Para editar una situación o un final, debe dar click en el lápiz. 
            Se desplegará una ventana que le permitirá ingresar el título de la situación y su descripción </p>
            
        </div> 
        <div class="ui field image">
            <img src="img/Tutorial/Editar situacion.gif" alt="Editar situación">
        </div>   
        <div class="actions">
            <div class="ui field">
            <button id="btnEntendidoEditarSituacion" class="ui primary fluid button">¡Entendido!</button>
            </div>
        </div>
      </form>
    </div>
  </div>
    `

    $("body").append(elModal);

    $('#modalEditarSituación').find(".header").css("font-size","2.3em");
    $('#modalEditarSituación').find(".field").css("font-size","1.7em");
    $('#modalEditarSituación').find("button").css("font-size","1em");
    $('#modalEditarSituación').find(".image").css("text-align","right")
    $('#modalEditarSituación').css("overflow-y", "scroll")
    $("#modalEditarSituación").find("img").css({
        "width":"98%",
        "height":"25%"
    })
     
    $('#modalEditarSituación')
        .modal('show');
    ;
    $("#btnEntendidoEditarSituacion").click(function (e) {
        e.preventDefault()
        $('#modalEditarSituación')
        .modal('hide');
        $("#modalEditarSituación").remove();
        $(this).off("click");
        
    });
}

function mostrarAyudaCrearFinal(){
    let elModal = `
    <div id="modalCrearFinal" class="ui modal">
    <div class="header">
      ¿Cómo crear un final?
    </div>
    <div class="content">
      <form class="ui form">
        <div class="ui field">
            <p>Para crear es necesario que haya una situación sin opciones posteriores. 
            Para que se convierta es necesario dar click en el botón establecer final para que esta se convierta
            en un final.</p>  
            
        </div> 
        <div class="ui field image">
            <img src="img/Tutorial/Crear final.gif" alt="Crear final">
        </div>   
        <div class="actions">
            <div class="ui field">
            <button id="btnEntendidoCrearFinal" class="ui primary fluid button">¡Entendido!</button>
            </div>
        </div>
      </form>
    </div>
  </div>
    `

    $("body").append(elModal);

    $('#modalCrearFinal').find(".header").css("font-size","2.3em");
    $('#modalCrearFinal').find(".field").css("font-size","1.7em");
    $('#modalCrearFinal').find("button").css("font-size","1em");
    $('#modalCrearFinal').find(".image").css("text-align","right")
    $('#modalCrearFinal').css("overflow-y", "scroll")
    $("#modalCrearFinal").find("img").css({
        "width":"98%",
        "height":"25%"
    })
     
    $('#modalCrearFinal')
        .modal('show');
    ;
    $("#btnEntendidoCrearFinal").click(function (e) {
        e.preventDefault()
        $('#modalCrearFinal')
        .modal('hide');
        $("#modalCrearFinal").remove();
        $(this).off("click")
        
    });
}

function mostrarAyudaEliminarSituacion(){
    let elModal = `
    <div id="modalEliminarSituacion" class="ui modal">
    <div class="header">
      ¿Cómo eliminar una situación?
    </div>
    <div class="content">
      <form class="ui form">
        <div class="ui field">
            <p>Se debe dar click en el ícono rojo con un menos (-)
            Para eliminar es necesario que haya una situación sin opciones posteriores. 
            La opción funciona para situaciones finales</p>
        </div> 
        <div class="ui field image">
            <img src="img/Tutorial/Eliminar situación.gif" alt="Eliminar situación">
        </div>   
        <div class="actions">
            <div class="ui field">
            <button id="btnEntendidoEliminarSituacion" class="ui primary fluid button">¡Entendido!</button>
            </div>
        </div>
      </form>
    </div>
  </div>
    `

    $("body").append(elModal);

    $('#modalEliminarSituacion').find(".header").css("font-size","2.3em");
    $('#modalEliminarSituacion').find(".field").css("font-size","1.7em");
    $('#modalEliminarSituacion').find("button").css("font-size","1em");
    $('#modalEliminarSituacion').find(".image").css("text-align","right")
    $('#modalEliminarSituacion').css("overflow-y", "scroll")
    $("#modalEliminarSituacion").find("img").css({
        "width":"98%",
        "height":"25%"
    })
     
    $('#modalEliminarSituacion')
        .modal('show');
    ;
    $("#btnEntendidoEliminarSituacion").click(function (e) {
        e.preventDefault()
        $('#modalEliminarSituacion')
        .modal('hide');
        $("#modalEliminarSituacion").remove();
        $(this).off("click")
        
    });
}

function mostrarComoDebeQuedarLaHistoria(){
    let elModal = `
    <div id="modalComoQuedaHistoria" class="ui modal">
    <div class="header">
      ¿Cómo saber si mi historia está correcta?
    </div>
    <div class="content">
      <form class="ui form">
        <div class="ui field">
            <p>Su historia debe tener finales en la parte inferior de cada una de sus ramas. 
            Si no, su historia aún no está completa</p>
        </div> 
        <div class="ui field image">
            <img src="img/Tutorial/Cómo queda la historia.png" alt="Como queda historia">
        </div>   
        <div class="actions">
            <div class="ui field">
            <button id="btnEntendidoComoQuedaHistoria" class="ui primary fluid button">¡Entendido!</button>
            </div>
        </div>
      </form>
    </div>
  </div>
    `

    $("body").append(elModal);

    $('#modalComoQuedaHistoria').find(".header").css("font-size","2.3em");
    $('#modalComoQuedaHistoria').find(".field").css("font-size","1.7em");
    $('#modalComoQuedaHistoria').find("button").css("font-size","1em");
    $('#modalComoQuedaHistoria').find(".image").css("text-align","right")
    $('#modalComoQuedaHistoria').css("overflow-y", "scroll")
    $("#modalComoQuedaHistoria").find("img").css({
        "width":"98%",
        "height":"25%"
    })
     
    $('#modalComoQuedaHistoria')
        .modal('show');
    ;
    $("#btnEntendidoComoQuedaHistoria").click(function (e) {
        e.preventDefault()
        $('#modalComoQuedaHistoria')
        .modal('hide');
        $("#modalComoQuedaHistoria").remove();
        $(this).off("click")
        
    });
}