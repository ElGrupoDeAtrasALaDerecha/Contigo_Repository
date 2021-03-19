
function agregarNombre(nombre,id){
    let texto= `<button id="${id}" class="chat_estudiante">
    <a class="item active">
        <font style="vertical-align: inherit;">
            <font style="vertical-align: inherit;"></font>
            ${nombre}
        </font>
        </font>
    </a>
</button>`
$("#lista_chats").append(texto);
$("#"+id).click(function (){
    aparecerChat(nombre)
});
}
function aparecerChat(nombre){
    let chat=` <div class="ui segment">
    <font style="vertical-align: inherit;">
        <font style="vertical-align: inherit;">
            <div id="chatCompleto2">
                <div id="titulo">
                    <h1>${nombre}</h1>
                </div>
                <div id="chat">
                    <div id="mensajes">
    
                    </div>
                </div>
                <div id="mensaje">
                    <input id="Enviarmensaje" type="text" placeholder="Escribir mensaje...">
                    <button id="btn_enviar_mns" type="button" class="btn btn-outline-dark">
                        <i class="bi bi-arrow-right-circle-fill"></i>
                    </button>
                </div>
            </div>
        </font>
    </font>
</div>`
$("#chat_con_est").empty();
$("#chat_con_est").append(chat);
}