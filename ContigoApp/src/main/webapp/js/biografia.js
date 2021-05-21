var personal;

$(document).ready(function(){
    listar();
})

function listar(){
    $.ajax({
        url: "PersonalCalificado?tipo=bio",
        type: "GET",
        headers:{
            token:getCookie("token")
        },
        dataType: "json",     
        beforeSend: function () {
        },
        success: function (result, textStatus, request) {
            personal=result.personales;
        },
        complete: function (result) {
        },
        error: function (result) {
        }
    });
}