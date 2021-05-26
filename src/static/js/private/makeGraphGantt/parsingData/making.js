import { chartMaking } from '../chartMaking.js';
import { parsTask, parsDepen } from './parsFields.js';
import { makeChildren } from './addChildrenToData.js';
import { makeWeakDepen } from './makeWeakDepen.js';

import { getSummary } from '../additionalModules/helpers_Module.js'

import { refreshChart } from '../chartMaking.js'

function makeGraph (response) {
    console.log(response)
    const rootTask = response.rootTask

    let tasks = parsTask(response)

// для обновления процентов, с дальнейшей возможностью расшерения до обновления каждого куска схемы в percentForTask
    getSummary({
        url: 'http://10.3.0.87:2000/admin/schema/1/summary',
        tasks: response
    }).then(res => {
        tasks = res            
        refreshChart(data)
    })

    const depen = parsDepen(response)

    console.log(tasks)
    
    const data = tasks.filter(el => el.theme) // filter tasks of themes

    makeWeakDepen(tasks, depen) // make WEAK depen in tasks
    
    makeChildren(data, depen, tasks) // make HIERARCHICAL depen in data
    
    console.log('Data elem', data)
<<<<<<< HEAD
    console.log(depen)

// для обновления процентов, с дальнейшей возможностью расшерения до обновления каждого куска схемы в percentForTask
    getSummary({
        url: 'http://localhost:2000/admin/schema/1/summary',
        tasks: response
    }).then(res => {
        tasks = res            
        refreshChart(data)
    }) 
=======
    
>>>>>>> 51d214d66448011b039f513c98ffa7528b8e453d
    chartMaking(data, rootTask)
}


export {makeGraph}