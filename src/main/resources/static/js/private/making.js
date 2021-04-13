import chartMaking from './chartMaking.js';
import scheme1 from './local-JSON/scheme1.js';
import parsField from './parsFiels.js';


function makeGraph (arrData) {
    arrData = scheme1

    const responseData = parsField(arrData)

    console.log(responseData)

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

    responseData[2].forEach( el => {
        el.children = []
        responseData[1].forEach(el1 => {
            if (el1.relationType === 'HIERARCHICAL') {
                if (el.id === el1.id0) {
                    responseData[0].forEach (task => {
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

    console.log(responseData[2])
    chartMaking(responseData[2])
}

export default makeGraph