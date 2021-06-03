
$(document).ready(function () {
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
        const menuSuperior = `
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
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
                                    <a class="nav-link active" aria-current="page" href="decisiones.html">Decisiones</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link active" aria-current="page" href="citas.html">Citas</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link active" aria-current="page" href="Conversatorios.html">Conversatorios</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <a href="index.html">
                        <div class="log-out" id="salir">
                            Salir
                            <ion-icon name="log-out-outline"></ion-icon>
                        </div>
                    </a>
                </div>
            </div>
        </nav>`

        $('body').prepend(menuSuperior)
        $("#salir").click(function(){
            cerrarSesion();
        });
})

function cerrarSesion() {
    delete_cookie("token");
    delete_cookie("tipoUsuario");
    delete_cookie("documento");
    window.location.assign("index.html");
}
