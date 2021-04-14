import chartMaking from './chartMaking.js';
import {parsTask, parsDepen} from './parsFiels.js';

function makeGraph (arrData) {
    
    const tasks = parsTask(arrData).splice(2, parsTask(arrData).length) // fix splice 

    const depen = parsDepen(arrData)
    
    const data = tasks.filter(el => el.theme)

    console.log(depen);
    console.log(tasks)


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

    // data.reduce((prev, next) => {
    //     depen.forEach((el, i) => {
    //         if (el.relationType === 'HIERARCHICAL') {
    //             if (el.id0 === next.id) {
    //                 prev.push ( next.children = tasksPars[5])
    //             }
    //         }
    //     })
    //     return data 
    // }, [])

    // data.forEach( data => {
    //     data.children = []
    //     depen.forEach(depen => {
    //         if (depen.relationType === 'HIERARCHICAL') {
    //             if (data.id === depen.id0) {
    //                 tasks.forEach (task => {
    //                     if (task.id === depen.id1) {
    //                         data.children.push(task)
    //                     }
    //                 })
    //             }
    //         }
    //     })
    // })

    // id0 - Parent
    // id1 - Children
    console.log(data)
    chartMaking(data)
}

export default makeGraph