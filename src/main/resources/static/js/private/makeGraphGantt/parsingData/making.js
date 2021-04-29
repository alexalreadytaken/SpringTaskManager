import { chartMaking } from '../chartMaking.js';
import { parsTask, parsDepen } from './parsFields.js';
import { makeChildren } from './addChildrenToData.js';
import { makeWeakDepen } from './makeWeakDepen.js';

import { makePercent } from "./percentForTasks.js";
import { summary } from '../../local-JSON/percent.js';



function makeGraph (response) {
    const rootTask = response.rootTask.name.replaceAll('_', ' ')

    const parsedTasks = parsTask(response)

    const tasks = makePercent({
        summary: summary,
        tasks: parsedTasks
    })

    const depen = parsDepen(response)
    
    const data = tasks.filter(el => el.theme)

    makeWeakDepen(tasks, depen)
    
    makeChildren(data, depen, tasks)

    console.log('Data elem', data)
    console.log('AllTasks: ', tasks)

    chartMaking(data, rootTask)
}

export {makeGraph}