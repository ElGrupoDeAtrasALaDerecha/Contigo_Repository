$("#ing_est").click(function(e){
    e.preventDefault();
    var doc=$("#documento").val();
    var pass=$("#contraseña").val();
        var obj={
            documento: doc,
            contraseña: pass
        };
        console.log(obj);
        loginEstudiante(obj);
});

/**
 * Función login
 * @param {*} obj 
 */
function loginEstudiante(obj){
    $.ajax({
        method: 'POST',
        url: 'LoginEstudiante',
        data: JSON.stringify(obj),
        dataType: "json",
        success: function(response) {
            console.log(response);
            debugger
            if(response.tipo==="ok"){
                setCookie("token",response.estudiante.token,0.3);
                alert("Mensaje: "+response.mensaje)
                $(location).attr('href','opciones.html');
            }
            else{
                alert("Error: "+response.mensaje)
            }
        },
        error: function(response){
            console.log(JSON.stringify(response))
        }
    }); 
}

