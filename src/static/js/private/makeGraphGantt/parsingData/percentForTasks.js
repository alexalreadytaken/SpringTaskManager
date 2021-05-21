function makePercent ({data, summary}) {
  return data.forEach(task => {
    summary.forEach(percent => {
      if (task.id === percent.entityId) {
        task.progressValue = percent.percentComplete
      }
    })
  })
}

export {makePercent}