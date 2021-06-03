
$(document).ready(function () {
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
