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
