$(document).ready(function () {
    const footer =  `
    <div class="box-1">
        <div class="logoForm">
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
                <a href="#"><p class="itemForm">Conversatorios</p></a> 
                <a href="#"><p class="itemForm">Historias de decisión</p></a> 
                <a href="#"><p class="itemForm">Chat privado</p></a> 
                <a href="#"><p class="itemForm">Citas de ayuda</p></a> 
            </div>
            <div class="coll">
                <a href="#"><p class="option">About</p></a> 
                <a href="#"><p class="itemForm">Desarrolladores</p></a> 
                <a href="#"><p class="itemForm">Personal calificado</p></a> 
                <a href="#"><p class="itemForm">Contacto</p></a> 
            </div>
            <div class="coll">
                <a href="#"><p class="option">Soporte</p></a> 
                <a href="#"><p class="itemForm">Términos & Condiciones</p></a> 
                <a href="#"><p class="itemForm">Política de Privacidad</p></a> 
                <a href="#"><p class="itemForm">Preguntas frecuentes</p></a> 
            </div>
        </div>
        <p class="itemForm" id="copyright">Copyright © 2021 Contigo. Creado por El Grupo De Atrás A La Derecha</p>
    </div>

    `
    $('footer').append(footer)
})