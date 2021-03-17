$("#btnlista").click(function () {
    ListarConver();
});


function ListarConver() {
    informacion = {
        idConver: 10,
    };

    $.ajax({
        url: "Conversatorio?id=10",
        type: "GET",
        dataType: "json",
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
        }, complete: function (result) {

        }, error: function (result) {
        }
    });
}
