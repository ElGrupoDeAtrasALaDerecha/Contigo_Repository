const params = new URLSearchParams(window.location.search)
$(document).ready(function(){
    
    let codigo= params.get("codigo")
    console.log(codigo);

});


