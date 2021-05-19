/**
 * Script de bot conti front-end
 */



$(document).ready(function(){
	if(getCookie("tipoUsuario")!=="1"){
		alert("No autorizado");
		/*window.location.assign("index.html");*/
	}
	conectar();
})

var numeroSala;
var conexionTerminada=false;

/**
* Promesa
* @param {*} ms 
*/
var wait = ms => new Promise((r, j) => setTimeout(r, ms));
/**
* Dirección con protocolo ws 
*/
var wsUri = "ws://25.108.94.55:8080/ContigoApp/contiBot";
/**
* Websocket
*/

var misMensajes=new Array();
var websocket;

/**
 * Función que se encarga de conectar al servidor
 */
function conectar(){
	websocket=new WebSocket(wsUri);
	websocket.onopen = function (event) {
		ping();
		console.log("Conectado..."); //... y aparecerá en la pantalla
		enviarCredenciales();
		
	}
	
	websocket.onmessage = function (event) {
		if (event.data === "pong") {
			ping();
		} else {
			let obj = JSON.parse(event.data);
			if (obj.tipo === "codigo sala") {
				numeroSala = obj.numero;
				pintarRespuesta(obj.mensaje);
			} else if (obj.tipo === "respuesta" ||obj.tipo === "mensajeDePersonal"||obj.tipo === "perdidaConexion") {
				pintarRespuesta(obj.mensaje);
			} else if (obj.tipo === "escribiendoPersonal"){
				pintarEscribiendo();
			} else if (obj.tipo==="cerrar conexion"){
                pintarCierreConexion(obj.mensaje);    
            }
		}
	}
	websocket.onerror=function (event){
		console.error('Socket encountered error: ', err.message, 'Closing socket');
    	ws.close();
  	}
	websocket.onclose=function(event){
		if (conexionTerminada){
			alert("Se perdió la conexión con el servidor. En un momento se reestablecerá la conexión");
			console.log('Socket is closed. Reconnect will be attempted in 1 second.');
    		setTimeout(function() {conectar();}, 1000);
		}		
	}
}



/**
 * Función que envía un mensaje (serializado) al servidor
 * @param {*} object 
 */
function enviarMensaje(object) {
	var stringObject = JSON.stringify(object);
	websocket.send(stringObject);
	console.log("Enviando: " + stringObject);
}

/**
 * Función que manda un ping al servidor para verigicar la conexión
 */
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
		tipo: "ingreso estudiante",
		token: getCookie("token")
	}
	enviarMensaje(datos);
}

/**
 * Función que muestra en la pantalla la respuesta del servidor. 
 * @param {string} respuesta 
 */

