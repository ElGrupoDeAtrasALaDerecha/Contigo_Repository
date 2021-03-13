var numeroSala;

/**
* Promesa
* @param {*} ms 
*/
var wait = ms => new Promise((r, j) => setTimeout(r, ms));
/**
* Dirección con protocolo ws
*/
var wsUri = "ws://localhost:30001";
/**
* Websocket
*/
var websocket = new WebSocket(wsUri);
websocket.onopen = function(event){
	console.log("Conectado..."); //... y aparecerá en la pantalla
	enviarCredenciales();
    ping();
}

websocket.onmessage=function(event){
	if (event.data==="pong"){
		ping();
	}else{
		let obj=JSON.parse(event.data);
		if(obj.tipo==="numero sala"){
			numeroSala=obj.numero;
		}else if(obj.tipo==="respuesta"){
			pintarRespuesta(obj.mensaje);
		}
	}
}


function enviarMensaje(object){
	var stringObject = JSON.stringify(object);
	websocket.send(stringObject);
	console.log("Enviando: "+stringObject);
}

function ping(){
	myPing = {tipo: "ping",message: "heartbeating"};
	var prom = wait(28000)  // prom, is a promise
	var showdone = () => enviarMensaje(myPing);
	prom.then(showdone)
}


/**
 * Se envían credenciales al bot para la identificación
 */
function enviarCredenciales(){
	let datos={
		tipo:"primer ingreso",
		token: getCookie("token")
	}
	enviarMensaje(datos);
}

/**
 * Función que muestra en la pantalla la respuesta del servidor
 * @param {string} respuesta 
 */

function pintarRespuesta(respuesta){
	console.log(respuesta);
}



/**
 * Información a conti
 */
 function decirleAConti(mensaje){
	let datos={
		tipo:"mensaje",
		"numero sala": "1",
		mensaje: mensaje
	}
	enviarMensaje(datos);
}