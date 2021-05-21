import { chartMaking } from '../chartMaking.js';
import { parsTask, parsDepen } from './parsFields.js';
import { makeChildren } from './addChildrenToData.js';
import { makeWeakDepen } from './makeWeakDepen.js';

import { getSummary } from '../additionalModules/helpers_Module.js'

import { refreshChart } from '../chartMaking.js'

// import { summary } from '../../local-JSON/percent.js';

function makeGraph (response) {

    console.log(response)
    const rootTask = response.rootTask.name.replaceAll('_', ' ')

    let tasks = parsTask(response)

    const depen = parsDepen(response)

    console.log(tasks)

    
    const data = tasks.filter(el => el.theme) // filter tasks of themes

    makeWeakDepen(tasks, depen) // make WEAK depen in tasks
    
    makeChildren(data, depen, tasks) // make HIERARCHICAL depen in data
    
    console.log('Data elem', data)
    console.log(depen)

// для обновления процентов, с дальнейшей возможностью расшерения до обновления каждого куска схемы в percentForTask
    // getSummary({
    //     url: 'http://192.168.3.2:3000/summary',
    //     tasks: response
    // }).then(res => {
    //     tasks = res            
    //     refreshChart(data)
    // }) 
    chartMaking(data, rootTask)
        
}


export {makeGraph, parsTask}