function pintarRespuesta(respuesta) {
	misMensajes.push(respuesta);
	let txtConti = `<div id="mns_tiempo_conti" class="mensaje-autor">
	<div id="logoPC">
	<img id="pi" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTEyIDUxMiIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMjsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPHBhdGggc3R5bGU9ImZpbGw6IzQ2NEQ1OTsiIGQ9Ik01OS45MDcsMzM4LjUwNkMzMS44OTQsMzI4LjMxLTcuMjMxLDI5Mi45MTksMS4xNTQsMjY5Ljg4MXM2MS4xMDYtMjUsODkuMTE4LTE0LjgwNA0KCWMyOC4wMTMsMTAuMTk2LDM4LjAwNCwzNC45ODMsMjkuNjE5LDU4LjAyMUMxMTEuNTA2LDMzNi4xMzYsODcuOTE5LDM0OC43MDEsNTkuOTA3LDMzOC41MDZ6Ii8+DQo8cGF0aCBzdHlsZT0ib3BhY2l0eTowLjE7ZW5hYmxlLWJhY2tncm91bmQ6bmV3ICAgIDsiIGQ9Ik01OS45MDcsMzM4LjUwNkMzMS44OTQsMzI4LjMxLTcuMjMxLDI5Mi45MTksMS4xNTQsMjY5Ljg4MQ0KCXM2MS4xMDYtMjUsODkuMTE4LTE0LjgwNGMyOC4wMTMsMTAuMTk2LDM4LjAwNCwzNC45ODMsMjkuNjE5LDU4LjAyMUMxMTEuNTA2LDMzNi4xMzYsODcuOTE5LDM0OC43MDEsNTkuOTA3LDMzOC41MDZ6Ii8+DQo8cGF0aCBzdHlsZT0ib3BhY2l0eTowLjE7ZW5hYmxlLWJhY2tncm91bmQ6bmV3ICAgIDsiIGQ9Ik05MC4yNzIsMjU1LjA3N2MtMTAuMzAzLTMuNzUtMjMuOTQ3LTUuODUyLTM3LjQ5OC01Ljg3DQoJYy00Ljg4NCwzMC43NzUtNS4xNzQsNjAuMjkyLTAuOTMzLDg1LjgyNmMyLjc1MywxLjM1Niw1LjQ1OCwyLjUyNCw4LjA2NSwzLjQ3M2MyOC4wMTMsMTAuMTk2LDUxLjYtMi4zNyw1OS45ODUtMjUuNDA4DQoJQzEyOC4yNzcsMjkwLjA1OSwxMTguMjg1LDI2NS4yNzIsOTAuMjcyLDI1NS4wNzd6Ii8+DQo8cGF0aCBzdHlsZT0iZmlsbDojNDY0RDU5OyIgZD0iTTQ1Mi4wOTMsMzM4LjUwNmMyOC4wMTMtMTAuMTk2LDY3LjEzOC00NS41ODYsNTguNzUzLTY4LjYyNWMtOC4zODUtMjMuMDM4LTYxLjEwNi0yNS04OS4xMTgtMTQuODA0DQoJYy0yOC4wMTMsMTAuMTk2LTM4LjAwNCwzNC45ODMtMjkuNjE5LDU4LjAyMUM0MDAuNDk0LDMzNi4xMzYsNDI0LjA4MSwzNDguNzAxLDQ1Mi4wOTMsMzM4LjUwNnoiLz4NCjxwYXRoIHN0eWxlPSJvcGFjaXR5OjAuMTtlbmFibGUtYmFja2dyb3VuZDpuZXcgICAgOyIgZD0iTTQ1Mi4wOTMsMzM4LjUwNmMyOC4wMTMtMTAuMTk2LDY3LjEzOC00NS41ODYsNTguNzUzLTY4LjYyNQ0KCWMtOC4zODUtMjMuMDM4LTYxLjEwNi0yNS04OS4xMTgtMTQuODA0Yy0yOC4wMTMsMTAuMTk2LTM4LjAwNCwzNC45ODMtMjkuNjE5LDU4LjAyMQ0KCUM0MDAuNDk0LDMzNi4xMzYsNDI0LjA4MSwzNDguNzAxLDQ1Mi4wOTMsMzM4LjUwNnoiLz4NCjxwYXRoIHN0eWxlPSJvcGFjaXR5OjAuMTtlbmFibGUtYmFja2dyb3VuZDpuZXcgICAgOyIgZD0iTTQyMS43MjgsMjU1LjA3N2MxMC4zMDItMy43NSwyMy45NDctNS44NTIsMzcuNDk4LTUuODcNCgljNC44ODQsMzAuNzc1LDUuMTc0LDYwLjI5MiwwLjkzMyw4NS44MjZjLTIuNzUzLDEuMzU2LTUuNDU3LDIuNTI0LTguMDY1LDMuNDczYy0yOC4wMTMsMTAuMTk2LTUxLjYtMi4zNy01OS45ODUtMjUuNDA4DQoJQzM4My43MjMsMjkwLjA1OSwzOTMuNzE1LDI2NS4yNzIsNDIxLjcyOCwyNTUuMDc3eiIvPg0KPHBhdGggc3R5bGU9ImZpbGw6IzQ2NEQ1OTsiIGQ9Ik0yNTYsNDQyLjM4OGM4OS4zNTQsMCwxNjEuNTcxLTE4LjAyMSwxODMuNDk3LTkwLjQxQzQ2OC4yNjMsMjU3LjAwNiw0MTEuNzcxLDUyLjgzOCwyNTYsNTIuODM4DQoJUzQzLjczNywyNTcuMDA2LDcyLjUwMywzNTEuOTc4Qzk0LjQyOSw0MjQuMzY3LDE2Ni42NDYsNDQyLjM4OCwyNTYsNDQyLjM4OHoiLz4NCjxwYXRoIHN0eWxlPSJmaWxsOiNGRkZGRkY7IiBkPSJNMzY2LjA4NSwzMDIuNDk2Yy01LjQ1Ni0xMS4xMjYtMy4xNTMtMjQuNDIxLDUuNTU2LTMzLjIzN2MxNC45MzMtMTUuMTE2LDIzLjk4LTM2LjA1NSwyMy40MTItNTkuMTEzDQoJYy0xLjA1Ni00Mi44NzktMzUuOTU3LTc3LjkyOC03OC44MzEtNzkuMTU3Yy0xOS4yMTYtMC41NTEtMzYuOTc2LDUuNTc5LTUxLjE0NCwxNi4yMzJjLTUuMzUsNC4wMjMtMTIuODA1LDQuMDIzLTE4LjE1NiwwDQoJYy0xNC4xNjgtMTAuNjUzLTMxLjkyOC0xNi43ODMtNTEuMTQ0LTE2LjIzMmMtNDIuODc0LDEuMjI5LTc3Ljc3NSwzNi4yNzgtNzguODMxLDc5LjE1N2MtMC41NjgsMjMuMDU3LDguNDc5LDQzLjk5NiwyMy40MTIsNTkuMTEyDQoJYzguNzA5LDguODE2LDExLjAxMiwyMi4xMTEsNS41NTYsMzMuMjM3Yy0xNi4xNTIsMzIuOTM2LTI2LjM4MSw3MS42NTUtMzAuOTU4LDEwOC43NDVjMzQuNDMyLDIzLjg2Nyw4NC4yNzYsMzEuMTQ3LDE0MS4wNDMsMzEuMTQ3DQoJczEwNi42MTEtNy4yOCwxNDEuMDQzLTMxLjE0N0MzOTIuNDY2LDM3NC4xNTEsMzgyLjIzNywzMzUuNDMzLDM2Ni4wODUsMzAyLjQ5NnoiLz4NCjxwYXRoIHN0eWxlPSJmaWxsOiNGN0Q4OEI7IiBkPSJNMjU2LDI2My45MzNjMTIuNzQ3LDAsMjYuNDExLTExLjU0NywyOS4xNTEtMzEuMTk3YzMuMzItMjMuODEyLTEzLjA1MS0yOC44ODYtMjkuMTUxLTI4Ljg4Ng0KCXMtMzIuNDcxLDUuMDc0LTI5LjE1MSwyOC44ODZDMjI5LjU4OSwyNTIuMzg2LDI0My4yNTMsMjYzLjkzMywyNTYsMjYzLjkzM3oiLz4NCjxwYXRoIHN0eWxlPSJmaWxsOiNGNENGN0E7IiBkPSJNMjYyLjAxMiwyNjMuMDk2Yy0yMC4yNjYtNy41ODgtMjYuNTU4LTQ0LjczNS0xNy43NzEtNTguMDkyDQoJYy0xMS4yNjgsMi41MzItMTkuODg2LDkuODQ1LTE3LjM5MiwyNy43MzJjMi43NCwxOS42NSwxNi40MDQsMzEuMTk3LDI5LjE1MSwzMS4xOTcNCglDMjU3Ljk5NywyNjMuOTMzLDI2MC4wMTYsMjYzLjY0OSwyNjIuMDEyLDI2My4wOTZ6Ii8+DQo8Zz4NCgk8ZWxsaXBzZSBzdHlsZT0iZmlsbDojRjI3MjgxOyIgY3g9IjE3My4yMyIgY3k9IjIxNy44NyIgcng9IjI3LjMzMiIgcnk9IjIxLjc2NiIvPg0KCTxlbGxpcHNlIHN0eWxlPSJmaWxsOiNGMjcyODE7IiBjeD0iMzM4Ljc3IiBjeT0iMjE3Ljg3IiByeD0iMjcuMzMyIiByeT0iMjEuNzY2Ii8+DQo8L2c+DQo8Zz4NCgk8cGF0aCBzdHlsZT0iZmlsbDojNDY0RDU5OyIgZD0iTTIwMi4xNjgsMjAxLjY4OWMtNC4xNDIsMC03LjUtMy4zNTgtNy41LTcuNWMwLTMuMDM4LTIuNDcyLTUuNTA5LTUuNTEtNS41MDkNCgkJcy01LjUwOSwyLjQ3Mi01LjUwOSw1LjUwOWMwLDQuMTQyLTMuMzU4LDcuNS03LjUsNy41cy03LjUtMy4zNTgtNy41LTcuNWMwLTExLjMwOSw5LjItMjAuNTA5LDIwLjUwOS0yMC41MDkNCgkJczIwLjUxLDkuMiwyMC41MSwyMC41MDlDMjA5LjY2OCwxOTguMzMxLDIwNi4zMSwyMDEuNjg5LDIwMi4xNjgsMjAxLjY4OXoiLz4NCgk8cGF0aCBzdHlsZT0iZmlsbDojNDY0RDU5OyIgZD0iTTMzNS44NTIsMjAxLjY4OWMtNC4xNDMsMC03LjUtMy4zNTgtNy41LTcuNWMwLTMuMDM4LTIuNDcyLTUuNTA5LTUuNTEtNS41MDkNCgkJYy0zLjAzNywwLTUuNTA5LDIuNDcyLTUuNTA5LDUuNTA5YzAsNC4xNDItMy4zNTcsNy41LTcuNSw3LjVzLTcuNS0zLjM1OC03LjUtNy41YzAtMTEuMzA5LDkuMi0yMC41MDksMjAuNTA5LTIwLjUwOQ0KCQljMTEuMzEsMCwyMC41MSw5LjIsMjAuNTEsMjAuNTA5QzM0My4zNTIsMTk4LjMzMSwzMzkuOTk0LDIwMS42ODksMzM1Ljg1MiwyMDEuNjg5eiIvPg0KPC9nPg0KPHBhdGggc3R5bGU9Im9wYWNpdHk6MC4xO2VuYWJsZS1iYWNrZ3JvdW5kOm5ldyAgICA7IiBkPSJNMTg3LjEwMiw2OC4wMzFDODMuOTQ2LDExNy4wOCw0OC40NzEsMjcyLjYzNSw3Mi41MDMsMzUxLjk3OA0KCWMyMS45MjYsNzIuMzg5LDk0LjE0Myw5MC40MSwxODMuNDk3LDkwLjQxYzM1LjYyMywwLDY4LjUxNi0yLjg3LDk2LjU5MS0xMC45MDJDMTM5LjI5LDQzOS45OTEsMTM2Ljk2NiwxNjUuNzksMTg3LjEwMiw2OC4wMzF6Ii8+DQo8Zz4NCgk8cGF0aCBzdHlsZT0iZmlsbDojRjdEODhCOyIgZD0iTTIxOC4zODYsNDU5LjE2MmgtMzIuNzk0Yy05LjkzMiwwLTE3Ljk4NC04LjA1Mi0xNy45ODQtMTcuOTg0bDAsMA0KCQljMC05LjkzMiw4LjA1Mi0xNy45ODQsMTcuOTg0LTE3Ljk4NGgzMi43OTRjOS45MzIsMCwxNy45ODQsOC4wNTIsMTcuOTg0LDE3Ljk4NGwwLDANCgkJQzIzNi4zNjksNDUxLjExLDIyOC4zMTgsNDU5LjE2MiwyMTguMzg2LDQ1OS4xNjJ6Ii8+DQoJPHBhdGggc3R5bGU9ImZpbGw6I0Y3RDg4QjsiIGQ9Ik0yOTMuNjE0LDQ1OS4xNjJoMzIuNzk0YzkuOTMyLDAsMTcuOTg0LTguMDUyLDE3Ljk4NC0xNy45ODRsMCwwDQoJCWMwLTkuOTMyLTguMDUyLTE3Ljk4NC0xNy45ODQtMTcuOTg0aC0zMi43OTRjLTkuOTMyLDAtMTcuOTg0LDguMDUyLTE3Ljk4NCwxNy45ODRsMCwwDQoJCUMyNzUuNjMxLDQ1MS4xMSwyODMuNjgyLDQ1OS4xNjIsMjkzLjYxNCw0NTkuMTYyeiIvPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPC9zdmc+DQo=" />
	
	<div id="" class="contenido"><b>${respuesta.emisor}</b>: ${respuesta.mensaje}</div>
	</div>
	<div id="tiempo-msn-conti" class="fecha">${mueveReloj()}</div>               
	</div>
	`
	$("#mensajes").append(txtConti);
	$("#mensajes").animate({ scrollTop: $("#mensajes").height()*(($("#mensajes").children()).length)});
}


