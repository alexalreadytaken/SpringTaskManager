function makeObjPair (listSt) {
    const obj = []

    listSt.forEach( el=> {
        obj.push({
            nameST: el,
            pair: document.getElementById(x).value
        })
    })

    return obj
}

export {makeObjPair}