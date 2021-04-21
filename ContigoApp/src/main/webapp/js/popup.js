var clase;
var btnAbrirPopup = document.getElementById('btn-abrir-popup'),
    overlay = document.getElementById('overlay'),
    popup = document.getElementById('popup');




btnCerrarPopup = document.getElementById('btn-cerrar-popup');

/*btnCerrarPopup.addEventListener('click', function () {
    
});*/

var situa = 0;

function crear() {
    /**/
    let txt = '<div class="overlay active" id="overlay">' +
        '<div class="popup active" id="popup">' +
        '<a href="#" id="btn-cerrar-popup" class="btn-cerrar-popup" onclick="eliminar()"> ' +
        '<i class="fa fa-times" aria-hidden="true"></i>' + '</a>' +
        '<h3> '+historia.titulo+' </h3>' +
        '<h4>Formulario</h4>' +
        '<form action="">' +
        '<div class="contenedor-inputs">' +
        '<input type="text" id="Next" placeholder="Texto siguiente">' +
        '<div class="numeroOpc">' +
        '<p>Numero de opciones (Max 3)</p>' +
        '<button id="opciones" class="Aumentar" onclick=mas()>+</button>' +
        '<button class="Disminuir" id="menos" onclick=quitaropc()>-</button>' +
        '</div>' +
        '<div id="extra"></div>' +
        '</div>' +
        '<input type="submit"  class="btn-submit" onclick=crearRamita() value="Crear">'
        + '</form>'
        + '</div>'
        + '</div';
    $("#ventana").append(txt);
}

function eliminar() {
    $('#overlay').remove();
    n = 0;
}



var historia;
window.onload = function obtenerhisotia() {
    var text;
    $.ajax({
        url: "Historia?id=" + getCookie("idHistoria"),
        type: "GET",
        dataType: "json",
        success: function (result, textStatus, request) {
            if (result != "error") {
                console.log(result);
                historia = result.historia;
                console.log(getCookie("idHistoria"))
                $("#Organigrama-resultante").empty();
                var nom = '<li id="Holi" class="parte'+situa+'">'+historia.titulo+'</li>';
                $("#Diagrama").append(nom);
                $('#Organigrama-resultante').orgchart({
                    'data': $('#Diagrama')
                });
            } else {
                console.log("error");
            }

        },
        complete: function (result) {


        },
        error: function (result) {

        }

    });
}

 


function crearRamita() {
    var Textito = $("#Next").val();
    console.log(Textito);
    if (n == 1) {
        $("#Organigrama-resultante").empty();
        let crearR = 
            '<ul>' +
            '<li>' + Textito +
            '<ul>' +
            '<li class="parte'+(situa=situa+1)+'">' + $("#1").val() + ' </li>' +
            '</ul>' +
            '</li>' +
            '</ul>';
        $("#Holi").append(crearR);
        $('#Organigrama-resultante').orgchart({
            'data': $('#Diagrama')
        });
        $('#overlay').remove();
        console.log($(".parte"+situa).val())
    }
    if (n == 2) {
        $("#Organigrama-resultante").empty();
        let crearR =
            '<ul>' +
            '<li>' + Textito +
            '<ul>' +
            '<li class="parte'+(situa=situa+1)+'">' + $("#1").val() + ' </li>' +
            '<li class="parte'+(situa=situa+1)+'">' + $("#2").val() + '</li>' +
            '</ul>' +
            '</li>' +
            '</ul>';
        $("#Holi").append(crearR);
        $('#Organigrama-resultante').orgchart({
            'data': $('#Diagrama')
        });
        $('#overlay').remove();
    }
    if (n == 3) {
        $("#Organigrama-resultante").empty();
        let crearR =
            '<ul>' +
            '<li>' + Textito +
            '<ul>' +
            '<li class="parte'+(situa=situa+1)+'">' + $("#1").val() + ' </li>' +
            '<li class="parte'+(situa=situa+1)+'">' + $("#2").val() + '</li>' +
            '<li class="parte'+(situa=situa+1)+'">' + $("#3").val() + ' </li>' +
            '</ul>' +
            '</li>' +
            '</ul>';
        $("#Holi").append(crearR);
        $('#Organigrama-resultante').orgchart({
            'data': $('#Diagrama')
        });
        $('#overlay').remove();
    }
    $('li').off('click');
    $('li').click(function(){
         clase = $(this).attr('class');
        
    });


    console.log(n)
}

