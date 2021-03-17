$("#Ingresar").click(function(e){
    e.preventDefault();
    var email=$("#correo").val();
    var pass=$("#contraseña").val();
    if(validarEmail(email)){
        var obj={
            correo: email,
            contraseña: pass
        };
        loginUsuario(obj)
    }else{
        alert("Formato de correo inválido") ;
    }
});


function loginUsuario(obj){
    $.ajax({
        method: 'POST',
        url: 'LoginPersonalCalificado',
        data: obj,
        dataType: "json",
        success: function(response) {
            if(response.tipo==="ok"){
                setCookie("token",response.personal.token,0.3);
                alert("Mensaje: "+response.mensaje)
                $(location).attr('href','admin_perca.html')
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

