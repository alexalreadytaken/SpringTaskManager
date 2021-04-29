
function parsTask(info) {    
    let tasks = Object.entries(info.tasksMap).map(el => el[1])
    tasks.splice(0, 2)

    return tasks
}



function parsDepen (data) {return Object.entries(data.dependencies).map(el => el[1])}

export {parsDepen, parsTask}