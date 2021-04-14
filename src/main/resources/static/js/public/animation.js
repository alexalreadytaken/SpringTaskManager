

let elem = document.getElementById('elem')
    .addEventListener ('click', el => {
        
        noteTask(check = true)
    })


noteTask = (check) => {
    document.getElementById('menuNote').style.display = 'block' // доделать проверку чтобы на каждый клик скрывалась а потом показывалась меню
    check = false
    if (!check) document.getElementById('menuNote').style.display = 'none'
}