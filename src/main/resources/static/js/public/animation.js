let check = document.getElementById('menuNote').style
let elem = document.getElementById('elem')
    .addEventListener ('click', el => {
        (check.display === 'block') ? check.display = 'none' : check.display = 'block'
    })

    const student = document.getElementById('nameUser')
    const kol = document.getElementById('markUser')

    import {list, pair} from '../private/local-JSON/studentList.js'

    console.log(list);
    console.log(kol);

    console.log(student)

    list.forEach(el => {
        student.insertAdjacentHTML('beforeend', `
            <option>${el}</option>
        `)
    })

    pair.forEach (el => {
        kol.insertAdjacentHTML ('beforeend', `
            <option>${el}</option>    
        `)
    })
    