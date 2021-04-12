import chartMaking from './chartMaking.js';
import scheme1 from './local-JSON/scheme1.js';
import scheme2 from './local-JSON/scheme2.js';

function makeGraph (arrData) {
    arrData = scheme1

    console.log(arrData)

    const tasksPars = Object.entries(arrData.tasksMap).map(el => el[1])
    const depen = Object.entries(arrData.dependencies).map(el => el[1])
    tasksPars.splice(0,2)
    
    console.log(depen);

// закидывем WEAK зависимости в отдельную переменную для удобной работы с ними



    const data = tasksPars.filter(data => data.theme)

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
                    tasksPars.forEach (task => {
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