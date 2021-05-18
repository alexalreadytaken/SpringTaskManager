function makeWeakDepen (tasks, depen) {
    tasks.forEach (task => {
        task.connector = []
        depen.forEach (depen => {
            if (depen.relationType === "WEAK" && task.id === depen.id0)  {
                task.connector.push({
                    connectTo: depen.id1
                })
            }
        })
    })
}

export {makeWeakDepen}