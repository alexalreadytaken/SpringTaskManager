let check = document.getElementById('menuNote').style
const elem = document.getElementById('elem')
    .addEventListener ('click', el => {
        (check.display === 'block') ? check.display = 'none' : check.display = 'block'
    })

    const student = document.getElementById('menuNote')

    import {createFormMark} from './dinamicCreate.js'
    import {list, pair} from '../local-JSON/studentList.js'
    import {makeObjPair} from './makeObjForPair.js'

    const result = makeObjPair(list)

    console.log(result)

    createFormMark(list, student, pair)
    