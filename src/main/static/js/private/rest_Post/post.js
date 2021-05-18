function sendData (url, data) {
    fetch (url, {
        method: 'POST',
        body: data
    }).then(result => result.json())
        .then(result => result)
}

export {sendData}