/**
 * Mensaje al bot conti
 * @param {string} mensaje 
 */
function decirleAConti(mensaje) {
	let datos = {
		tipo: "mensaje",
		numeroSala: numeroSala,
		mensaje: mensaje
	}
	enviarMensaje(datos);
	misMensajes.push(datos);
}


/**
 * Función del Reloj 
 * @returns texto string con la hora
 */
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


// Eventos de vista



$("#btn_enviar_mns").click(function () {
	var mns = $("#Enviarmensaje").val();
	if (mns !== "") {
		decirleAConti(mns);
		let mensaje = `
					<div id="mns_tiempo_usuario" class="mensaje-amigo">
					<div class="contenido"><b>Tú:</b> ${mns} </div>
					<i id="iconP" class="bi bi-person-fill"></i>
					<div id="tiempo-msn-usuario" class="fecha">${mueveReloj()}</div>
					</div>`
		$("#mensajes").append(mensaje);
		$('input[type="text"]').val('');
		$("#mensajes").animate({ scrollTop: $("#mensajes").height()*(($("#mensajes").children()).length)});
	}

});


$("body").keyup(function (e) {
	if (e.keyCode == 13) {
		$('#btn_enviar_mns').click();
	}
});


const x = document.getElementById('escribiendoPersonal');
	x.style.display ="none"
$("#Enviarmensaje").keypress(function(event){
	if (event.which !== 13){
		let mensaje={
			tipo:"escribiendoEstudiante",
			numeroSala: numeroSala
		}
		enviarMensaje(mensaje)
	}
})

function pintarEscribiendo(){
	let timeout
	x.style.display ="block"
	clearTimeout(timeout)
	timeout = setTimeout(() => {
		x.style.display ="none"
		clearTimeout(timeout)
	}, 1000)	
}

function pintarCierreConexion(mensaje){
	conexionTerminada=true;
	pintarRespuesta(mensaje);
	$("#Enviarmensaje").val("En un momento serás redirigido a la ventana principal");
	$("#Enviarmensaje").prop("readonly", true);
	setTimeout(function(){window.location.assign("opciones.html")}, 8000);
}
