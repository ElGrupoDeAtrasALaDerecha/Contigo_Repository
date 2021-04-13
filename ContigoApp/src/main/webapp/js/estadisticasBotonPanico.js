var clicks =0;
var estudiante
var fecha

var boton = document.getElementById("generarEstadisticas");
var pCont = document.getElementById("areaContador");


$(document).ready(function () {
    estudiante = parseInt(getCookie("documento"));
    console.log(estudiante)
});

boton.onclick = function () {
    clicks++;
    pCont.textContent = clicks;
    var obj = {
        estudiante: estudiante,
        clicks: clicks
      }
      console.log(obj)
      alert("6")
      enviarInformacion(obj)

}


function enviarInformacion(obj){
    $.ajax({
        method: 'POST',
        url: 'gestionCurso',
        data: JSON.stringify(obj),
        dataType: "json",
        success: function(response) {
            if (response.tipo === "ok") {
                console.log(response);
                // console.log(response.mensaje)
                $(location).attr('href', 'gestionCurso.html');
            }else{
                console.log(response.mensaje);
            }
        },
        error: function(response){
            console.log(JSON.stringify(response))
        }
    }); 
}