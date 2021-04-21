import { chartMaking } from './chartMaking.js';
import { parsTask, parsDepen } from './parsFiels.js';
import { makeChildren } from './addChildrenToData.js';
import { makeWeakDepen } from './makeWeakDepen.js';
import { makePercent } from "./percentForTasks.js";

function makeGraph (arrData) {
    const rootTask = arrData.rootTask.name.replaceAll('_', ' ')

    const tasks = parsTask(arrData).splice(2, parsTask(arrData).length)
    const depen = parsDepen(arrData)
    
    const data = tasks.filter(el => el.theme)

    makeWeakDepen(tasks, depen)

    makeChildren(data, depen, tasks)

    makePercent(tasks)
    // id0 - Parent
    // id1 - Children
    console.log(data)
    chartMaking(data, rootTask)
}

export {makeGraph}