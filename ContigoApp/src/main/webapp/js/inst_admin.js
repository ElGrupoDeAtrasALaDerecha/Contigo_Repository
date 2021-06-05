// Sidebar conf
var id_inst;
var institucion;
$(document).ready(function () {
    id_inst = getCookie("ID_Inst")
    if (id_inst === "") {
        window.location.assign("index.html");
    } else {
        $.ajax({
            method: 'GET',
            url: 'Institucion',
            dataType: "json",
            headers: {
                token: getCookie("ID_Inst")
            },
            contentType: 'JSON application/json charset=utf-8',
            success: function (response) {
                if (response.tipo === "ok") {
                    institucion = response.institucion;
                    $("#navbarDropdown").append(institucion.nombre);
                } else {
                    toastr.warning(response.mensaje)
                }
            },
            error: function (response) {
                toastr.warning(response.mensaje)
            }
        });

    }
    $("#m1").css("background-color", "#162997")
    $(".m1").css("display", "block")
    $(".m2").css("display", "none")
    $(".m3").css("display", "none")
    $(".m4").css("display", "none")
})

$(".d-block").click(function () {
    var selected = '#' + ($(this).attr('id'))
    $(".selected").css("background-color", "#111B54")
    $(selected).css("background-color", "#162997")
    show(($(this).attr('id')))
})

function show(txt) {
    $(".m1").css("display", "none")
    $(".m2").css("display", "none")
    $(".m3").css("display", "none")
    $(".m4").css("display", "none")
    $("." + txt).css("display", "block")
    var selected = '#' + txt
    $(selected).css("background-color", "#162997")
}
// 
$('#grado_slect').click(function () {
  var gradoSelt = $('#grado_slect option:selected').val()
  var id_inst = getCookie("ID_Inst")
  var obj = {
    clasificacion_id: gradoSelt,
    institucion_id: id_inst
  }
  // console.log(obj)
  if(gradoSelt != 0){
    crearGrado(obj)
    listarGrados ()
  }
})
/**
 * Función login
 * @param {*} obj
 */
function crearGrado(obj) {
    $.ajax({
        url: 'Grado',
        method: 'POST',
        dataType: 'json',
        data: JSON.stringify(obj),
        contentType: 'JSON application/json charset=utf-8',
        success: function (response) {
            setCodigo(response)
        },
        error: function (response) {
            setCodigo(response)
        }
    })
    listarGrados()
}

function listarGrados() {
    $.ajax({
        url: 'Grado',
        method: 'GET',
        dataType: 'json',
        success: function (response) {
            // console.log(response)
            listaDeGrados(response)
        },
        error: function (response) {
            // console.log("Error en la petición GET")
            // console.log(JSON.stringify(response))
        }
    })
}

window.onload = listarGrados
var table = document.getElementById('tabla_grados')
var cont = 0
var aux = 0
var rows = 0

//Función para cargar la lista de cursos creados en el listado de grados.
function listaDeGrados(serverMsj) {
    if (aux == serverMsj.Grados.length) {
        rows = serverMsj.Grados.length
    } else {
        rows = serverMsj.Grados.length - 1
        aux = serverMsj.Grados.length
    }
    if (cont > 0) {
        for (let index = 0; index < rows; index++) {
            table.deleteRow(1);
        }
    } else {
        aux = serverMsj.Grados.length
    }
    for (let index = 0; index < serverMsj.Grados.length; index++) {
        var grado = serverMsj.GradosClasificados[index].clasificacion
        var codigo = serverMsj.GradosClasificados[index].codigo
        var curso = { g: grado, c: codigo }
        table.insertRow(-1).innerHTML = '<td><a onclick = "setCurso(\'' + curso + '\');">' + grado + '</a></td> <td>' + codigo + '</td>';
    }
    cont++
}

function setCodigo (serverMsj) {
  console.log(serverMsj)
  if(serverMsj.codigo){
    document.querySelector('.ds').value = serverMsj.codigo
  }
  listarGrados ()
}

function setCurso(data) {
    let codigoGrado = data.codigo
    show("m2")
    $("#m1").css("background-color", "#111B54")
    graficas.style.display = "block"
    solicitarDatosGrafica(codigoGrado);
    consultarInformacion()
}
$('.toCopy').click(function copyToClipboard() {
  var aux = document.createElement("input");
  aux.setAttribute("value", $("#cdg").val());
  document.body.appendChild(aux);
  aux.select();
  if (document.execCommand("copy")){
    toastr.success('¡Copiado!')
  } else {
    toastr.error('¡Erro al copiar!')
  }
  document.body.removeChild(aux);
})

$('#print').click(function () {
  const print = document.getElementById("output").innerHTML
  console.log(print)
  w = window.open()
  w.document.write(print)
  w.document.write('<style>.table{text-align: center; border: 2px solid #ced4da; font-size: 20px;} .table th, .table td{padding-left: 10px; border-top: 1px solid #ced4da;} .table td{padding-top: 10px; }</style>')
  w.document.close(); 
  w.focus(); 
	w.print();
	w.close();
})
/**
 * Cuando le da click al botón de cerrar sesión, se cierra la sesión
 */
$("#salir").click(function () {
    cerrarSesion();
});

/**
 * Función que cierra sesión
 */
function cerrarSesion() {
    delete_cookie("token");
    delete_cookie("tipoUsuario");
    delete_cookie("ID_Inst");
    window.location.assign("index.html");
}
