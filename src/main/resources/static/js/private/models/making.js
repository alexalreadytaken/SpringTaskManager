import anyChartMaking from '../controllers/anyChartMaking.js';
export default function makeGraph(arrData) {

    arrData = {
        tasksMap: {
        2: {
        id: "2",
        name: "Квалификации",
        duration: 0,
        notes: null,
        theme: true,
        opened: false,
        fields: null,
        actualStart: "2020-11-18 11:45:00",
        actualEnd: "2020-11-18 16:15:00"
        },
        3: {
        id: "3",
        name: "Квалификация_1",
        duration: 0,
        notes: null,
        theme: false,
        opened: false,
        fields: {
        Вид: "Навык"
        },
        actualStart: "2020-11-18 11:45:00",
        actualEnd: "2020-11-18 11:45:00"
        },
        4: {
        id: "4",
        name: "Квалификация_2",
        duration: 0,
        notes: null,
        theme: false,
        opened: false,
        fields: {
        Вид: "Навык"
        },
        actualStart: "2020-11-18 15:30:00",
        actualEnd: "2020-11-18 15:30:00"
        },
        5: {
        id: "5",
        name: "Проект_1",
        duration: 0,
        notes: null,
        theme: false,
        opened: false,
        fields: {
        Вид: "Продукт"
        },
        actualStart: "2020-11-18 16:15:00",
        actualEnd: "2020-11-18 16:15:00"
        },
        6: {
        id: "6",
        name: "Тема_1",
        duration: 0,
        notes: null,
        theme: true,
        opened: false,
        fields: null,
        actualStart: "2020-11-18 08:00:00",
        actualEnd: "2020-11-18 11:45:00"
        },
        7: {
        id: "7",
        name: "Лекция_1",
        duration: 0,
        notes: "<p> Заметки к лекции 1 </p>",
        theme: false,
        opened: false,
        fields: {
        Вид: "Теория",
        Тип: "Интерактив",
        Формат: "Online"
        },
        actualStart: "2020-11-18 08:00:00",
        actualEnd: "2020-11-18 08:45:00"
        },
        8: {
        id: "8",
        name: "Лекция_2",
        duration: 0,
        notes: "<p> Заметки к лекции 2 </p>",
        theme: false,
        opened: false,
        fields: {
        Вид: "Теория"
        },
        actualStart: "2020-11-18 08:45:00",
        actualEnd: "2020-11-18 09:30:00"
        },
        9: {
        id: "9",
        name: "Тренинг_1",
        duration: 0,
        notes: "<p> План практики 1 </p>",
        theme: false,
        opened: false,
        fields: {
        Вид: "Практика",
        Тип: "Самостоятельное"
        },
        actualStart: "2020-11-18 08:45:00",
        actualEnd: "2020-11-18 11:45:00"
        },
        10: {
        id: "10",
        name: "Тема_2",
        duration: 0,
        notes: null,
        theme: true,
        opened: false,
        fields: null,
        actualStart: "2020-11-18 11:45:00",
        actualEnd: "2020-11-18 15:30:00"
        },
        11: {
        id: "11",
        name: "Лекция_1",
        duration: 0,
        notes: "<p> Заметки к лекции 1 </p>",
        theme: false,
        opened: false,
        fields: {
        Вид: "Теория"
        },
        actualStart: "2020-11-18 11:45:00",
        actualEnd: "2020-11-18 12:30:00"
        },
        12: {
        id: "12",
        name: "Лекция_2",
        duration: 0,
        notes: "<p> Заметки к лекции 2 </p>",
        theme: false,
        opened: false,
        fields: {
        Вид: "Теория",
        Тип: "Самостоятельное",
        Формат: "Online"
        },
        actualStart: "2020-11-18 12:30:00",
        actualEnd: "2020-11-18 13:15:00"
        },
        13: {
        id: "13",
        name: "Проект1",
        duration: 0,
        notes: "<p> План практики 1 </p>",
        theme: false,
        opened: false,
        fields: {
        Вид: "Практика",
        Тип: "Самостоятельное"
        },
        actualStart: "2020-11-18 12:30:00",
        actualEnd: "2020-11-18 15:30:00"
        },
        14: {
        id: "14",
        name: "Тема_2",
        duration: 0,
        notes: null,
        theme: true,
        opened: false,
        fields: null,
        actualStart: "2020-11-18 08:00:00",
        actualEnd: "2020-11-18 16:15:00"
        },
        15: {
        id: "15",
        name: "Лекция_1",
        duration: 0,
        notes: "<p> Заметки к лекции 1 </p>",
        theme: false,
        opened: false,
        fields: {
        Вид: "Теория",
        Тип: "Интеракив",
        Формат: "Offline"
        },
        actualStart: "2020-11-18 08:00:00",
        actualEnd: "2020-11-18 08:45:00"
        },
        16: {
        id: "16",
        name: "Лекция_2",
        duration: 0,
        notes: "<p> Заметки к лекции 2 </p>",
        theme: false,
        opened: false,
        fields: {
        Вид: "Теория"
        },
        actualStart: "2020-11-18 09:30:00",
        actualEnd: "2020-11-18 10:15:00"
        },
        17: {
        id: "17",
        name: "Проект_2",
        duration: 0,
        notes: "<p> План практики 1 </p>",
        theme: false,
        opened: false,
        fields: {
        Вид: "Практика",
        Тип: "Самостоятельное"
        },
        actualStart: "2020-11-18 13:15:00",
        actualEnd: "2020-11-18 16:15:00"
        }
        },
        dependencies: [
        {
        id0: "6.9",
        id1: "3",
        relationType: "WEAK"
        },
        {
        id0: "2.3",
        id1: "4",
        relationType: "WEAK"
        },
        {
        id0: "10.13",
        id1: "4",
        relationType: "WEAK"
        },
        {
        id0: "2.4",
        id1: "5",
        relationType: "WEAK"
        },
        {
        id0: "14.17",
        id1: "5",
        relationType: "WEAK"
        },
        {
        id0: "6.7",
        id1: "8",
        relationType: "WEAK"
        },
        {
        id0: "6.7",
        id1: "9",
        relationType: "WEAK"
        },
        {
        id0: "6",
        id1: "10",
        relationType: "WEAK"
        },
        {
        id0: "10.11",
        id1: "12",
        relationType: "WEAK"
        },
        {
        id0: "10.11",
        id1: "13",
        relationType: "WEAK"
        },
        {
        id0: "14.15",
        id1: "16",
        relationType: "WEAK"
        },
        {
        id0: "6.8",
        id1: "16",
        relationType: "WEAK"
        },
        {
        id0: "14.15",
        id1: "17",
        relationType: "WEAK"
        },
        {
        id0: "10.12",
        id1: "17",
        relationType: "WEAK"
        },
        {
        id0: "14",
        id1: "15",
        relationType: "HIERARCHICAL"
        },
        {
        id0: "14",
        id1: "16",
        relationType: "HIERARCHICAL"
        },
        {
        id0: "14",
        id1: "17",
        relationType: "HIERARCHICAL"
        },
        {
        id0: "10",
        id1: "11",
        relationType: "HIERARCHICAL"
        },
        {
        id0: "10",
        id1: "12",
        relationType: "HIERARCHICAL"
        },
        {
        id0: "10",
        id1: "13",
        relationType: "HIERARCHICAL"
        },
        {
        id0: "6",
        id1: "7",
        relationType: "HIERARCHICAL"
        },
        {
        id0: "6",
        id1: "8",
        relationType: "HIERARCHICAL"
        },
        {
        id0: "6",
        id1: "9",
        relationType: "HIERARCHICAL"
        },
        {
        id0: "2",
        id1: "3",
        relationType: "HIERARCHICAL"
        },
        {
        id0: "2",
        id1: "4",
        relationType: "HIERARCHICAL"
        },
        {
        id0: "2",
        id1: "5",
        relationType: "HIERARCHICAL"
        }
        ],
        rootTask: {
        id: "1",
        name: "Предмет_1",
        duration: 0,
        notes: null,
        theme: true,
        opened: true,
        fields: null,
        actualStart: "2020-11-18 08:00:00",
        actualEnd: "2020-11-18 16:15:00"
        }
        }
    var data = []

    console.log(arrData);
    let tasksPars = Object.entries(arrData.tasksMap).map(el => el[1])
    let depen = Object.entries(arrData.dependencies).map(el=>el[1])

    
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
            if (el1.relationType === "WEAK") {
                if ( el1.id0.split('.')[1]) {
                    console.log( el1.id0.split('.')[1] )
                } else {
                    console.log( el1.id0.split('.')[0] )
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