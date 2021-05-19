$(document).ready(function () {
    const footer =  `
    <div id="footer">
        <div id="superior">
            <ul>
                <li><a href="index.html" class="menuFooter">Inicio</a></li>
                <li><a href="Conversatorios.html" class="menuFooter">Conversatorios</a></li>
                <li><a href="listadoHistorias.html" class="menuFooter">Decisiones</a></li>
                <li><a href="#" class="menuFooter">Ayuda</a></li>
            </ul>
            <hr>
        </div>
        <table id="table-footer">
            <tr>
                <td id="Columna-footer">
                    <div id="PrimeraColumna">
                        <div>
                            <span id="contigo2"><a href="#">Contigo</a></span>
                        </div>
                        <div id="iconos">
                            <a href="#"><img class="facebook" src="Iconos/facebook.png"></a>
                            <a href="#"><img class="instagram" src="Iconos/instagram.png"></a>
                            <a href="#"><img class="linkedin" src="Iconos/linkedin.png"></a>
                            <a href="#"><img class="youtube" src="Iconos/youtube.png"></a>
                        </div>
                    </div>
                </td>
                <td id="Columna-footer">
                    <div id="SegundaColumna">
                        <div>
                            <span id="bold">Empresa</span>
                        </div>
                        <div>
                            <span><a href="#">Terminos & Condiciones</a></span>
                        </div>
                        <div>
                            <span><a href="#">Política de Privacidad</a></span>
                        </div>
                        <div>
                            <span><a href="#">FAQS</a></span>
                        </div>
                        <div>
                            <span><a href="ingresoPerca.html">Equipo</a></span>
                        </div>
                    </div>
                </td>
                <td id="Columna-footer">
                    <div id="TercerColumna">
                        <div>
                            <span id="bold">About</span>
                        </div>
                        <div>
                            <span><a href="#">Desarrolladores</a></span>
                        </div>
                        <div>
                            <span><a href="#">Blog</a></span>
                        </div>
                        <div>
                            <span><a href="#">Contacto</a></span>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
        <div id="Inferior">
            <div>
                <span>Copyright © 2021 Contigo. Creado por El Grupo De Atrás A La Derecha</span>
            </div>
        </div>
    </div>
    `
    $('#footer').append(footer)
})