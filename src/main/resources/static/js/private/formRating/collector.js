let check = document.getElementById('menuNote').style
const elem = document.getElementById('elem')
    .addEventListener ('click', el => {
        (check.display === 'block') ? check.display = 'none' : check.display = 'block'
    })

    const student = document.getElementById('menuNote')

    import {createFormMark} from './dinamicCreate.js'
    import {list, pair} from '../local-JSON/studentList.js'
    import {makeObjPair} from './makeObjForPair.js'

    // import {sendData} from '../rest_Post/post.js'
    // import {config} from '../config.js'

    function createDataForm () {
        makeObjPair(list)
        .then(result => console.log(result)) // fix send to server
    }

    createFormMark(list, student, pair)

export {createDataForm}