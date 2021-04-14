function makeChildren (data, depen, tasks) {
    data.forEach( data => {
        data.children = []
        depen.forEach(depen => {
            if (depen.relationType === 'HIERARCHICAL' && data.id === depen.id0) {
                tasks.forEach (task => {
                    if (task.id === depen.id1) {
                        data.children.push(task)
                    }
                })
            }
        })
    })
}

export default makeChildren