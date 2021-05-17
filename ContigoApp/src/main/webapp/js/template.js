
$(document).ready(function () {
const footer = `<div id="footer">
                    <div id="superior">
                        <ul>
                            <li><a href="#" class="menuFooter">Inicio</a></li>
                            <li><a href="Conversatorios.html" class="menuFooter">Conversatorios</a></li>
                            <li><a href="listadoHistorias.html" class="menuFooter">Decisiones</a></li>
                            <li><a href="citas.html" class="menuFooter">Citas</a></li>
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
                                        <span><a href="#">Equipo</a></span>
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
                    </div>`

        const menuSuperior = `<div id="menu">
                            <ul>
                                <li><a id="contigo-1" href="index.html">Contigo</a></li>
                                <div id="up">
                                    <li><a href="opciones.html">Inicio</a></li>
                                    <li><a href="Conversatorios.html">Conversatorios</a></li>
                                    <li><a href="listadoHistorias.html">Decisiones</a></li>
                                    <li><a href="citas.html" class="menuFooter">Citas</a></li>
                                    <li><a href="#">Ayuda</a></li>
                                 
                                    <li style="float:right;"><div id="salir"><a>Salir</a></div></li>
                                </div>
                            </ul>
                            </div>`

        $('body').prepend(menuSuperior)
        $('body').append(footer)
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
