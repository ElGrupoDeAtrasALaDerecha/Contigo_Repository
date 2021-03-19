
function agregarNombreChat_sinA(nombre,id){
    let texto= `<button id="${id}" class="chat_estudiante_sinA" >
        <font style="vertical-align: inherit;">
            <font style="vertical-align: inherit;"></font>
            <i class="user circle icon"></i>
            ${nombre}
        </font>
        </font>
</button>`
$("#chats_sin_a").append(texto);
$("#"+id).click(function (){
    aparecerChat(nombre)
});
}

function agregarNombreChat_A(nombre,id){
    let texto= `<button id="${id}" class="chat_estudiante_A" class="ui black basic button">
        <font style="vertical-align: inherit;">
            <font style="vertical-align: inherit;"></font>
            <i class="user circle icon"></i>
            ${nombre}
        </font>
        </font>
</button>`
$("#chats_atendidos").append(texto);
$("#"+id).click(function (){
    aparecerChat(nombre);
    salaElegida=id;
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