import anyChartMaking from '../controllers/anyChartMaking.js';
import scheme1 from '../local-JSON/scheme1.js';
import scheme2 from '../local-JSON/scheme2.js'

export default function makeGraph(arrData) {
    var data = []
    var weakDepen = []
    var bigData = []

    arrData = scheme1
    // arrData = scheme2

    console.log(scheme1)

    let tasksPars = Object.entries(arrData.tasksMap).map(el => el[1])
    let depen = Object.entries(arrData.dependencies).map(el => el[1])
    
// закидывем WEAK зависимости в отдельную переменную для удобной работы с ними

    depen.forEach(el => {
        if (el.relationType === 'WEAK') {
            if ( el.id0.split('.')[1]) {
                weakDepen.push({
                    id0: el.id0.split('.')[1], id1: el.id1
                })
            } else {
                weakDepen.push({
                    id0: el.id0.split('.')[0], id1: el.id1
                })
            }
        }
    })

    console.log(tasksPars)

// костыль для адекватного прокидывания в data.connectorTo (сделать через проверку повторяющихся выходящих тасков, чтобы они лежали в массиме и их было удобнее доставать из массива и передавать в data в str)

    tasksPars.forEach( taskEl => {
        weakDepen.forEach( weakEl => {
            if (weakEl.id0 === taskEl.id) {
                bigData.push({
                    dep:weakEl.id1,
                    id0: taskEl.id
                })
            }
        })
    })

    // tasksPars.forEach( el => {
    //     bigData.forEach ( el1 => {
    //         if (el.id = el1.id0) {
    //             el.connectTo 
    //         }
    //     })
    // })

    console.log(bigData)
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
        })
    })

    console.log(weakDepen)
    // id0 - Parent
    // id1 - Children
    // нужно смотреть по зависимостям отцов, искать сразу всех детей
    // лучше сделать через рекурсию -> чтобы функция смотрела есть ли у детей еще дети

    console.log(data)

    anyChartMaking(data)
}