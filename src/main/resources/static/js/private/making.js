import chartMaking from './chartMaking.js';
import scheme1 from './local-JSON/scheme1.js';
import scheme2 from './local-JSON/scheme2.js';

function makeGraph (arrData) {
    arrData = scheme1

    let weakDepen = [], bigData = []

    console.log(arrData)

    const tasksPars = Object.entries(arrData.tasksMap).map(el => el[1])
    const depen = Object.entries(arrData.dependencies).map(el => el[1])
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

    // console.log(data);

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

    chartMaking(data)
}

export default makeGraph