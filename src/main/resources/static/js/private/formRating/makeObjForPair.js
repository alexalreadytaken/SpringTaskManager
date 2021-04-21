async function makeObjPair (listSt) {
    const obj = []

    await document.getElementById

    listSt.forEach( (el, x) => {
        obj.push({
            nameST: el,
            pair: document.getElementById(x).value
        })
    })

    return obj
}

export {makeObjPair}