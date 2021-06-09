var notificaciones;
$(document).ready(function () {
    const menuSuperior = `
        <nav class="navbar navbar-expand-lg navbar-light bg-light ui fixed menu">
            <div class="container-fluid">
                <a class="navbar-brand" href="opciones.html">Contigo</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <div class="minav">
                        <div class="navitems">
                            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                                <li class="nav-item">
                                    <a class="nav-link active" aria-current="page" href="listadoHistorias.html">Decisiones</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link active" aria-current="page" href="citasEst.html">Citas</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link active" aria-current="page" href="Conversatorios.html">Conversatorios</a>
                                </li>
                            </ul>
                        </div>
                    </div>


                    <a id="notificaciones">
                        
                        <div id="menuNotificaciones" class="ui scrolling pointing dropdown link right item">
                        
                            <i class="big bell icon"></i> 
                            <div id="itemsNotificaciones" class="menu">
                                <div class="header">Notificaciones</div>  
                            </div>
                        </div>
                    </a>
                    <a>
                    <div class item>
                    
                    </div>
                    </a>
                    <a id="salir">
                        <div class="log-out" >
                            Salir
                            <ion-icon name="log-out-outline"></ion-icon>
                        </div>
                    </a>
                </div>
            </div>
        </nav>`

    $('body').prepend(menuSuperior)

    const footer =  `
    <div class="box-1">
        <div class="logo">
            <a href="#"><p class="title">Contigo</p></a>
        </div>
        <div class="box-11">
            <div class="icon">    
                <a href="#"><img class="sepia" src="Iconos/facebook.png" alt=""></a>
            </div>
            <div class="icon">
                <a href="#"><img class="sepia" src="Iconos/instagram.png"></a>
            </div>
            <div class="icon">
                <a href="#"><img class="sepia" src="Iconos/linkedin.png"></a>
            </div>
            <div class="icon">
                <a href="#"><img class="sepia" src="Iconos/youtube.png"></a>
            </div>
        </div>
        <div class="row">
            <img id="lines" src="img/lines.png" alt="">
        </div>
    </div>
    <div class="box-2">
        <div class="rxw">
            <div class="coll">
                <a href="#"><p class="option">Servicios</p></a> 
                <a href="#"><p class="item">Conversatorios</p></a> 
                <a href="#"><p class="item">Historias de decisión</p></a> 
                <a href="#"><p class="item">Chat privado</p></a> 
                <a href="#"><p class="item">Citas de ayuda</p></a> 
            </div>
            <div class="coll">
                <a href="#"><p class="option">About</p></a> 
                <a href="#"><p class="item">Desarrolladores</p></a> 
                <a href="#"><p class="item">Personal calificado</p></a> 
                <a href="#"><p class="item">Contacto</p></a> 
            </div>
            <div class="coll">
                <a href="#"><p class="option">Soporte</p></a> 
                <a href="#"><p class="item">Términos & Condiciones</p></a> 
                <a href="#"><p class="item">Política de Privacidad</p></a> 
                <a href="#"><p class="item">Preguntas frecuentes</p></a> 
            </div>
        </div>
        <p class="item" id="copyright">Copyright © 2021 Contigo. Creado por El Grupo De Atrás A La Derecha</p>
    </div>
    `
    $('footer').append(footer)
    $('body').append(`<script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>`)
    $("#salir").click(function(){
        cerrarSesion();
    });

    verNotificaciones();
})

function cerrarSesion() {
    delete_cookie("token");
    delete_cookie("tipoUsuario");
    delete_cookie("documento");
    window.location.assign("index.html");
}


function verNotificaciones(){
    
    let tipoUsuario=getCookie("tipoUsuario");
    if(tipoUsuario==="1" || tipoUsuario==="2"){
        $.ajax({
            url: 'Notificacion',
            method: 'GET',
            dataType: 'json',
            headers:{
                token:getCookie("token")
            },
            success: function (response) {
                if(response.tipo==="ok"){
                    notificaciones=response.notificaciones
                    pintarNotificaciones();
                }
            },
            error: function (response) {
                //window.location.assign("index.html")
            }
        })
    }
}


function pintarNotificaciones(){
    
    if(notificaciones.length===0){
        let tituloNueva=`<div class="header">No hay notificaciones para mostrar</div>`;
        $("#itemsNotificaciones").append(tituloNueva);
    }else{
        let notificacionesNuevas=0
        for (let i = 0; i < notificaciones.length; i++) {
            
            let notificacion = notificaciones[i];
            if(notificacion.vista===false && i===0){
                let tituloNueva=`<div class="header">Nuevas</div>`;
                    $("#itemsNotificaciones").append(tituloNueva);
            }
            else if(notificacion.vista ===true && i===0){
                let tituloNueva=`<div class="header">Antiguas</div>`;
                    $("#itemsNotificaciones").append(tituloNueva);
            }
            else if(notificacion.vista===true && notificaciones[i-1].vista===false){
                let tituloNueva=`<div class="header">Antiguas</div>`;
                    $("#itemsNotificaciones").append(tituloNueva);
            }
            let txtActiva="";
            if(notificacion.vista===false){
                txtActiva="active";
                notificacionesNuevas++;
            }
            let txt=`<div id="n-${notificacion.id}" class="ui ${txtActiva} item">
                        <div class="ui header">
                            ${notificacion.titulo}
                        </div>
                        <div class="ui content">
                            ${notificacion.texto}
                        </div>
                    </div>
                    <div class="ui divider"> </div>`
            $("#itemsNotificaciones").append(txt);
           
                $("#n-"+notificacion.id).click(function(){
                    console.log("Dio click")
                    if(notificacion.vista===false){    
                        actualizarNotificacion(notificacion);
                    }
                })
            
        }
        if(notificacionesNuevas!==0){
            let numeroEnNotificaciones=`<div class="ui blue circular top left attached label">${notificacionesNuevas}</div>`
            $(".bell.icon").after(numeroEnNotificaciones);
        }
        $(".dropdown").dropdown();
    }
    
}
/**
 * Función que actualiza el estado de una notificación (que pasa de)
 * @param {*} notificacion 
 */
function actualizarNotificacion(notificacion){
    $.ajax({
        url: 'Notificacion',
        method: 'PUT',
        dataType: 'json',
        data: JSON.stringify(notificacion),
        contentType: "JSON application/json charset=utf-8",
        headers:{
            token:getCookie("token")
        },
        success: function (response) {
            
        },
        complete: function(){
            if(notificacion.tipo==="cita"){
                window.location.assign("admin_perca.html")
            }
            if(notificacion.tipo==="conversatorio"){
                window.location.assign("Conversatorios.html");
            }
            if(notificacion.tipo==="historia"){
                window.location.assign("listadoHistorias.html");
            }
        },
        error: function (response) {
            //window.location.assign("index.html")
        }
    })
}