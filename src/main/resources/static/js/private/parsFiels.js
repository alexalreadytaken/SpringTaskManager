function parsField(dataP) {
    const tasksPars = Object.entries(dataP.tasksMap).map(el => el[1])
    const depen = Object.entries(dataP.dependencies).map(el => el[1])
    
    tasksPars.splice(0,2)
    
    const data = tasksPars.filter(el => el.theme)
    
    return [tasksPars, depen, data]
}

export default parsField