import anyChartMaking from '../controllers/anyChartMaking.js';
import scheme1 from '../local-JSON/scheme1.js';
import scheme2 from '../local-JSON/scheme2.js'

export default function makeGraph(arrData) {
    var data = []

    // arrData = scheme1
    arrData = scheme2

    
    let tasksPars = Object.entries(arrData.tasksMap).map(el => el[1])
    let depen = Object.entries(arrData.dependencies).map(el => el[1])

    
    tasksPars.forEach (el => { if(el.theme) data.push(el) }) // построение отцов (главные темы)
   

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
            // if (el1.relationType === "WEAK") {
            //     if ( el1.id0.split('.')[1]) {
            //         console.log( el1.id0.split('.')[1] )
            //     } else {
            //         console.log( el1.id0.split('.')[0] )
            //     }
            // }
        })
    })


    // id0 - Parent
    // id1 - Children
    // нужно смотреть по зависимостям отцов, искать сразу всех детей
    // лучше сделать через рекурсию -> чтобы функция смотрела есть ли у детей еще дети

    console.log(data)

    anyChartMaking(data)
}