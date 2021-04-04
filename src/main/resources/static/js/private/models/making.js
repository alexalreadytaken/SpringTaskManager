import anyChartMaking from '../controllers/anyChartMaking.js';
// import scheme1 from '../local-JSON/scheme1.js';
import scheme2 from '../local-JSON/scheme2.js'

export default function makeGraph(arrData) {
    arrData = scheme2

    var data = [], weakDepen = [], bigData = []

    console.log(arrData)

    let tasksPars = Object.entries(arrData.tasksMap).map(el => el[1])
    let depen = Object.entries(arrData.dependencies).map(el => el[1])
    
    tasksPars.splice(0,2)
    
    console.log(tasksPars)

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

// костыль для адекватного прокидывания в data.connectorTo (сделать через проверку повторяющихся выходящих тасков, чтобы они лежали в массиме и их было удобнее доставать из массива и передавать в data в str)

    tasksPars.forEach( taskEl => {
        weakDepen.forEach( weakEl => {
            if (weakEl.id1 === taskEl.id) {
                bigData.push({
                    id1:weakEl.id1,
                    id0: weakEl.id0
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

    // id0 - Parent
    // id1 - Children
    // нужно смотреть по зависимостям отцов, искать сразу всех детей
    // лучше сделать через рекурсию -> чтобы функция смотрела есть ли у детей еще дети

    console.log(data)

    anyChartMaking(data)
}