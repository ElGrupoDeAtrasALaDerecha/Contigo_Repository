var c=1;

$("#Primera").click(function() {
    $("#situa").hide("slow");
    mostrar();
});
$("#Segunda").click(function() {
    $("#situa").toggle("slow");
    $("#situa3").toggle("slow");
});
$("#Primera2").click(function() {
    $("#situa2").toggle("slow");
    $("#resp1").toggle("slow");
});
$("#Segunda2").click(function() {
    $("#situa2").toggle("slow");
    $("#resp2").toggle("slow");
});
$("#Tercera2").click(function() {
    $("#situa2").toggle("slow");
    $("#situa3").toggle("slow");
});
$("#Primera3").click(function() {
    $("#situa3").toggle("slow");
    $("#resp2").toggle("slow");
});
$("#Segunda3").click(function() {
    $("#situa3").toggle("slow");
    $("#resp3").toggle("slow");
});
$("#Tercera3").click(function() {
    $("#situa3").toggle("slow");
    $("#resp1").toggle("slow");
});


function mostrar(){
    $("#situa2").toggle("slow");
}

