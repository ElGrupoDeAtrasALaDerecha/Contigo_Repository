$(document).ready(function () {
    LlamarConver();
    document.getElementById('content1').style.display = 'block';
    document.getElementById('content2').style.display = 'none';
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

function listarConver(conversatorio) {
    $("#conver").empty();
    for (var i = 0; i < conversatorio.length; i++) {
        text = '<div id = "' + conversatorios[i].id + '" class="col-md-6 col-sm-6 conversatorio">' +
            '<div id="Caja-texto">' +
            '<img src="' + conversatorios[i].imagen + '" class="img-portafolio">' +
            '<div class="textoSobre-img">' + conversatorios[i].titulo +
            '</div>' +
            '</div>' +
            '</div>';
        $("#conver").append(text);
    }

    text = '<div id = "agregarConversatorio" class="col-md-6 col-sm-6">' +
        '<div id="Caja-texto">' +
        '<img src="img/unMAS.jpg" class="img-portafolio">' +
        '<div class="textoSobre-img"> Agregar conversatorio' +
        '</div>' +
        '</div>' +
        '</div>';
    $("#conver").append(text);

    $(".conversatorio").on("click", function () {
        document.getElementById('content2').style.display = 'block';
        document.getElementById('content1').style.display = 'none';
        idcar = $(this).prop("id");
        for (var i = 0; i < conversatorios.length; i++) {
            if (parseInt(conversatorio[i].id) === parseInt(idcar)) {
                TraerOrador(conversatorio[i], conversatorio[i].orador);

            }
        }
    });

    $("#agregarConversatorio").on("click", function () {
        window.location.assign("crear_cnv.html")
    });
};

function TraerOrador(conver, orador) {
    $.ajax({
        url: "PersonalCalificado",
        type: "GET",
        dataType: "json",
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            personal = result.personales;
            colocarInfo(conver, orador, personal)
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
function colocarInfo(array, orador, personal) {
    for (var i = 0; i < personal.length; i++) {
        if (personal[i].documento === orador) {
            text = '<br>' +
                '<img src="" class="imgRedonda">' +
                '<br> Orador:' +
                '<center>' +
                '<h2>' +
                '<span> ' + personal[i].primerNombre + ' ' + personal[i].primerApellido + '</span>' +
                '<br>' +
                '</h2>' +
                '</center>' +
                '<br>' +
                '<p></p>' +
                '<br>'
            $("#orador").append(text);
        }
    }
    text = '<br>' +
        'Descripcion:' +
        '<br><br>' +
        '<p>' + array.descripcion + '</p>' +
        '<br><br>' +
        'Lugar:' +
        '<br><br>' +
        '<p>' + array.lugar + '</p>' +
        '<br><br>' +
        'Cronograma:' +
        '<br><br>' +
        '<p>' + array.cronograma + '</p>' +
        '<br><br><br><br><br><br><br><br>'+
        '<br><br><br><br><br><br><br><br>'+
        '<br><br><br><br>'

    $("#cronograma").append(text);

    text = '<br>' +
        '<img src="' + array.infografia + '">'
        '<br>'+
        '<br>' 
    $("#infografia").append(text);
    text = '<h2>' +
        '<span>' + array.titulo + '</span>' +
        '<br>' +
        '</h2>' +
        '<p> </p>' +
        '<h3><span></span> </h3>' +
        '<a href="blog.html" class="banner-button">Botón de pánico</a>'
    $("#titulo").append(text);
}


function crearConversatorio() {

    titulo = $("#texto").val();

    informacion = {
        Titulo: titulo,
    };

    console.log(informacion);
    $.ajax({
        url: "Conversatorio",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(informacion),
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            console.log(result);
            if (result != "error") {
                console.log(result);
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

