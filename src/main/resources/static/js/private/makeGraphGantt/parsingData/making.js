import { chartMaking } from '../chartMaking.js';
import { parsTask, parsDepen } from './parsFields.js';
import { makeChildren } from './addChildrenToData.js';
import { makeWeakDepen } from './makeWeakDepen.js';

import { makePercent } from "./percentForTasks.js";
import {summary} from '../../local-JSON/percent.js';



function makeGraph (arrData) {
    const rootTask = arrData.rootTask.name.replaceAll('_', ' ')
    
    const parsedTasks = parsTask(arrData)

    const tasks = makePercent({
        summary: summary,
        tasks: parsedTasks
    })

    const depen = parsDepen(arrData)
    
    const data = tasks.filter(el => el.theme)

    makeWeakDepen(tasks, depen)
    
    makeChildren(data, depen, tasks)

    // id0 - Parent
    // id1 - Children
    console.log('Data elem', data)
    console.log('AllTasks: ', tasks)
    chartMaking(data, rootTask)
}

export {makeGraph}