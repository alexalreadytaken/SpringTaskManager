function makePercent (tasks) {
    tasks.forEach (task => {
        task.progressValue = 0.2
    })
}

export {makePercent}