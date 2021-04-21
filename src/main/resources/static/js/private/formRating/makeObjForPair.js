function makeObjPair (listSt) {
    const obj = []
    listSt.forEach (el => {
        obj.push ({
            name: el,
            mark: 3
        }) 
    })

    setTimeout(() => {
        let children = document.getElementById('menuNote').children

        console.log(children[1].children[0].value)
    }, 1000);

    return obj
}

export {makeObjPair}