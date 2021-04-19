var opcGrado = document.getElementById('opcGrado');
var divge = document.getElementById('ge');
var btnDatos = document.getElementById('btn');
var opcVisualizar;//Select de opcion a visualizar tipo de graficas a analizar 
var grados;
var listaEstudiantes= document.getElementById('ListaEstudiantes');;
var arregloEstudiantes;
opcGrado.style.display = "none"
btnDatos.style.display = "none"
listaEstudiantes.style.display = "none"
function obtenerSelect() {
    /* Para obtener el valor */
     opcVisualiza = document.getElementById("consulta").value;
    if (opcVisualiza === "1" || opcVisualiza ==="2") {
        opcGrado.style.display = "block"
        divge.style.display = "none"
        
        
    } else if (opcVisualiza === "") {
        opcGrado.style.display = "none"
    }
}

function selects(){
    grados = document.getElementById("grados").value;
    var tiempo= document.getElementById("frecuenciaEstadisticas").value;
    if (grados !== "" && tiempo !== "") {
        btnDatos.style.display = "block"
        
        
    } else {
        btnDatos.style.display = "none"
    }
}

function traerEstudiantes(){
    if(opcVisualizar === "2"){
        if(grados !==""){
            listaEstudiantes.style.display = "block"
            $.ajax({
                method: 'GET',
                url: 'EstudiantePorGradoServlet?grado='+grados,
                dataType: "json",
                contentType: 'JSON application/json charset=utf-8',
                headers:{
                    token:getCookie("token")
                }, 
                success: function (response) {
                    if (response.tipo === "ok") {
                        arregloEstudiantes = response.estudiantes;
                        llenarSelect();
                        listaEstudiantes.style.display = "block"
                        
                    }else{
                        console.log(response.mensaje);
                    }
                },
                error: function (response) {
                    console.log("Error: " + response.mensaje)
                    
                }
            });
        }
    }
}

function llenarSelect(){
    for(var i=0;i<arregloEstudiantes.length;i++){
        let txt=`<option value="${arregloEstudiantes[i].documento}">${arregloEstudiantes[i].primerNombre} ${arregloEstudiantes[i].segundoNombre} ${arregloEstudiantes[i].primerApellido} ${arregloEstudiantes[i].segundoApellido}</option>`
        $("#estudiantes").append(txt);
    }
}

$("#btnGerar").on("click", function () {
    window.location.assign("gestionCurso.html")
});