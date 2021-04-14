import chartMaking from './chartMaking.js';
import {parsTask, parsDepen} from './parsFiels.js';
import makeChildren from './addChildrenToData.js';

function makeGraph (arrData) {
    const rootTask = arrData.rootTask.name.replaceAll('_', ' ')

    const tasks = parsTask(arrData).splice(2, parsTask(arrData).length) // fix splice 
    const depen = parsDepen(arrData)
    
    const data = tasks.filter(el => el.theme)

    // console.log(depen);
    // console.log(tasks);

    tasks.forEach (task => {
        task.connector = []
        depen.forEach (depen => {
            if (depen.relationType === "WEAK" && task.id === depen.id0)  {
                // console.log({
                //     id: depen.id0,
                //     id1: depen.id1
                // })
                task.connector.push({
                    connectTo: depen.id1,
                    connectType: "finish-start"
                })
            }    
        })
    })

   makeChildren(data, depen, tasks)

    // id0 - Parent
    // id1 - Children
    console.log(data)
    chartMaking(data, rootTask)
}

export default makeGraph