const df = document.getElementById('opcbtn');
df.style.display = "none"
function obtenerSelect() {
    /* Para obtener el valor */
    var tex = document.getElementById("modulos").value;
    if (tex === "1") {
        df.style.display = "block"
    } else if (tex !== "1") {
        df.style.display = "none"
    }
    /* Para obtener el texto 
    var combo = document.getElementById("modulos");
    var selected = combo.options[combo.selectedIndex].text;
    alert(selected);*/
}

$("#btnGerar").on("click", function () {
    window.location.assign("gestionCurso.html")
});