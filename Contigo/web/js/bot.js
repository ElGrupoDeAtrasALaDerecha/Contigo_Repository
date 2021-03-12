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
	var datos ={
        tipo:"datos",
        nombre:"pablito",
        segundoNombre:"Emilio"
    }
    enviarMensaje(datos);
    ping();
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
