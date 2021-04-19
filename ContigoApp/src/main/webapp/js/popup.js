var btnAbrirPopup = document.getElementById('btn-abrir-popup'),
    overlay = document.getElementById('overlay'),
    popup = document.getElementById('popup'),
    btnCerrarPopup = document.getElementById('btn-cerrar-popup');

btnAbrirPopup.addEventListener('click', function () {
    overlay.classList.add('active');
    popup.classList.add('active');
});

btnCerrarPopup.addEventListener('click', function () {
    overlay.classList.remove('active');
    popup.classList.remove('active');
});
var n=0;
$('#opciones').click(function () {
    n = n+1;
    console.log(n)
    if (n <4) {
        let txt = '<div>' +
            '<input type="text" placeholder="ingrese la opción '+n+'"></input>' +
            '</div>';
        $('#extra').append(txt);
        
    
    }
    
});