$("#btnlista").click(function () {
    LlamarConver();
});


function LlamarConver() {
    informacion = {
        idConver: 10,
    };
    $.ajax({
        url: "Conversatorio",
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            console.log(result.conversatorios);
            conversatorios = result.conversatorios;
            listarConver(conversatorios);
            if (result != "error") {
                console.log(result);
            } else {
                console.log("error");
            }
        }, complete: function (result) {

        }, error: function (result) {
        }
    });
}

function listarConver(conversatorio){
    $("#conver").empty(); 
	for (var i = 0; i<conversatorio.length; i++) {
        text= '<div class="col-md-6 col-sm-6">'+
        '<div id="Caja-texto">'+
          '<img src="'+conversatorios[i].imagen+'" class="img-portafolio">'+
          '<div class="textoSobre-img">'+ conversatorios[i].titulo +
          '</div>'+
        '</div>'+
      '</div>';
      $("#conver").append(text);
	}
};


