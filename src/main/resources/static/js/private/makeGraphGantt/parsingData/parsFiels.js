function parsTask(info) {return Object.entries(info.tasksMap).map(el => el[1])}

function parsDepen (data) {return Object.entries(data.dependencies).map(el => el[1])}

export {parsDepen, parsTask}