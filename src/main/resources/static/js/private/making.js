import chartMaking from './chartMaking.js';
import scheme1 from './local-JSON/scheme1.js';
import {parsTask, parsDepen} from './parsFiels.js';

function makeGraph (arrData) {
    arrData = scheme1
    const tasks = parsTask(arrData).splice(2, parsTask(arrData).length) // fix splice 

    const depen = parsDepen(arrData)
    
    const data = tasks.filter(el => el.theme)

    console.log(depen);
    console.log(tasks)


    tasks.forEach (task => {

    })



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

    data.forEach( data => {
        data.children = []
        depen.forEach(depen => {
            if (depen.relationType === 'HIERARCHICAL') {
                if (data.id === depen.id0) {
                    tasks.forEach (task => {
                        if (task.id === depen.id1) {
                            data.children.push(task)
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