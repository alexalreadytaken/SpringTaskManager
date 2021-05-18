function makePercent ({summary, tasks}) {
    tasks.forEach(task => {
      summary.forEach(percent => {
          if (task.id === percent.entityId) {
             task.progressValue = percent.percentComplete
          }
        }) 
    })
    
    return tasks
}

export {makePercent}