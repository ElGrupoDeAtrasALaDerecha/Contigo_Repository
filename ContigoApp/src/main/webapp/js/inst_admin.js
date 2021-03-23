$('#crear_g').click(function (e) {
  var gradoSelt = $('#grado_slect option:selected').val()
  var id_inst = getCookie("ID_Inst")
  var obj = {
    clasificacion_id: gradoSelt,
    institucion_id: id_inst
  }
  console.log(obj)
  crearGrado(obj)
  listarGrados ()
})
/**
 * Función login
 * @param {*} obj
 */
function crearGrado (obj) {
  $.ajax({
    url: 'Grado',
    method: 'POST',
    dataType: 'json',
    data: JSON.stringify(obj),
    contentType: 'JSON application/json charset=utf-8',
    success: function (response) {
      console.log(response)
      setCodigo(response)
    },
    error: function (response) {
      console.log(JSON.stringify(response))
      setCodigo(response)
    }
  })
}

function listarGrados () {
  $.ajax({
    url: 'Grado',
    method: 'GET',
    dataType: 'json',
    success: function (response) {
      console.log(response)
      listaDeGrados(response)
    },
    error: function (response) {
      console.log("Error en la petición GET")
      console.log(JSON.stringify(response))
    }
  })
}

window.onload = listarGrados
var table = document.getElementById('tabla_grados')
var cont = 0
var aux = 0
var rows = 0

function listaDeGrados(serverMsj) {
  console.log("Salu2")
  if(aux == serverMsj.Grados.length){
    rows = serverMsj.Grados.length
  }else{
    rows = serverMsj.Grados.length-1
    aux = serverMsj.Grados.length
  }
  if (cont > 0) {
    for (let index = 0; index < rows; index++){ 
        table.deleteRow(1);
      } 
  }else{
    aux = serverMsj.Grados.length
  }
  for (let index = 0; index < serverMsj.Grados.length; index++) {
    table.insertRow(-1).innerHTML = '<td>' + serverMsj.GradosClasificados[index].clasificacion +'</td> <td>' + serverMsj.GradosClasificados[index].codigo +'</td>';    
  }
  cont++
  listarGrados()
}

function setCodigo (serverMsj) {
  // alert(serverMsj.codigo)
  if(serverMsj.codigo){
    document.getElementById('grado_code').value = serverMsj.codigo
  }
}
