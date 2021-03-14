/**
 * Script de bot conti front-end
 */



/*$("#btnPanico").click(function(){
    window.location.assign("chat2.html");
});*/

var numeroSala;

/**
* Promesa
* @param {*} ms 
*/
var wait = ms => new Promise((r, j) => setTimeout(r, ms));
/**
* Dirección con protocolo ws
*/
var wsUri = "ws://localhost:8080/ContigoApp/contiBot";
/**
* Websocket
*/
var websocket = new WebSocket(wsUri);
websocket.onopen = function (event) {
	console.log("Conectado..."); //... y aparecerá en la pantalla
	enviarCredenciales();
	ping();
}

websocket.onmessage = function (event) {
	if (event.data === "pong") {
		ping();
	} else {
		let obj = JSON.parse(event.data);
		if (obj.tipo === "codigo sala") {
			numeroSala = obj.numero;
		} else if (obj.tipo === "respuesta") {
			pintarRespuesta(obj.mensaje);
		}
	}
}


function enviarMensaje(object) {
	var stringObject = JSON.stringify(object);
	websocket.send(stringObject);
	console.log("Enviando: " + stringObject);
}

function ping() {
	myPing = { tipo: "ping", message: "heartbeating" };
	var prom = wait(28000)  // prom, is a promise
	var showdone = () => enviarMensaje(myPing);
	prom.then(showdone)
}


/**
 * Se envían credenciales al bot para la identificación
 */
function enviarCredenciales() {
	let datos = {
		tipo: "primer ingreso",
		token: getCookie("token")
	}
	enviarMensaje(datos);
}

/**
 * Función que muestra en la pantalla la respuesta del servidor
 * @param {string} respuesta 
 */

function pintarRespuesta(respuesta) {
	console.log(respuesta);
	let txtConti=`<div id="mns_tiempo_conti" class="mensaje-autor">
	<i class="bi bi-person"></i>
	<div class="flecha-izquierda"></div>
	<div id="" class="contenido">${respuesta}</div>
	<div id="tiempo-msn-conti" class="fecha">Enviado hace y minutos</div>               
	</div>
	`
	$("#mensajes").append(txtConti);
}



/**
 * Información a conti
 */
function decirleAConti(mensaje) {
	let datos = {
		tipo: "mensaje",
		"numero sala": numeroSala,
		mensaje: mensaje
	}
	enviarMensaje(datos);
}




// Eventos de vista


$(document).ready(function () {
	$('#btn_enviar_mns').click(function () {
		$('input[type="text"]').val('');
	});
});

$("#btn_enviar_mns").click(function () {
	console.log(mueveReloj());
	var mns = $("#Enviarmensaje").val();
	decirleAConti(mns);
	let mensaje = `
	<div id="mns_tiempo_usuario" class="mensaje-amigo">
	<div class="contenido">${mns} </div>
    <div class="flecha-derecha"></div>
    <i class="bi bi-person-fill"></i>
    <div id="tiempo-msn-usuario" class="fecha">${mueveReloj()}</div>
	</div>`
	$("#mensajes").append(mensaje);
});

$(document).ready(function () {
})



$("body").keyup(function (e) {
	if (e.keyCode == 13) {
		$('#btn_enviar_mns').click();
	}
});

/*reloj*/
function mueveReloj() {
	momentoActual = new Date()
	hora = momentoActual.getHours()
	minuto = momentoActual.getMinutes()
	segundo = momentoActual.getSeconds()

	str_segundo = new String(segundo)
	if (str_segundo.length == 1)
		segundo = "0" + segundo

	str_minuto = new String(minuto)
	if (str_minuto.length == 1)
		minuto = "0" + minuto

	str_hora = new String(hora)
	if (str_hora.length == 1)
		hora = "0" + hora

	horaImprimible = hora + " : " + minuto + " : " + segundo

	document.form_reloj.reloj.value = horaImprimible

	setTimeout("mueveReloj()", 1000)
	return horaImprimible;
}