import chartMaking from './chartMaking.js';
import scheme1 from './local-JSON/scheme1.js';
import {parsTask, parsDepen} from './parsFiels.js';

function makeGraph (arrData) {
    arrData = scheme1

    console.log(parsTask(arrData))

    const tasks = parsTask(arrData).splice(2, parsTask(arrData).length) // fix splice 

    const depen = parsDepen(arrData)
    
    const data = tasks.filter(el => el.theme)

    console.log(depen);

    // tasksPars.forEach (el => {
    //     el.connector = [
    //         {
    //             "connectTo": '4',
    //             "connectTo": '2'
    //         }
    //     ]
    // })

    // data.reduce((prev, next) => {
    //     depen.forEach((el, i) => {
    //         if (el.relationType === 'HIERARCHICAL'  ) {
    //             if (el.id0 === next.id) {
    //                 prev.push ( next.children = tasksPars[5])
    //             }
    //         }
    //     })
    //     return data 
    // }, [])

    data.forEach( el => {
        el.children = []
        depen.forEach(el1 => {
            if (el1.relationType === 'HIERARCHICAL') {
                if (el.id === el1.id0) {
                    tasks.forEach (task => {
                        if (task.id === el1.id1) {
                            el.children.push(task)
                        }
                    })
                }
            }
        })
    })

    // id0 - Parent
    // id1 - Children
    console.log(data)
    chartMaking(data)
}

export default makeGraph