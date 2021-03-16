function ListarConver(){
    $.ajax({
        url: "Institucion",
        type: "GET",
        dataType: "json",
        data:JSON.stringify(informacion),
        contentType: "JSON application/json charset=utf-8",
        beforeSend: function() {
            
        },
        success: function(result, textStatus, request) {
            console.log(result);
            if (result != "error") {
                console.log(result);
                
            } else {
                console.log("error");
            }

        },
        complete: function(result) {
        

        },
        error: function(result) {

        }

    });

}