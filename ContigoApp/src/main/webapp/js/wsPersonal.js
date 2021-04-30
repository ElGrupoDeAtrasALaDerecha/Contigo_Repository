/**
 * Script de bot conti front-end (vista de personal calificado)
 */

 var salasSinAtender = new Array();
 var salasEnAtencion = new Array();
 var salaElegida;
 var dataPersonal;
 var divEscribiendo;
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
 
 $(document).ready(function () {
	 if (getCookie("tipoUsuario") !== "2") {
		 alert("No autorizado");
		 //window.location.assign("index.html");
		 $('#cerrarConexion').popup();
	 }
 })
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
		 if (obj.tipo === "salas") {
			 console.log("Las salas son: ", obj)
			 if (obj.salas !== undefined) {
				 salasSinAtender = obj.salas;
				 dataPersonal = obj.data;
				 pintarSalas();
			 }
 
		 }
		 else if (obj.tipo === "mensajeEstudiante") {
			 console.log("entró un mensaje: ", obj);
			 let numeroSala = obj.numeroSala;
			 let sala = buscarSalaEnAtencion(numeroSala);
			 sala.mensajes.push(obj.mensaje);
			 if (numeroSala === parseInt(salaElegida, 10)) {
				 pintarRespuesta(obj.mensaje,true);
			 }
 
		 }
		 else if (obj.tipo === "nuevoEstudiante") {
			 console.log("Entró un estudiante: ", obj);
			 delete obj.tipo;
			 salasSinAtender.push(obj);
			 agregarNombreChatSinAtender(obj.estudiante.primerNombre + " " + obj.estudiante.primerApellido, obj.numeroSala);
		 } else if (obj.tipo === "desconexionEstudiante") {
			 removerEstudianteDeLista(obj);
 
		 }
		 else if (obj.tipo === "estudianteAtendido") {
			 console.log("Otro personal atendió un estudiante: ", obj)
			 removerEstudianteDeChat(obj.numeroSala);
		 }
		 else if (obj.tipo === "conversacion") {
			 console.log("Se agregó una conversación ", obj);
			 let sala = buscarSalaEnAtencion(obj.numeroSala);
			 sala.mensajes = obj.conversacion;
			 salaElegida = obj.numeroSala;
			 aparecerChat(obj.estudiante.primerNombre + " " + obj.estudiante.primerApellido);
 
		 } else if (obj.tipo === "escribiendoEstudiante") {
			 pintarEscribiendo(obj.numeroSala);
		 }
 
 
	 }
 }
 websocket.onclose = function(event){
	 //window.location.assign("admin_perca.html");
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
		 tipo: "ingreso personal",
		 token: getCookie("token")
	 }
	 enviarMensaje(datos);
 }
 
 /**
  * Función que muestra en la pantalla la respuesta del servidor
  * @param {string} respuesta 
  * @param {boolean} down
  */
 
 function pintarRespuesta(respuesta,down) {
	 let txt = `<div id="mns_tiempo_conti" class="mensaje-autor">
	 <i class="bi bi-person"></i>
	 <div class="flecha-izquierda"></div>
	 <div id="" class="contenido"><b>${respuesta.emisor}: </b>${respuesta.mensaje}</div>
	 <div id="tiempo-msn-conti" class="fecha">Enviado hace y minutos</div>               
	 </div>
	 `
	 $("#mensajes").append(txt);
	 if(down){
		 $("#mensajes").animate({ scrollTop: $("#mensajes").height() * (($("#mensajes").children()).length) });
	 }
	 
 }
 
 
 /**
  * Mensaje dirigido al
  * @param {string} mensaje 
  */
 function decirleAEstudiante(mensaje) {
	 let datos = {
		 tipo: "mensaje personal",
		 numeroSala: salaElegida,
		 mensaje: mensaje
	 }
	 enviarMensaje(datos);
	 sala = buscarSalaEnAtencion(datos.numeroSala);
	 sala.mensajes.push({ tipo: 2, emisor: dataPersonal.primerNombre + " " + dataPersonal.primerApellido, mensaje: mensaje });
	 $("#mensajes").animate({ scrollTop: $("#mensajes").height() * (($("#mensajes").children()).length) });
 }
 
 /**
  * Función que muestra las salas de chats sin atender
  */
 function pintarSalas() {
	 console.log(salasSinAtender);
	 for (let i = 0; i < salasSinAtender.length; i++) {
		 let sala = salasSinAtender[i];
		 if (!sala.atendido) {
			 agregarNombreChatSinAtender(sala.estudiante.primerNombre + " " + sala.estudiante.primerApellido, sala.numeroSala);
		 }
	 }
 }
 
 
 
 /**
  * Remueve un estudiante del chat de la lista sin atender
  * @param {number} numeroSala que es el número de la sala en la que está el estudiante
  * @returns La sala
  */
 function removerEstudianteDeListaSinAtender(numeroSala) {
	 //
	 for (let i = 0; i < salasSinAtender.length; i++) {
		 let sala = salasSinAtender[i];
		 if (sala.numeroSala === parseInt(numeroSala, 10)) {
			 return salasSinAtender.splice(i, 1)[0];
		 }
	 }
 }
 
 
 /**
  * Remueve un estudiante del chat de la lista en atención
  * @param {number} numeroSala que es el número de la sala en la que está el estudiante
  * @returns La sala removida
  */
 function removerEstudianteDeListaEnAtencion(numeroSala) {
	 //
	 for (let i = 0; i < salasSinAtender.length; i++) {
		 let sala = salasSinAtender[i];
		 if (sala.numeroSala === numeroSala) {
			 return salasSinAtender.splice(i, 1);
		 }
	 }
 }
 
 
 /**
  * Función que busca un objeto sala  en la lista de salas sin atender
  * @param {number} numeroSala que es el número de la sala
  * @returns el objeto sala
  */
 function buscarSalaSinAtender(numeroSala) {
	 for (let i = 0; i < salasSinAtender.length; i++) {
		 let sala = salasSinAtender[i];
		 if (sala.numeroSala === numeroSala) {
			 return sala;
		 }
	 }
	 return null;
 }
 
 /**
  * Función que busca un objeto sala  en la lista de salas sin atender
  * @param {number} numeroSala que es el número de la sala
  * @returns el objeto sala correspondiente al número
  */
 function buscarSalaEnAtencion(numeroSala) {
	 for (let i = 0; i < salasEnAtencion.length; i++) {
		 let sala = salasEnAtencion[i];
		 if (sala.numeroSala === parseInt(numeroSala, 10)) {
			 return sala;
		 }
	 }
	 return null;
 }
 
 
 /**
  * Se elimia estudiante de alguna de las listas de atención porque se desconectó
  * @param {*} obj que es la respuesta del servidor 
  * 
  */
 function removerEstudianteDeLista(obj) {
	 for (let i = 0; i < salasEnAtencion.length; i++) {
		 if (salasEnAtencion[i].numeroSala === obj.numeroSala) {
			 salasEnAtencion.splice(i, 1);
			 if (parseInt(salaElegida, 10) === parseInt(obj.numeroSala, 10)) {
				 console.log("Entré al if", obj.mensaje)
				 $("#Enviarmensaje").val(obj.mensaje);
				 $("#Enviarmensaje").prop("readonly", true);
				 $("body").off("keyup");
			 }
			 $("#" + obj.numeroSala).remove();
			 return;
		 }
	 }
	 for (let i = 0; i < salasSinAtender.length; i++) {
		 if (salasSinAtender[i].numeroSala === obj.numeroSala) {
			 salasSinAtender.splice(i, 1);
			 $("#" + obj.numeroSala).remove();
			 return;
		 }
 
	 }
	 $("#" + numeroSala).remove();
 }
 
 
 /**
  * Se agrega un botón con el chat del estudiante en la sala sin atención 
  * @param {string} nombre Que es el nombre del estudiante
  * @param {number} id que es el id de la sala
  */
 function agregarNombreChatSinAtender(nombre, id) {
 
	 //Agrego el botón
	 let texto = `<button id="${id}" class="chat_estudiante_sinA" >
						 <font style="vertical-align: inherit;">
							 <font style="vertical-align: inherit;"></font>
							 <i class="user circle icon"></i>
							 ${nombre}
						 </font>
						 </font>
				 </button>`
	 $("#chats_sin_a").append(texto);
 
	 //agrego el evento al botón
	 $("#" + id).click(function () {
		 let mensaje = {
			 tipo: "conexion personal",
			 numeroSala: $(this).prop("id"),
			 token: getCookie("token")
		 }
		 enviarMensaje(mensaje);
		 salaElegida = mensaje.numeroSala;
		 let sala = removerEstudianteDeListaSinAtender(salaElegida);
		 console.log("Sala removida sin atender", sala);
		 $(this).off("click");
		 $(this).remove();
		 salasEnAtencion.push(sala);
		 agregarNombreChatEnAtencion(nombre, id);
		 console.log(sala, mensaje.numeroSala);
	 });
 }
 /**
  * Se agrega un botón con el chat del estudiante en la en atención 
  * @param {string} nombre Que es el nombre del estudiante
  * @param {number} id que es el id de la sala
  */
 function agregarNombreChatEnAtencion(nombre, id) {
	 let texto = `<button id="${id}" class="chat_estudiante_A" class="ui black basic button">
					 <font style="vertical-align: inherit;">
						 <font style="vertical-align: inherit;"></font>
							 <i class="user circle icon"></i>
							 ${nombre}
						 </font>
					 </font>
				 </button>`
	 $("#chats_atendidos").append(texto);
	 $("#" + id).click(function () {
		 salaElegida = id;
		 aparecerChat(nombre);
	 });
 }
 
 
 /**
  * Aparece la conversación de un estudiante
  * @param {string} nombre 
  */
 function aparecerChat(nombre) {
	 $("#chat_con_est").empty();
	 let chat = ` <div class="ui segment">
				 <font style="vertical-align: inherit;">
					 <font style="vertical-align: inherit;">
						 <div id="chatCompleto2">
							 <div id="titulo">
								 
								 <h1>${nombre}</h1>
								 <div id="escribiendoEstudiante" class="escribiendoA">
									 <h5> Escribiendo</h5>
									 <div id="load"class="loader"></div>
								 </div>
								 <button id="cerrarConexion" class="ui black mini right floated button"
										 data-content="Cerrar conexión">
										 <i class="close icon"></i>
								 </button>
							 </div>
							 <div id="chat">
								 <div id="mensajes">
				 
								 </div>
							 </div>
							 <div id="mensaje">
								 <input id="Enviarmensaje" class="mensajeInput" type="text" placeholder="Escribir mensaje...">
								 <button id="btn_enviar_mns" type="button" class="btn btn-outline-dark">
									 <i class="bi bi-arrow-right-circle-fill"></i>
								 </button>
							 </div>
						 </div>
					 </font>
				 </font>
			 </div>`
 
	 $("#chat_con_est").append(chat);
 
 
	 divEscribiendo = document.getElementById('escribiendoEstudiante');
	 divEscribiendo.style.display = "none"
	 $("#Enviarmensaje").keypress(function(event){
		 if (event.which !== 13){
			 let mensaje={
				 tipo:"escribiendoPersonal",
				 numeroSala: salaElegida
			 }
			 enviarMensaje(mensaje)
		 }
	 })
 
	 cargarListaDeMensajes();
 
	 /**
	   * Al dar click, se envía un mensaje a un estudiante
	   */
	 $("#btn_enviar_mns").click(function () {
		 if($("#Enviarmensaje").val()!==""){
			 mensajeDesdePersonalAlEstudiante();
		 }
		 
	 });
	 $("body").off("keyup");
	 $("body").keyup(function (e) {
		 if (e.keyCode == 13) {
			 $('#btn_enviar_mns').click();
		 }
	 });
 
	 $("#cerrarConexion").click(function(){
		 cerrarConexionConEstudiante();
	 })
 }
 
 /**
  * Mensaje enviado desde el personal al estudiante
  */
 function mensajeDesdePersonalAlEstudiante() {
	 let mns = {
		 emisor: "Tú",
		 mensaje: $("#Enviarmensaje").val()
	 }
	 decirleAEstudiante(mns.mensaje);
	 pintarMensajeDePersonalAEstudiante(mns,true)
 }
 
 /**
  * 
  * @param {string} mns 
  * @param {Boolean} down 
  */
 function pintarMensajeDePersonalAEstudiante(mns,down) {
	 let mensaje = `
		 <div id="mns_tiempo_usuario" class="mensaje-amigo">
		 <div class="contenido"><b>${mns.emisor}: </b>${mns.mensaje} </div>
		 <div class="flecha-derecha"></div>
		 <i class="bi bi-person-fill"></i>
		 <div id="tiempo-msn-usuario" class="fecha"></div>
		 </div>`
	 $("#mensajes").append(mensaje);
	 $('input[type="text"]').val('');
	 if(down){
		 $("#mensajes").animate({ scrollTop: $("#mensajes").height() * (($("#mensajes").children()).length) });
	 }
	 
 }
 /**
  * Función que carga los mensajes de un chat específico
  */
 
 function cargarListaDeMensajes() {
	 let sala = buscarSalaEnAtencion(salaElegida);
	 console.log("la sala elegida", sala);
	 let mensajes = sala.mensajes;
	 for (let i = 0; i < mensajes.length; i++) {
		 let mensaje = mensajes[i];
		 if (mensaje.tipo === 1) {
			 pintarRespuesta(mensaje,false);
 
		 } else {
			 pintarMensajeDePersonalAEstudiante(mensaje,false);
		 }
 
 
	 }
 }
 /**
  * 
  * @param {*} numeroSalaMensaje 
  */
 function pintarEscribiendo(numeroSalaMensaje) {
	 if (salaElegida === parseInt(numeroSalaMensaje, 10)) {
		 let timeout
		 divEscribiendo.style.display = "block"
		 clearTimeout(timeout)
		 timeout = setTimeout(() => {
			 divEscribiendo.style.display = "none"
			 clearTimeout(timeout)
		 }, 1000)
	 }
 }
 
 function cerrarConexionConEstudiante(){
	 let obj= {
		 tipo:"cerrar conexion",
		 numeroSala:salaElegida
	 }
	 enviarMensaje(obj);
	 $("#Enviarmensaje").val("Ha terminado de conversar con este estudiante");
	 $("#Enviarmensaje").prop("readonly", true);
	 $("body").off("keyup");
 }