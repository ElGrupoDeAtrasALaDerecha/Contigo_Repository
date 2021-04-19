var n = 0;


function mas() {
    n = n + 1;
    console.log('N', n)
    if (n < 4) {
        let txt = '<div>' +
            '<input type="text" placeholder="ingrese la opción ' + n + '" id="' + n + '"></input>' +
            '</div>';
        $('#extra').append(txt);
    }
}

function quitaropc() {
    console.log('Nr', n)
    if (n >= 3) {
        n = 3;
    }
    if (n != 0) {
        $('#' + n + '').remove();
        n = n - 1;
    }
}



function obtenerNombre(nom){
    return nom;
}
console.log('funciona mira :)',nom)






