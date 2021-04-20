let check = document.getElementById('menuNote').style
let elem = document.getElementById('elem')
    .addEventListener ('click', el => {
        (check.display === 'block') ? check.display = 'none' : check.display = 'block'
    })

    const student = document.getElementById('nameUser')
    const kol = document.getElementById('markUser')

    import {list, pair} from '../local-JSON/studentList.js'

    console.log(list);
    console.log(kol);

    console.log(student)

    list.forEach(el => {
        student.insertAdjacentHTML('beforeend', `
            <div>${el}</div>
        `)
    })
    
// make for everyone student form for pair

    // for (let i = 0; i < pair.length; i++) {
    //     pair.forEach (el1 => {
    //         kol.insertAdjacentHTML ('beforeend', `
    //             <select>
    //                 <option>${el1}</option>    
    //             </select>
    //         `)
    //     })
    // }
    
    