import {createDataForm} from './collector.js'

async function createFormMark (list, student, pair) {
    list.forEach((el, x) => {
        student.insertAdjacentHTML('beforeend', `
            <div class = 'st_Name' >${el}</div>
            <div>
                <select class = 'st_Pair' id = '${x}'>
                </select>
            </div>
        `)
    })

    student.insertAdjacentHTML('beforeend', `
        <button id = "sendForm">Сохранить!</button>
    `)

    await document.getElementById('sendForm')
        .addEventListener('click', createDataForm )

    list.forEach ((list, i) => {
        pair.forEach(pair => {
            document.getElementById(i).insertAdjacentHTML('beforeend', `
                <option value = '${pair}'>
                    ${pair}
                </option>
            `)
        })
    }) 
}

export {createFormMark}