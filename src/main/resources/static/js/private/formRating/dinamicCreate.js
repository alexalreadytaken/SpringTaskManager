function createFormMark (list, student, pair) {
    list.forEach((el, x) => {
        student.insertAdjacentHTML('beforeend', `
            <div style = "float: left">${el}</div>
            <div>
                <select id = '${x}'>
                </select>
            </div>
        `)
    })

    list.forEach ((list, i) => {
        pair.forEach(pair => {
            document.getElementById(i).insertAdjacentHTML('beforeend', `
                <option>
                    ${pair}
                </option>
            `)
        })
    }) 
}

export {createFormMark}