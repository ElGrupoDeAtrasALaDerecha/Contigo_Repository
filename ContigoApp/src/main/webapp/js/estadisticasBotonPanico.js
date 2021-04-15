var clicks =0;
var estudiante
var fecha
const tiempoTranscurrido = Date.now();
const hoy = new Date(tiempoTranscurrido);

fecha = hoy.toLocaleDateString();

var boton = document.getElementById("generarEstadisticas");
var pCont = document.getElementById("areaContador");


$(document).ready(function () {
    estudiante = getCookie("documento");
    console.log(estudiante)
    console.log(fecha)
});

boton.onclick = function () {
    clicks++;
    pCont.textContent = clicks;
    var obj = {
        clicks: clicks,
        fecha : fecha,
        estudiante: estudiante    
      }
      console.log(obj)
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