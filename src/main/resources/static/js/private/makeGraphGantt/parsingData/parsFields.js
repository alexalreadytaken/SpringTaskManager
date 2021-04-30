// парсит мапу приходящуюю от сервера на массив объектов с тасками
function parsTask(info) {    
    return Object.entries(info.tasksMap).map(el => el[1]).splice(2, Object.entries(info.tasksMap).map(el => el[1]).length)
}


// парсит мапу приходящуюю от сервера на массив объектов с зависимостями тасков
function parsDepen (data) {
    return Object.entries(data.dependencies).map(el => el[1])
}

export {parsDepen, parsTask}