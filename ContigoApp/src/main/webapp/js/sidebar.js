// Manejo de la modalidad de ocltar y mostrar el sidebar
let list = document.querySelector('.nav-list')
let sidebar = document.querySelector('.sidebar')
let arrowhd = document.querySelector('#hd')
let arrowsh = document.querySelector('#sh')

list.onclick = function () {
    if (!document.querySelector('.active')) {
        sidebar.classList.toggle("active")
    }
}
arrowsh.onclick = function () {
    sidebar.classList.toggle("active")
}
arrowhd.onclick = function () {
    sidebar.classList.toggle("active")
}

// Manejo del cambio entre contenido de cada item del sidebar
const li = document.querySelectorAll('li')
const item = document.querySelectorAll('.item')
const item_content = document.querySelectorAll('.item-content')

li.forEach( (items, i) => {
  li[i].addEventListener('click',()=>{
    item.forEach((element, i) => {
      item[i].classList.remove('selected')
    });
    item[i].classList.add('selected')
    item_content.forEach((element, i) => {
      item_content[i].classList.add('hide')
    });
    item_content[i].classList.remove('hide')
  })
});

$("#op5").click(function () {
    delete_cookie("token");
    delete_cookie("tipoUsuario");
    delete_cookie("documento");
    window.location.assign("index.html");
})


